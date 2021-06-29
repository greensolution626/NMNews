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
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class SearchTopBarPeopleAdapter extends RecyclerView.Adapter<SearchTopBarPeopleAdapter.MyViewHolder> {
    // private List<LocationModel> moviesList;
    private List<String> moviesList;
    private List<Integer> imgList;
    private Context context;

    /*public LocationAdapter(List<LocationModel> moviesList) {
        this.moviesList = moviesList;
    }*/

    public SearchTopBarPeopleAdapter(Context context, List<String> moviesList, List<Integer> imgList) {
        this.moviesList = moviesList;
        this.imgList = imgList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image_profile_hashtag;

        public MyViewHolder(View view) {
            super(view);
            // title = (TextView) view.findViewById(R.id.loc_name);
            image_profile_hashtag = (ImageView) view.findViewById(R.id.image_profile_hashtag);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_peoplebare, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*LocationModel movie = moviesList.get(position);
        holder.title.setText(movie.getmName());*/
        // holder.title.setText(moviesList.get(position));
        Glide.with(context)
                .load(imgList.get(position))
                .placeholder(R.color.adbag)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(holder.image_profile_hashtag);
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}
