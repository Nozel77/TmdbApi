package com.example.tmdbapi;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieModel implements Parcelable {
    private String MovieName;
    private String Overview;

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    private String ReleaseDate;
    public String getBackdropPath() {
        return BackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        BackdropPath = backdropPath;
    }

    private String BackdropPath;


    private String posterPath;

    MovieModel(){

    }

    protected MovieModel(Parcel in) {
        MovieName = in.readString();
        BackdropPath = in.readString();
        Overview = in.readString();
        ReleaseDate = in.readString();
        PosterPath = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    private String PosterPath;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(MovieName);
        parcel.writeString(BackdropPath);
        parcel.writeString(Overview);
        parcel.writeString(ReleaseDate);
        parcel.writeString(PosterPath);
    }
}
