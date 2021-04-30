package com.example.chucknorrisio.datasource;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;

import com.example.chucknorrisio.model.Joke;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JokeRemoteDataSource {


    public void findJokeBy(JokeCallback jokeCallback, String category) {
        HTTPClient.retrofit().create(ChuckNorrisAPI.class)
                .findRandomBy(category)
                .enqueue(new Callback<Joke>() {

                @Override
                public void onResponse(Call<Joke> call, Response<Joke> response) {
                if(response.isSuccessful()){
                    jokeCallback.onSuccess(response.body());

                }
                jokeCallback.onComplete();

            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                jokeCallback.onError(t.getMessage());
                jokeCallback.onComplete();
            }
        });

    }

    public interface JokeCallback {
        void onSuccess(Joke joke);

        void onError(String message);

        void onComplete();

    }
}
