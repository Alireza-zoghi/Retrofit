package com.alirezazoghi.retrofit.App;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.FileUtil;
import androidx.room.util.StringUtil;

import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.Adapter.ActorsAdapter;
import com.alirezazoghi.retrofit.Model.Actors;

import java.io.File;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Actors> actorsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Actor");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ActorsAdapter(MainActivity.this, actorsList));

        getActors();
    }

    private void getActors() {
        Application.getAPI().getActors().enqueue(new Callback<List<Actors>>() {
            @Override
            public void onResponse(Call<List<Actors>> call, Response<List<Actors>> response) {
                if (response != null) {
                    app.l(response.body() + " size ");
                    actorsList.clear();
                    actorsList.addAll(response.body());
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
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(MainActivity.this, AddActorActivity.class));
        }
        return false;
    }
}
