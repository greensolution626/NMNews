package com.android.nmnewsagency.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.UserProfileActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.MyViewHolder> {
   // private List<LocationModel> moviesList;
    private List<String> moviesList;
    private List<Integer> imgList;
    Context context;

    /*public LocationAdapter(List<LocationModel> moviesList) {
        this.moviesList = moviesList;
    }*/

    public FollowingAdapter(List<String> moviesList, Context context, List<Integer> imgList) {
        this.moviesList = moviesList;
        this.imgList = imgList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CircleImageView img_folow;
        public LinearLayout lin_folower;

        public MyViewHolder(View view) {
            super(view);
          // title = (TextView) view.findViewById(R.id.hashtag_name);
            img_folow = (CircleImageView) view.findViewById(R.id.img_folow);
            lin_folower = (LinearLayout) view.findViewById(R.id.lin_folower);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_following, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*LocationModel movie = moviesList.get(position);
        holder.title.setText(movie.getmName());*/
      //  holder.title.setText(moviesList.get(position));
        Glide.with(context)
                .load(imgList.get(position))
                .placeholder(R.color.adbag)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(holder.img_folow);
        holder.lin_folower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UserProfileActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }
}
