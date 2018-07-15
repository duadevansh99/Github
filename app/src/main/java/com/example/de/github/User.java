package com.example.de.github;

import com.google.gson.annotations.SerializedName;

public class User {

    String name,email,following_url,followers_url;
    long id;
    int followers,following,public_repos;
    @SerializedName("avatar_url")
    String dp;
}
