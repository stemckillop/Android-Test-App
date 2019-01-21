package com.example.myapplication.data;

public class MovieDetail {
    public String name;
    public float score;
    public Actor[] actors;
    public String description;

    public MovieDetail(Actor[] act, String name, String desc, float score) {
        this.actors = act;
        this.name = name;
        this.score = score;
        this.description = desc;
    }
}
