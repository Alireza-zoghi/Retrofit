package com.alirezazoghi.retrofit.app;


import android.annotation.SuppressLint;
import android.content.Context;

import com.alirezazoghi.retrofit.retrofit.API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Application extends android.app.Application {

    private static Retrofit retrofit;
    private static API api;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson=new GsonBuilder().create();
        OkHttpClient client=new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(app.main.URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(API.class);

    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }

    public static API getAPI(){
        return api;
    }
}
