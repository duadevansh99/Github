package com.example.de.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReposActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<Repos> repos=new ArrayList<>();
    ArrayList<String> repoTitles=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);
        listView=findViewById(R.id.listView);
        progressBar=findViewById(R.id.progressBarRepos);
        listView.setVisibility(View.GONE);

        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,repoTitles);
        listView.setAdapter(adapter);

        Intent intent=getIntent();
        String userName=intent.getStringExtra(MainActivity.REPOS);

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        UserService service=retrofit.create(UserService.class);
        Call<ArrayList<Repos>> call=service.getRepoTitle(userName);
        call.enqueue(new Callback<ArrayList<Repos>>() {
            @Override
            public void onResponse(Call<ArrayList<Repos>> call, Response<ArrayList<Repos>> response) {

                repos=response.body();
                repoTitles.clear();
                for(int i=0;i<repos.size();i++){
                    repoTitles.add(repos.get(i).name);
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<Repos>> call, Throwable t) {

            }
        });


    }
}
