package com.example.cinivibe;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieRecyclerView implements Parcelable {
    private String image;
    private String title;
    private String description;

    public MovieRecyclerView(String image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public MovieRecyclerView(Parcel in){

        image = in.readString();
        title = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(title);
        dest.writeString(description);
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

    public void setTitle(String title){
        this.title = title;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setDescription(String description){
        this.description = description;
    }

}
