package com.example.spotifywrap.ui.home;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class serviceClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.spotify.com/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
