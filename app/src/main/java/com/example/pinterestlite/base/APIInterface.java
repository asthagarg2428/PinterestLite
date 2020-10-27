package com.example.pinterestlite.base;

import com.example.pinterestlite.model.ResponseImageRootModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIInterface {

    @GET(".")
    Call<ResponseImageRootModel> getImagesList(@QueryMap HashMap<String,String> params);
}
