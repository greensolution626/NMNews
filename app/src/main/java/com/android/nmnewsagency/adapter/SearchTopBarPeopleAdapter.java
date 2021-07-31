package com.android.nmnewsagency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.SearchTopSearchModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class SearchTopBarPeopleAdapter extends RecyclerView.Adapter<SearchTopBarPeopleAdapter.MyViewHolder> {
    private List<SearchTopSearchModel.DataBeanX.DataBean.PagedRecordBean> moviesList;
    private Context context;

    public SearchTopBarPeopleAdapter(Context context, List<SearchTopSearchModel.DataBeanX.DataBean.PagedRecordBean> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name;
        public ImageView image_profile_hashtag;

        public MyViewHolder(View view) {
            super(view);
            image_profile_hashtag = (ImageView) view.findViewById(R.id.image_profile_hashtag);
            user_name = (TextView) view.findViewById(R.id.user_name);
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
        holder.user_name.setText(moviesList.get(position).getTitle());
        Glide.with(context)
                .load(moviesList.get(position).getAvatar())
                .placeholder(R.color.adbag)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(holder.image_profile_hashtag);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
