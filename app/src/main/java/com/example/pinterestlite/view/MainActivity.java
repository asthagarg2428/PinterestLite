package com.example.pinterestlite.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinterestlite.R;
import com.example.pinterestlite.databinding.ActivityMainBinding;
import com.example.pinterestlite.model.ImageResult;
import com.example.pinterestlite.repository.ImageSearchViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageSearchViewModel myViewModel;
    private ActivityMainBinding binding;
    private MyRecyclerViewAdapter adapter;
    private GridLayoutManager layoutMgr;
    private boolean isLoading;
    private int pageNo = 0;
    private int imagesToFetchAtOneTime = 10;
    private ArrayList<ImageResult> imageList = new ArrayList<>();
    private String searchedQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("MainActivity", "onCreate Enter");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        myViewModel = ViewModelProviders.of(this).get(ImageSearchViewModel.class);
        setClickListeners();
        initScrollListener();
        layoutMgr = new GridLayoutManager(this, 2);
        binding.recyclerView.setLayoutManager(layoutMgr);
    }


    private void setClickListeners() {
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                try {
                    Log.d("MainActivity", "Before fetch");
                    searchedQuery = query;
                    myViewModel.fetchImages(query, imagesToFetchAtOneTime, 0);
                    observeSearchedImageList();
                    Log.d("MainActivity", "After fetch");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(MainActivity.this, "" + ex.getMessage(), Toast.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void observeSearchedImageList() {
        myViewModel.imagesList.observe(this, fetchedImageList -> {
            Log.i("MainActivity", "onChanged called" + fetchedImageList);
            imageList.addAll(fetchedImageList);
            isLoading = false;
            if (adapter == null) {
                adapter = new MyRecyclerViewAdapter(imageList, this);
                binding.recyclerView.setAdapter(adapter);
            } else {
                adapter.updateAdapter(imageList);
            }
        });
    }

    private void initScrollListener() {
        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutMgr = (GridLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading && layoutMgr != null && layoutMgr.findLastCompletelyVisibleItemPosition() == (imageList.size() - 3)) {
                    pageNo = pageNo + imagesToFetchAtOneTime + 1;
                    Log.d("TEST_FLICKR","onScrolled pageNo = "+pageNo);
                    myViewModel.fetchImages(searchedQuery, imagesToFetchAtOneTime, pageNo);
                    isLoading = true;
                }
            }
        });
    }
}
