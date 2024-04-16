package com.example.spotifywrap.ui.home;

public class Artist {
    private String name ;
    private String imageUrl = "" ;

    public Artist(String name) {
        this.name = name ;

    }
    public Artist(String name, String imageUrl) {
        this.name = name ;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImageUrl() { return this.imageUrl; }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl ;
    }







}
