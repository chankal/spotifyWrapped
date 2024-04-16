package com.example.spotifywrap.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotifywrap.R;

import java.util.ArrayList;

public class TopArtists extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton backButton;
    private ArtistAdapter adapter;
    private ArrayList<Artist> topArtists;
    private SongService songService ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_artists);


        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewTopArtists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.backButtonArtist);
        songService = new SongService(getApplicationContext());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getTopArtists();
    }

    private void getTopArtists() {
        songService.getTopArtists(() -> {
            topArtists = songService.getArtists();
            updateArtists();
        });

    }

    private void updateArtists() {
        if (topArtists.size()>0) {
            adapter = new ArtistAdapter(this, topArtists);
            recyclerView.setAdapter(adapter);

        } else {
            Toast.makeText(this, "No artists found", Toast.LENGTH_SHORT).show();
        }
    }

}
