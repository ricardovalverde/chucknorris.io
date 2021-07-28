package com.example.chucknorrisio.datasource;

import com.example.chucknorrisio.model.Joke;

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
                        if (response.isSuccessful()) {
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
