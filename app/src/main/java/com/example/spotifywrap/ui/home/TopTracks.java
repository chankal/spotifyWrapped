package com.example.spotifywrap.ui.home;

import java.util.List;

public class TopTracks {
    private List<Track> items;
    private int total;
    private int limit;
    private int offset;

    // getters and setters
}

class Track {
    private String name;
    private Album album;
    private List<Artist> artists;
    private int duration_ms;

    // getters and setters

}

class Album {
    private String name;

    // getters and setters

    public String getName() {
        return name;
    }
}


