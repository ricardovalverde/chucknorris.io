package com.example.chucknorrisio.model;

public class Joke {
    private String joke;
    private String iconUrl;

    public Joke(String joke, String iconUrl) {
        this.joke = joke;
        this.iconUrl = iconUrl;
    }

    public String getJoke() {
        return joke;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}
