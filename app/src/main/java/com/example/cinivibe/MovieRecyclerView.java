package com.example.cinivibe;

public class MovieRecyclerView {
    private int image;
    private String title;
    public MovieRecyclerView(int image, String title) {
        this.image = image;
        this.title = title;
    }
    public int getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }

    }
