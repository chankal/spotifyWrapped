package com.example.spotifywrap.ui.home;

import java.util.List;

public class TopArtists {
    private List<Artist> items;
    // other fields...

    // getters and setters
    public List<Artist> getItems() {
        return items;
    }
}

class Artist {
    private String name;
    private List<String> genres;
    // other fields...

    // getters and setters
    public String getName() {
        return name;
    }
    public List<String> getGenres() {
        return genres;
    }

}