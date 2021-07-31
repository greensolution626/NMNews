package com.android.nmnewsagency.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.NewVideoHashtagModelClass;
import com.android.nmnewsagency.modelclass.NewVideoMentionModelClass;
import com.bumptech.glide.Glide;

import java.util.List;

public class AutoCompleteSearchBarAdapter1 extends RecyclerView.Adapter<AutoCompleteSearchBarAdapter1.MyViewHolder> {
    // private List<LocationModel> moviesList;
    private List<NewVideoMentionModelClass.DataBean.PagedRecordBean> moviesList;
    private Context context;

    /*public LocationAdapter(List<LocationModel> moviesList) {
        this.moviesList = moviesList;
    }*/

    public AutoCompleteSearchBarAdapter1(Context context, List<NewVideoMentionModelClass.DataBean.PagedRecordBean> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, txt_subId;
        public ImageView image_profile_hashtag;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.user_name);
            txt_subId = (TextView) view.findViewById(R.id.txt_subId);
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
        NewVideoMentionModelClass.DataBean.PagedRecordBean movie = moviesList.get(position);
        holder.title.setText(movie.getUserName());
        holder.txt_subId.setText(movie.getTitle());
        new Handler().post(new Runnable() {
            public void run() {
                Glide.with(context)
                        .load(moviesList.get(position).getAvatar())
                        .into(holder.image_profile_hashtag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
