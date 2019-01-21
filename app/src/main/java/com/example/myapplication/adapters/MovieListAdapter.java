package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MovieDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Movie;
import com.example.myapplication.data.MovieDetail;

import java.util.ArrayList;

public class MovieListAdapter extends BaseAdapter {

    public Context ctx;
    public Movie[] movieList;

    public MovieListAdapter(Context context, Movie[] items) {
        this.ctx = context;
        this.movieList = items;
    }

    @Override
    public int getCount() {
        return movieList.length;
    }

    @Override
    public Object getItem(int position) {
        return movieList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.main_movie_list, parent, false);
        }

        final Movie current = (Movie) getItem(position);

        TextView title = (TextView)convertView.findViewById(R.id.list_movie_title);
        title.setText(current.name);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bun = new Bundle();
                bun.putString("MOVIE_TITLE", current.name);
                Intent intent = new Intent(ctx, MovieDetailActivity.class);
                intent.putExtras(bun);
                ctx.startActivity(intent);
            }
        });

        return convertView;
    }
}
