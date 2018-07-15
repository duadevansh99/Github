package com.example.de.github;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{login}")
    Call<User> getUserDetails(@Path("login") String login);

    @GET("users/{login}/repos")
    Call<ArrayList<Repos>> getRepoTitle(@Path("login") String login);

    @GET("users/{login}/followers")
    Call<ArrayList<Follower>> getFollowers(@Path("login") String login);

}
