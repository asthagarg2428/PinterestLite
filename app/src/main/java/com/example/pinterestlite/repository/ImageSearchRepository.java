package com.example.pinterestlite.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.pinterestlite.base.APIInterface;
import com.example.pinterestlite.base.ApiClient;
import com.example.pinterestlite.model.ImageResult;
import com.example.pinterestlite.model.ResponseImageRootModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageSearchRepository {

    private static ImageSearchRepository repo = null;

    private APIInterface apiInterface;
    private MutableLiveData<ArrayList<ImageResult>> imagesListLV = new MutableLiveData<>();

    private ImageSearchRepository() {
        // setup api client and all since one time use only code it is
        apiInterface = ApiClient.getClient().create(APIInterface.class);

    }

    public static ImageSearchRepository getInstance() {
        if (repo == null)
            repo = new ImageSearchRepository();
        return repo;
    }


    public MutableLiveData<ArrayList<ImageResult>> fetchImages(HashMap<String,String> map){
        try{
            Log.d("Repo","Before fetch");
            Call<ResponseImageRootModel> call = apiInterface.getImagesList(map);
            Log.d("Repo","After fetch");
            call.enqueue(new Callback<ResponseImageRootModel>() {
                @Override
                public void onResponse(Call<ResponseImageRootModel> call, Response<ResponseImageRootModel> response) {
                    try {
                        if(response.body() != null){
                            ArrayList<ImageResult> imageResList = ((ResponseImageRootModel) response.body()).getPhotos().getPhoto();
                            imagesListLV.setValue(imageResList);
                        }
                    }catch (Exception ex){
                        Log.e("Repo","Exception "+ex.getMessage());
                    }

                }
                @Override
                public void onFailure(Call<ResponseImageRootModel> call, Throwable t) {
                    Log.e("Repo",""+t.getMessage());
                }
            });

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return imagesListLV;
    }

    public void clearLD(){
        if(imagesListLV != null)
            imagesListLV.setValue(null);
    }

}
