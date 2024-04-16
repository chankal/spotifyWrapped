package com.example.spotifywrap.ui.home;

import android.content.SharedPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TopInfo extends UserInfo {
    private static final String URL = "https://api.spotify.com/v1/me/top/tracks";
    private TopTracks topTracks;

    private static final String URL_ARTISTS = "https://api.spotify.com/v1/me/top/artists";
    private TopArtists topArtists;
    
    public TopInfo(RequestQueue queue, SharedPreferences sharedPreferences) {
        super(queue, sharedPreferences);
    }

    public TopArtists getTopArtists() {
        return topArtists;
    }

    public TopTracks getTopTracks() {
        return topTracks;
    }

    @Override
    public void get(final VolleyCallBack callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL,
                null, response -> {
            Gson gson = new Gson();
            topTracks = gson.fromJson(response.toString(), TopTracks.class);
            callBack.onSuccess();
        }, error -> get(() -> {})) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                //ResourceBundle sharedPreferences;
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }



    public void getTopArtists(final VolleyCallBack callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_ARTISTS,
                null, response -> {
            Gson gson = new Gson();
            topArtists = gson.fromJson(response.toString(), TopArtists.class);
            callBack.onSuccess();
        }, error -> getTopArtists(() -> {})) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = sharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public Map<String, Integer> getTopGenres() {
        Map<String, Integer> genreCounts = new HashMap<>();
        for (Artist artist : topArtists.getItems()) {
            for (String genre : artist.getGenres()) {
                genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
            }
        }
        return genreCounts;
    }
}