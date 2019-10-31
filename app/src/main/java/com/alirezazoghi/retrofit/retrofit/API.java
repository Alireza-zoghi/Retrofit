package com.alirezazoghi.retrofit.retrofit;

import com.alirezazoghi.retrofit.model.Actors;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface API {

    @GET("users.php")
    Call<List<Actors>> getActors();

    //Dynamic url
    @GET
    Call<List<Actors>> getActorsDynamic(@Url String url);

    @GET("users.php")
    Call<Actors> getActorByID(@Query("id") int id);

    @GET("{file}.php")
    Call<List<Actors>> getActors(@Path("file") String file);

    @GET("users.php")
    Call<Actors> getActorByMap(@QueryMap Map<String, Integer> option);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadePhoto(
            // TODO: 8/22/2019 @Multipart
    );

    @FormUrlEncoded
    @GET("users.php")
    Call<Actors> getActorByIdForm(@Field("id") int id);

    @Headers({
            "User-Agent: Alireza Zoghi"
    })
    @GET("header.php")
    Call<String> getUserAgent();

    @FormUrlEncoded
    @POST("upload.php")
    Call<String> upload(@Field("fname") String fname, @Field("lname") String lname, @Field("age") String age, @Field("image") String image, @Field("status") String status);

    @Streaming
    @GET()
    Call<ResponseBody> downloadFile(
            @Url String url
    );
}
