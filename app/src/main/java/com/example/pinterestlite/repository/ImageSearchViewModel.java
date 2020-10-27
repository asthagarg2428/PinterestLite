package com.example.pinterestlite.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pinterestlite.R;
import com.example.pinterestlite.model.ImageResult;

import java.util.ArrayList;
import java.util.HashMap;

public class ImageSearchViewModel extends AndroidViewModel {

    private Context mContext;
    public LiveData<ArrayList<ImageResult>> imagesList;
    public ImageSearchViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
    }

    public void fetchImages(String searchQuery, int totalCount, int start){
        Log.i("MainActivity", "fetchImages searchQuery" + searchQuery+", totalCount = "+totalCount+ ", start = "+start);
        HashMap<String, String> map = new HashMap<>();
        // Todo: keys in constants
        map.put("text",searchQuery);
        map.put("per_page",String.valueOf(totalCount));
        map.put("method","flickr.photos.search");
        map.put("api_key",getApplication().getResources().getString(R.string.api_key));
        map.put("format","json");
        map.put("nojsoncallback","1");
        map.put("page",String.valueOf(start));
        ImageSearchRepository repo = ImageSearchRepository.getInstance();
        imagesList = repo.fetchImages(map);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("ViewModel", "on cleared called");
        ImageSearchRepository repo = ImageSearchRepository.getInstance();
        repo.clearLD();
    }

}
