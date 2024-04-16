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
import com.example.spotifywrap.ui.home.Song;
import com.example.spotifywrap.ui.home.SongAdapter;
import com.example.spotifywrap.ui.home.SongService;

public class TopTracksActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton backButton;
    private SongService songService;
    private SongAdapter adapter;
    private ArrayList<Song> topTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_songs);

        songService = new SongService(getApplicationContext());

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.backRecentSongs);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getTopTracks();
    }

    private void getTopTracks() {
        songService.getTopTracks(() -> {
            topTracks = songService.getSongs();
            updateSong();
        });
    }
    private void updateSong() {

        if (topTracks.size() > 0) {
            adapter = new SongAdapter(this, topTracks);
            recyclerView.setAdapter(adapter);
        }
    }


}

