package com.example.chucknorrisio.model;

import com.google.gson.annotations.SerializedName;

public class Joke {

    @SerializedName("value")
    private String joke;

    @SerializedName("icon_url")
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
