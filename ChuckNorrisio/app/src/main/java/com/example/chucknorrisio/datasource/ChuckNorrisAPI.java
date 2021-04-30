package com.example.chucknorrisio.datasource;

import com.example.chucknorrisio.model.Joke;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChuckNorrisAPI {
    String BASE_URL = Endpoint.BASE_URL;

    @GET("jokes/categories")
    Call<List<String>> findAll();


    @GET("jokes/random")
    Call<Joke> findRandomBy(@Query("category") String category);

}
