package com.example.chucknorrisio.presentation;

import com.example.chucknorrisio.JokeActivity;
import com.example.chucknorrisio.datasource.JokeRemoteDataSource;
import com.example.chucknorrisio.model.Joke;

public class JokePresenter implements JokeRemoteDataSource.JokeCallback {
    private final JokeActivity view;
    private final JokeRemoteDataSource dataSource;

    public JokePresenter(JokeActivity jokeActivity, JokeRemoteDataSource jokeRemoteDataSource) {
        this.view = jokeActivity;
        this.dataSource = jokeRemoteDataSource;
    }

    public void findJokeBy(String category) {
        this.view.showProgressBar();
        this.dataSource.findJokeBy(this, category);

    }

    @Override
    public void onSuccess(Joke joke) {
        this.view.showJoke(joke);
    }

    @Override
    public void onError(String message) {
        this.view.showFailure(message);
    }

    @Override
    public void onComplete() {
        this.view.hideProgressBar();
    }
}
