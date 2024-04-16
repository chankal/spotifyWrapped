package com.example.spotifywrap;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotifywrap.R;
import com.example.spotifywrap.ui.home.Login;
import com.example.spotifywrap.ui.home.RecentSongs;
import com.example.spotifywrap.ui.home.TopTracksActivity;
import com.example.spotifywrap.ui.home.User;

public class MainActivity extends AppCompatActivity {

    private Button buttonSpotifyLogin;
    private TextView greetingMessage;
    boolean isAuthenticated = false;
    private static final int LOGIN_REQUEST_CODE = 1001;
    private SharedPreferences sharedPreferences;
    private Button buttonRecentlyPlayedSong;
    private Button buttonYourWrap;
    private Button buttonMusicTaste;
    private Button buttonDuoWrapped;
    private Button buttonTopTracks;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSpotifyLogin = findViewById(R.id.buttonSpotifyLogin);
        greetingMessage = findViewById(R.id.greetingMessage);
        buttonRecentlyPlayedSong = findViewById(R.id.buttonRecentlyPlayedSong);
        buttonYourWrap = findViewById(R.id.buttonYourWrap);
        buttonMusicTaste = findViewById(R.id.buttonMusicTaste);
        buttonDuoWrapped = findViewById(R.id.buttonDuoWrapped);
        sharedPreferences = getSharedPreferences("SPOTIFY", MODE_PRIVATE);
        buttonTopTracks = findViewById(R.id.buttonTopTracks);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        greetingMessage.setText(sharedPreferences.getString("userid", "No User"));

        buttonSpotifyLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                intent.putExtra("LOGIN_REQUEST_CODE", LOGIN_REQUEST_CODE); // Add request code to the intent
                startActivityForResult(intent, LOGIN_REQUEST_CODE);
            }
        });

        buttonRecentlyPlayedSong.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RecentSongs.class);
            startActivityForResult(intent, 1);
        }

        });

        buttonTopTracks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TopTracksActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE) {

            isAuthenticated = data.getBooleanExtra("isAuthenticated", false);
            if (isAuthenticated) {
                Log.d("STARTING", "Got authenticated");
                showGreetingMessage();
                sharedPreferences = getSharedPreferences("SPOTIFY", 0);
                isAuthenticated = sharedPreferences.getBoolean("isAuthenticated", false);
                String token = sharedPreferences.getString("token", "");
                String userId = sharedPreferences.getString("userid", "");
                String username = sharedPreferences.getString("username", "");
                showGreetingMessage();
                greetingMessage.setText("Welcome," + sharedPreferences.getString("username", "No User"));

                showAuthenticatedViews();

            } else {
                Log.d("STARTING", "Not authenticated");
                showLoginButton();
            }
        }

    }

    private void showAuthenticatedViews() {
        greetingMessage.setVisibility(View.VISIBLE);
        greetingMessage.setText("Welcome," + sharedPreferences.getString("username", "No User"));
        buttonSpotifyLogin.setVisibility(View.GONE);
        buttonRecentlyPlayedSong.setVisibility(View.VISIBLE);
        buttonYourWrap.setVisibility(View.VISIBLE);
        buttonMusicTaste.setVisibility(View.VISIBLE);
        buttonDuoWrapped.setVisibility(View.VISIBLE);
        buttonTopTracks.setVisibility(View.VISIBLE);
    }
    private void showGreetingMessage() {
        greetingMessage.setVisibility(View.VISIBLE);
        buttonSpotifyLogin.setVisibility(View.GONE);
    }

    private void showLoginButton() {
        greetingMessage.setVisibility(View.GONE);
        buttonSpotifyLogin.setVisibility(View.VISIBLE);
    }
    protected void onDestroy() {
        super.onDestroy();
    }

}
