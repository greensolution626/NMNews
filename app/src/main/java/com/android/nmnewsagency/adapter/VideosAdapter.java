package com.android.nmnewsagency.adapter;

import android.annotation.SuppressLint;
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
import com.android.nmnewsagency.activity.OwnVideoDetailActivity;
import com.android.nmnewsagency.modelclass.SearchTopSearchModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {
   // private List<LocationModel> moviesList;
    private List<SearchTopSearchModel.DataBeanX.DataBean.PagedRecordBean> moviesList;
    Context context;

    public VideosAdapter(Context context, List<SearchTopSearchModel.DataBeanX.DataBean.PagedRecordBean> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView video_search;
        public ImageView image_search_video_profile;
        LinearLayout lin_videoes;

        public MyViewHolder(View view) {
            super(view);
            image_search_video_profile = (ImageView) view.findViewById(R.id.image_search_video_profile);
            video_search = (TextView) view.findViewById(R.id.video_search);
            lin_videoes = (LinearLayout) view.findViewById(R.id.lin_videoes);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_videos, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.video_search.setText(moviesList.get(position).getTitle());
        Glide.with(context)
                .load(moviesList.get(position).getAvatar())
                .placeholder(R.color.adbag)
                .centerCrop()
                /*.transition(DrawableTransitionOptions.withCrossFade(500))*/
                .into(holder.image_search_video_profile);
                holder.lin_videoes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, OwnVideoDetailActivity.class);
                        intent.putExtra("newsid",moviesList.get(position).getId());
                        intent.putExtra("self","other");
                        context.startActivity(intent);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
