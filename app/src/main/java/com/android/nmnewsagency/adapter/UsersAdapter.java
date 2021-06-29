package com.android.nmnewsagency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {
   // private List<LocationModel> moviesList;
    private List<String> moviesList;
    private List<Integer> imgList;
    Context context;

    /*public LocationAdapter(List<LocationModel> moviesList) {
        this.moviesList = moviesList;
    }*/

    public UsersAdapter(Context context,List<String> moviesList,List<Integer> imgList) {
        this.moviesList = moviesList;
        this.imgList = imgList;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        public ImageView image_search_profile;

        public MyViewHolder(View view) {
            super(view);
            image_search_profile = (ImageView) view.findViewById(R.id.image_search_profile);
            user_name = (TextView) view.findViewById(R.id.user_name);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usears, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*LocationModel movie = moviesList.get(position);
        holder.title.setText(movie.getmName());*/
        holder.user_name.setText(moviesList.get(position));
        Glide.with(context)
                .load(imgList.get(position))
                .placeholder(R.color.adbag)
                .centerCrop()
                /*.transition(DrawableTransitionOptions.withCrossFade(500))*/
                .into(holder.image_search_profile);
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}
