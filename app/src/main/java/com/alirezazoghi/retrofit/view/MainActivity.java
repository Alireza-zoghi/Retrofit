package com.alirezazoghi.retrofit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alirezazoghi.retrofit.adapter.ActorsAdapter;
import com.alirezazoghi.retrofit.app.Application;
import com.alirezazoghi.retrofit.app.app;
import com.alirezazoghi.retrofit.model.Actors;
import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.tools.onRecyclerViewItemClick;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements onRecyclerViewItemClick {

    private RecyclerView recyclerView;
    private ActorsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Actor");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ActorsAdapter(this);
        recyclerView.setAdapter(adapter);

        getActors();
    }

    private void getActors() {
        Application.getAPI().getActors().enqueue(new Callback<List<Actors>>() {
            @Override
            public void onResponse(Call<List<Actors>> call, Response<List<Actors>> response) {
                if (response.body() != null) {
                    adapter.Submit(response.body());
                } else {
                    app.l("null response");
                }
            }

            @Override
            public void onFailure(Call<List<Actors>> call, Throwable t) {
                app.l(t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(MainActivity.this, AddActorActivity.class));
                break;
            case R.id.download:
                startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                break;
        }

        return false;
    }

    @Override
    public void onItemClick(Actors actors, ImageView imageView) {
        Intent intent = new Intent(MainActivity.this, ActorActivity.class);
        intent.putExtra("actors", actors);
        intent.putExtra("transaction", ViewCompat.getTransitionName(imageView));

        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this,
                imageView,
                Objects.requireNonNull(ViewCompat.getTransitionName(imageView))
        );
        startActivity(intent, compat.toBundle());
    }
}
