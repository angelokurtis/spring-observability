package br.dev.kurtis.clientapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MatchClient {
    @GET("/matches/{matchId}")
    Call<Match> requestMatch(@Path("matchId") Integer matchId);
}
