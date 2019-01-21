#include <jni.h>
#include <string>
#include <vector>
#include <map>

namespace movies {
    class Actor {
    public:
        std::string name;
        int age;

        //optional challenge 1: Load image from URL
        std::string imageUrl;
    };

    class Movie {
    public:
        std::string name;
        int lastUpdated;

    };

    class MovieDetail {
    public:
        std::string name;
        float score;
        std::vector<Actor> actors;
        std::string description;
    };

    class MovieController {
    private:
        std::vector<Movie*> _movies;
        std::map<std::string, MovieDetail*> _details;

    public:
        MovieController() {
            //populate data
            for (int i = 0; i < 10; i++) {
                auto movie = new Movie();
                movie->name = "Top Gun " + std::to_string(i);
                movie->lastUpdated = i * 10000;
                _movies.push_back(movie);

                auto movieDetail = new MovieDetail();
                movieDetail->name = movie->name;
                movieDetail->score = rand() % 10;
                movieDetail->description = "As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.";

                auto tomCruise = Actor();
                tomCruise.name = "Tom Cruise";
                tomCruise.age = 50;

                auto valKilmer = Actor();
                valKilmer.name = "Val Kilmer";
                valKilmer.age = 46;
                valKilmer.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg";

                movieDetail->actors.push_back(tomCruise);
                movieDetail->actors.push_back(valKilmer);

                if (i % 2 == 0) {
                    auto timRobbins = Actor();
                    timRobbins.name = "Tim Robbins";
                    timRobbins.age = 55;
                    timRobbins.imageUrl = "https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg";

                    movieDetail->actors.push_back(timRobbins);
                } else {
                    auto jenniferConnelly = Actor();
                    jenniferConnelly.name = "Jennifer Connelly";
                    jenniferConnelly.age = 39;
                    jenniferConnelly.imageUrl = "https://m.media-amazon.com/images/M/MV5BOTczNTgzODYyMF5BMl5BanBnXkFtZTcwNjk4ODk4Mw@@._V1_UY317_CR12,0,214,317_AL_.jpg";

                    movieDetail->actors.push_back(jenniferConnelly);
                }

                _details[movie->name] = movieDetail;
            }
        }

        //Returns list of movies
        std::vector<Movie*> getMovies() {
            return _movies;
        }

        //Returns details about a specific movie
        MovieDetail* getMovieDetail(std::string name) {
            for (auto item:_details) {
                if (item.second->name == name) {
                    return item.second;
                }
            }
            return nullptr;
        }


    };
}
movies::MovieController controller = movies::MovieController();

extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_example_myapplication_MainActivity_getMoviesJNI(
        JNIEnv *env,
        jobject obj) {

    std::vector<movies::Movie *> movies = controller.getMovies();

    jclass cls = env->FindClass("com/example/myapplication/data/Movie");
    jobjectArray array = env->NewObjectArray(controller.getMovies().size(), cls, nullptr);
    jmethodID construct = env->GetMethodID(cls, "<init>", "(Ljava/lang/String;I)V");

    for (int i = 0; i < movies.size(); i++) {

        env->SetObjectArrayElement(array, i, env->NewObject(cls, construct, env->NewStringUTF(movies[i]->name.c_str()), movies[i]->lastUpdated));

    }

    return array;
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_example_myapplication_MovieDetailActivity_getMovieDetailJNI (
        JNIEnv *env,
        jobject obj,
        jstring id) {

    const char *cstr = env->GetStringUTFChars(id, NULL);
    movies::MovieDetail* det = controller.getMovieDetail(cstr);

    jclass detail = env->FindClass("com/example/myapplication/data/MovieDetail");
    jmethodID setDetail = env->GetMethodID(detail, "<init>", "([Lcom/example/myapplication/data/Actor;Ljava/lang/String;Ljava/lang/String;F)V");
    jobject detailObj;

    jclass actorDetail = env->FindClass("com/example/myapplication/data/Actor");
    jmethodID actorSetDetail = env->GetMethodID(actorDetail, "<init>", "(Ljava/lang/String;Ljava/lang/String;I)V");
    jobjectArray actor = env->NewObjectArray(det->actors.size(), actorDetail, nullptr);


    for (int i= 0; i < det->actors.size(); i++) {
        env->SetObjectArrayElement(actor, i, env->NewObject(actorDetail, actorSetDetail, env->NewStringUTF(det->actors[i].name.c_str()), env->NewStringUTF(det->actors[i].imageUrl.c_str()), det->actors[i].age));
    }

    if (det != NULL) {
        detailObj = env->NewObject(detail, setDetail, actor, env->NewStringUTF(det->name.c_str()), env->NewStringUTF(det->description.c_str()), det->score);
    }

    return detailObj;
};
