package com.example.myapplication.data;

public class Movie {
    public String name;
    public int lastUpdated;

    public Movie() {
        this.name = "Test";
    }

    public Movie(String name) {
        this.name = name;
    }
    public Movie(String name, int last) {
        this.name = name;
        this.lastUpdated = last;
    }

    public void setMovieName(String s, int i) {
        this.name = s;
        this.lastUpdated = i;
    }
}
