package com.example.tmdbapi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListMovieActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterListener {
    RecyclerView rvMovieName;
    ArrayList<MovieModel> listDataMovie;
    private MovieAdapter adapterMovie;
    private ProgressBar progressBar;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    public void getMovieList(){
        String url = "https://api.themoviedb.org/3/discover/movie?include_adult=false&include_video=false&language=en-US&page=1&sort_by=popularity.desc&api_key=baa0cfef49e2a982e25aaf900ab30b91";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("succes", "onResponse: "+jsonObject.toString());
                        try {
                            JSONArray jsonArrayMovie = jsonObject.getJSONArray("results");
                            for (int i = 0; i <jsonArrayMovie.length() ; i++) {
                                MovieModel myTeam = new MovieModel();
                                JSONObject jsonMovie = jsonArrayMovie.getJSONObject(i);
                                myTeam.setMovieName(jsonMovie.getString("original_title"));
                                myTeam.setOverview(jsonMovie.getString("release_datex"));
                                myTeam.setPosterPath(jsonMovie.getString("poster_path"));
                                listDataMovie.add(myTeam);
                            }

                            rvMovieName = findViewById(R.id.rvMovieName);

                            adapterMovie = new MovieAdapter(getApplicationContext(),listDataMovie, ListMovieActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvMovieName.setHasFixedSize(true);
                            rvMovieName.setLayoutManager(mLayoutManager);
                            rvMovieName.setAdapter(adapterMovie);

                            progressBar.setVisibility(View.GONE);
                            rvMovieName.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("failed", "onError: "+anError.toString());
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie_actvity);
        progressBar = findViewById(R.id.progressBar); // Ganti R.id.progressBar dengan ID ProgressBar yang sesuai dengan layout Anda



        listDataMovie = new ArrayList<>();



        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        getMovieList();

    }

    @Override
    public void onContactSelected(MovieModel contact) {

    }
}
