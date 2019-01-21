package com.example.myapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.adapters.ActorListAdapter;
import com.example.myapplication.data.MovieDetail;

public class MovieDetailActivity extends AppCompatActivity {

    MovieDetail movieDetail;
    ActorListAdapter actorListAdapter;

    public native MovieDetail getMovieDetailJNI(String i);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle b = getIntent().getExtras();
        String movieTitle = b.getString("MOVIE_TITLE");

        MovieDetailActivity.this.setTitle(movieTitle);
        movieDetail = getMovieDetailJNI(movieTitle);

        actorListAdapter = new ActorListAdapter(MovieDetailActivity.this, movieDetail.actors);
        ListView list = findViewById(R.id.detail_actors);
        list.setAdapter(actorListAdapter);

        TextView desc = findViewById(R.id.detail_description);
        desc.setText(movieDetail.description);

        TextView score = findViewById(R.id.detail_score);
        score.setText("Score: " + String.valueOf(movieDetail.score));


    }
}
