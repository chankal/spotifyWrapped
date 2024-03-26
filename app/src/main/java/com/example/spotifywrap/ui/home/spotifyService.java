package com.example.spotifywrap.ui.home;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import okhttp3.ResponseBody;

// define API endpoints
public interface spotifyService {
    @GET("v1/endpoint")
    Call<ResponseBody> callEndpoint(@Header("Authorization") String accessToken);

    /*
    Endpoints needed :

    - get users playlist + tracks
    -





     */

}
