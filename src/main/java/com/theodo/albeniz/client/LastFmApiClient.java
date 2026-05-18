package com.theodo.albeniz.client;

import retrofit2.http.Query;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LastFmApiClient {

    @GET("/2.0/?method=track.getInfo&format=json")
    Call<TrackDto> getProjects(
            @Query("api_key") String api_key,
            @Query("artist") String artist,
            @Query("track") String track);
}
