package com.example.tmdbapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailMoviePage extends AppCompatActivity {

    Intent i;
    MovieModel movieModel;
    TextView  tvMovieName, tvOverview, tvReleaseDate ;
    private ImageView ivBackdropPath;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie_page);

        i = getIntent();
        movieModel = (MovieModel) i.getParcelableExtra("myMovie");
        tvMovieName = findViewById(R.id.tvmoviename);
        tvOverview = findViewById(R.id.tvoverview);
        tvReleaseDate = findViewById(R.id.tvreleasedate);
        ivBackdropPath = findViewById(R.id.ivbackdroppath);

        tvMovieName.setText(movieModel.getMovieName());
        tvOverview.setText(movieModel.getOverview());
        tvReleaseDate.setText(movieModel.getReleaseDate());
        Glide.with(this).load("https://image.tmdb.org/t/p/original" + movieModel.getBackdropPath()).into(ivBackdropPath);
    }
}