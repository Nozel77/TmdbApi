package com.example.tmdbapi;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
                                MovieModel myMovie = new MovieModel();
                                JSONObject jsonMovie = jsonArrayMovie.getJSONObject(i);
                                myMovie.setMovieName(jsonMovie.getString("original_title"));
                                myMovie.setOverview(jsonMovie.getString("overview"));
                                myMovie.setReleaseDate(jsonMovie.getString("release_date"));
                                myMovie.setBackdropPath(jsonMovie.getString("backdrop_path"));
                                myMovie.setPosterPath(jsonMovie.getString("poster_path"));
                                listDataMovie.add(myMovie);
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
    public void onContactSelected(MovieModel myMovie) {
        Intent intent = new Intent(ListMovieActivity.this,DetailMoviePage.class);
        intent.putExtra("myMovie",myMovie);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListMovieActivity.this);
        builder.setTitle("Perhatian!")
                .setMessage("Apakah kamu yakin ingin menghapus item ini?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Tindakan yang dilakukan ketika tombol OK diklik
                        listDataMovie.remove(position);
                        adapterMovie.notifyItemRemoved(position);
                        Toast.makeText(ListMovieActivity.this.getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Tindakan yang dilakukan ketika tombol Batal diklik
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

