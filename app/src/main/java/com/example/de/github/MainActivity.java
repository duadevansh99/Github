package com.example.de.github;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button searchButton;
    User user;
    ProgressBar progressBar;
    TextView name,email,ID,followers,following,repos;
    ImageView avatar;
    String userSearch;
    public static final String REPOS="sending username to repos";
    public static final String FOLLOWERS="sending username to followers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editText);
        searchButton=findViewById(R.id.searchbutton);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        ID=findViewById(R.id.ID);
        followers=findViewById(R.id.followers);
        following=findViewById(R.id.following);
        avatar=findViewById(R.id.avatarImage);
        repos=findViewById(R.id.repositories);
    }

    public void startFetch(View view){

        progressBar.setVisibility(View.VISIBLE);

        Retrofit.Builder builder=new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit=builder.build();

        UserService service=retrofit.create(UserService.class);
        userSearch=editText.getText().toString();
        Call<User> call=service.getUserDetails(userSearch);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                user=response.body();

                if(response.body()==null){
                    Toast.makeText(MainActivity.this,"ERROR! Check Username",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else{
                    Picasso.get().load(user.dp).into(avatar);
                    name.setText("Name- " + user.name);
                    email.setText("E-Mail - " + user.email);
                    ID.setText("ID- "+ user.id);
                    followers.setText("Followers- " + user.followers);
                    following.setText("Following- " + user.following);
                    repos.setText("Repositories- "+ user.public_repos);
                    progressBar.setVisibility(View.GONE);
                }

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    public void goToRepos(View view){

        Intent intent=new Intent(this,ReposActivity.class);
        intent.putExtra(REPOS,userSearch);
        startActivity(intent);

    }

    public void goToFollowers(View view){

        Intent intent=new Intent(this,FollowersActivity.class);
        intent.putExtra(FOLLOWERS,userSearch);
        startActivity(intent);

    }
}
