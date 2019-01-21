package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.adapters.MovieListAdapter;
import com.example.myapplication.data.Movie;
import com.example.myapplication.data.MovieDetail;

public class MainActivity extends AppCompatActivity {

    MovieListAdapter movieAdapter;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    public native Movie[] getMoviesJNI();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Movie[] movies = getMoviesJNI();

        movieAdapter = new MovieListAdapter(this, movies);
        ListView listView = (ListView)findViewById(R.id.main_list_movies);
        listView.setAdapter(movieAdapter);

    }




}
