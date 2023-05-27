package com.example.tmdbapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";

    public static final String EMAIL_KEY = "email_key";

    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;
    String username, password;

    //list widget yang akan dikenakan aksi
    EditText txtUsername;
    EditText txtPassword;
    Button btnLogin;
    ProgressBar pbLadingBar;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // konekkan semua komponen dengan xml nya
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnPassword);
        pbLadingBar = findViewById(R.id.pbloadingBar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = txtUsername.getText().toString();
                password = txtPassword.getText().toString();
                pbLadingBar.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(false);

                // hit api login
                AndroidNetworking.post("https://mediadwi.com/api/latihan/login")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", password)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle successful response
                                Log.d("sukses login", "onResponse: " + response.toString());
                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    if (status) {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, username.toString());
                                        editor.putString(PASSWORD_KEY, "");

                                        // to save our data with key and value.
                                        editor.apply();
                                        startActivity(new Intent(MainActivity.this, ListMovieActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                pbLadingBar.setVisibility(View.GONE);
                                btnLogin.setEnabled(true);
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            }
        });
        googleBtn = findViewById(R.id.google_btn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            ListMovieActivity();
        }


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                ListMovieActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void ListMovieActivity() {
        finish();
        Intent intent = new Intent(MainActivity.this, ListMovieActivity.class);
        startActivity(intent);
    }
}
