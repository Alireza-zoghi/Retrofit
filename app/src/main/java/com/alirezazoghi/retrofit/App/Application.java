package com.alirezazoghi.retrofit.App;


import android.annotation.SuppressLint;
import android.content.Context;

import com.alirezazoghi.retrofit.Retrofit.API;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Application extends android.app.Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static Retrofit retrofit;
    private static API api;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        Gson gson=new GsonBuilder().create();
        OkHttpClient client=new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(app.main.URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(API.class);
    }

    public static Context getContext() {
        return context;
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }

    public static API getAPI(){
        return api;
    }
}
