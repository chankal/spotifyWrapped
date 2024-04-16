package com.example.spotifywrap.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongService {
    private ArrayList<Song> songs = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private RequestQueue queue;

    public SongService(Context context) {
        sharedPreferences = context.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(context);
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public ArrayList<Song> getRecentlyPlayedTracks(final VolleyCallBack callBack) {
        String endpoint = "https://api.spotify.com/v1/me/player/recently-played";
        //filter get unique one -> wrap up
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, endpoint, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");
                    Log.d("STARTING", "list of songs" + jsonArray);


                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            object = object.optJSONObject("track");
                            JSONObject albumObject = object.getJSONObject("album");
                            JSONArray artistsArray = object.getJSONArray("artists");
                            List<Artist> artists = new ArrayList<>();
                            for (int i = 0; i < artistsArray.length(); i++) {
                                JSONObject artistObject = artistsArray.getJSONObject(i);
                                String artistName = artistObject.getString("name");
                                String imageUrl = ""; // You can add image URL retrieval here if needed
                                Artist artist = new Artist(artistName, imageUrl);
                                artists.add(artist);
                            }


//                            JSONArray artistsArray = object.getJSONArray("artists");
//                            String artistName = artistsArray.getJSONObject(0).getString("name");
//                            Log.d("STARTING", "ARTIST" + artistName);
//
//                            Artist a = new Artist(artistName) ;
                            JSONArray imagesArray = albumObject.getJSONArray("images");
                            String imageUrl = imagesArray.getJSONObject(0).getString("url");
                            Log.d("STARTING", "GOT SONGS" + object.toString());

                            Song song = gson.fromJson(object.toString(), Song.class);


//                            song.setArtist(a);
                            song.setArtists(artists);

                            song.setImageUrl(imageUrl);

                            songs.add(song);
                            Log.d("STARTING", "Added songs");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("STARTING", "oops error" + e.toString());
                        }
                    }
                    callBack.onSuccess();
                }, error -> {

                }) {
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
        return songs;
    }


    public ArrayList<Song> getTopTracks(final VolleyCallBack callBack) {
        String url = "https://api.spotify.com/v1/me/top/tracks";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, response -> {
                    Gson gson = new Gson();
                    JSONArray jsonArray = response.optJSONArray("items");
                    Log.d("STARTING", "list of songs" + jsonArray);


                    for (int n = 0; n < jsonArray.length(); n++) {
                        try {
                            JSONObject object = jsonArray.getJSONObject(n);
                            object = object.optJSONObject("track");
                            JSONObject albumObject = object.getJSONObject("album");
                            JSONArray artistsArray = object.getJSONArray("artists");
                            List<Artist> artists = new ArrayList<>();
                            for (int i = 0; i < artistsArray.length(); i++) {
                                JSONObject artistObject = artistsArray.getJSONObject(i);
                                String artistName = artistObject.getString("name");
                                String imageUrl = ""; // You can add image URL retrieval here if needed
                                Artist artist = new Artist(artistName, imageUrl);
                                artists.add(artist);
                            }


                            JSONArray imagesArray = albumObject.getJSONArray("images");
                            String imageUrl = imagesArray.getJSONObject(0).getString("url");
                            Log.d("STARTING", "GOT SONGS" + object.toString());


                            Song song = gson.fromJson(object.toString(), Song.class);

                            song.setArtists(artists);

                            song.setImageUrl(imageUrl);

                            songs.add(song);
                            Log.d("STARTING", "Added songs");

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("STARTING", "oops error" + e.toString());
                        }
                    }
                    callBack.onSuccess();
                }, error -> {

                }) {
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
        return songs;
    }

    public interface VolleyCallBack {
        void onSuccess();
    }


}

