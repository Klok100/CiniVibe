package com.example.cinivibe;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieRecyclerView implements Parcelable {
    private String image;
    private String title;
    private String description;
    private int [] genres;
    private double rating;
    private String releaseDate;

    public MovieRecyclerView(String image, String title, String description, int [] genres, double rating, String releaseDate) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public MovieRecyclerView(Parcel in){

        image = in.readString();
        title = in.readString();
        description = in.readString();
        genres = in.createIntArray();
        rating = in.readDouble();
        releaseDate = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeIntArray(genres);
        dest.writeDouble(rating);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieRecyclerView> CREATOR = new Creator<MovieRecyclerView>() {
        @Override
        public MovieRecyclerView createFromParcel(Parcel in) {
            return new MovieRecyclerView(in);
        }

        @Override
        public MovieRecyclerView[] newArray(int size) {
            return new MovieRecyclerView[size];
        }
    };

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int [] getArray() {return genres; }

    public double getRating() {return rating; }

    public String getReleaseDate() {return releaseDate; }

    public void setTitle(String title){
        this.title = title;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setArray(int [] genres) {this.genres = genres;}

    public void setRating(double rating) {this.rating = rating;}

    public void setReleaseDate(String releaseDate) {this.releaseDate = releaseDate;}

}
