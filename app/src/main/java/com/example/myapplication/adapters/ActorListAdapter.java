package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.MovieDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.data.Actor;
import com.example.myapplication.data.Movie;
import com.squareup.picasso.Picasso;

public class ActorListAdapter extends BaseAdapter {

    Context ctx;
    Actor[] actor;


    public ActorListAdapter(Context context, Actor[] actors) {
        this.ctx = context;
        this.actor = actors;
    }

    @Override
    public int getCount() {
        return actor.length;
    }

    @Override
    public Object getItem(int position) {
        return actor[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.detail_actor_list, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        final Actor current = (Actor) getItem(position);
        viewHolder.itemName.setText(current.name);
        viewHolder.itemAge.setText(current.age + " Years Old");
        try {
            if (!current.imageUrl.isEmpty()) {
                Log.e("TAG", "IMAGEURL IS NOT EMPTY " + current.imageUrl);
                Picasso.get().load(current.imageUrl).into(viewHolder.itemImage);
            } else {
                Log.e("TAG", "IMAGEURL IS EMPTY");
                viewHolder.itemImage.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.person_identity2));
            }
        } catch (Exception e) {

        }

        return convertView;
    }

    private class ViewHolder {
        TextView itemName;
        TextView itemAge;
        ImageView itemImage;

        public ViewHolder(View view) {
            itemName = (TextView)view.findViewById(R.id.detail_actor_name);
            itemAge = (TextView)view.findViewById(R.id.detail_actor_age);
            itemImage = (ImageView)view.findViewById(R.id.detail_actor_image);
        }
    }
}
