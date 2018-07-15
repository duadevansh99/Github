package com.example.de.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FollowersActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<Follower> followersNet=new ArrayList<>();
    ArrayList<Follower> followers=new ArrayList<>();
    FollowersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        listView=findViewById(R.id.listViewFollowers);
        progressBar=findViewById(R.id.progressBarFollowers);

        listView.setVisibility(View.GONE);

        adapter=new FollowersAdapter(this,followers);
        listView.setAdapter(adapter);

        Intent intent=getIntent();
        String userName=intent.getStringExtra(MainActivity.FOLLOWERS);

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        UserService service=retrofit.create(UserService.class);
        Call<ArrayList<Follower>> call=service.getFollowers(userName);
        call.enqueue(new Callback<ArrayList<Follower>>() {
            @Override
            public void onResponse(Call<ArrayList<Follower>> call, Response<ArrayList<Follower>> response) {
                followersNet=response.body();
                followers.clear();
                for(int i=0;i<followersNet.size();i++){
                    followers.add(followersNet.get(i));
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Follower>> call, Throwable t) {

            }
        });


    }
}
