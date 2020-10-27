package com.example.pinterestlite.view;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pinterestlite.R;
import com.example.pinterestlite.model.ImageResult;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private ArrayList<ImageResult> list;
    private Context mContext;
    public MyRecyclerViewAdapter(ArrayList<ImageResult> imgList, Context context){
        list = imgList;
        mContext = context;
    }

    public void updateAdapter(ArrayList<ImageResult> imgList){
        list = imgList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try{
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
            return new MyViewHolder(itemView);
        }catch(Exception ex){
            Log.e("MainActivity","Adapter ex = "+ex.getMessage());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ImageResult imageObject = list.get(position);
        // Todo: replace with String builder append & use utility class for it
        String strURI = "http://farm"+imageObject.getFarm()+".static.flickr.com/"+imageObject.getServer()+"/"+imageObject.getId()+"_"+imageObject.getSecret()+".jgp";
        Uri imgURI = Uri.parse(strURI);
        //Uri imgURI = Uri.parse("http://farm1.static.flickr.com/578/23451156376_8983a8ebc7.jpg");
        Log.e("MainActivity","onBindViewHolder imgURI : "+imgURI);

        holder.simpleDV.setImageURI(imgURI);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView simpleDV;
        public MyViewHolder(View itemView){
            super(itemView);
            this.simpleDV = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
        }
    }
}
