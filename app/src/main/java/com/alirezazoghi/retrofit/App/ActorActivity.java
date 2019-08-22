package com.alirezazoghi.retrofit.App;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.os.Handler;

import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.Model.Actors;
import com.squareup.picasso.Picasso;


import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorActivity extends AppCompatActivity {


    private AppCompatTextView name, age, description;
    private CircleImageView image;
    private int id = -1;
    private Actors actors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);

        id = getIntent().getIntExtra("id", -1);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        description = findViewById(R.id.description);
        image = findViewById(R.id.Image);
        getActor(id);

    }

    private void getActor(int id) {
        Application.getAPI().getActorByID(id).enqueue(new Callback<Actors>() {
            @Override
            public void onResponse(Call<Actors> call, Response<Actors> response) {
                name.setText(response.body().getFname() + " " + response.body().getLname());
                age.setText(response.body().getAge() + "");
                description.setText(response.body().getStatus());
                Picasso.get().load(app.main.URL + response.body().getImage()).into(image);
            }

            @Override
            public void onFailure(Call<Actors> call, Throwable t) {

            }
        });
    }

    private void getActorMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("id", id);

        Application.getAPI().getActorByMap(map).enqueue(new Callback<Actors>() {
            @Override
            public void onResponse(Call<Actors> call, Response<Actors> response) {
                name.setText(response.body().getFname() + " " + response.body().getLname());
                age.setText(response.body().getAge() + "");
                description.setText(response.body().getStatus());

                actors = response.body();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edit();
                    }
                }, 5000);
            }

            @Override
            public void onFailure(Call<Actors> call, Throwable t) {

            }
        });
    }

    private void edit() {
        Application.getAPI().editUser(actors).enqueue(new Callback<Actors>() {
            @Override
            public void onResponse(Call<Actors> call, Response<Actors> response) {
                name.setText(response.body().getFname() + " " + response.body().getLname());
                age.setText(response.body().getAge() + "");
                description.setText(response.body().getStatus());
                Picasso.get().load(app.main.URL + response.body().getImage()).into(image);
            }

            @Override
            public void onFailure(Call<Actors> call, Throwable t) {

            }
        });

    }
}
