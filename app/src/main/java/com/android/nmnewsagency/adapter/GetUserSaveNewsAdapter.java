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
import com.android.nmnewsagency.activity.OwnVideoDetailActivity;
import com.android.nmnewsagency.modelclass.GetUserSaveNewsModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class GetUserSaveNewsAdapter extends RecyclerView.Adapter<GetUserSaveNewsAdapter.MyViewHolder> {
    private List<GetUserSaveNewsModel.DataBeanX.DataBean.PagedRecordBean> moviesList;
    Context context;

    public GetUserSaveNewsAdapter(Context context, List<GetUserSaveNewsModel.DataBeanX.DataBean.PagedRecordBean> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_hashtag;
        LinearLayout lin_item;

        public MyViewHolder(View view) {
            super(view);
           img_hashtag = (ImageView) view.findViewById(R.id.img_hashtag);
           lin_item = (LinearLayout) view.findViewById(R.id.lin_additem);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hashtag_detail, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetUserSaveNewsModel.DataBeanX.DataBean.PagedRecordBean movie = moviesList.get(position);
        Glide.with(context)
                .load(movie.getImageUrl())
                .into(holder.img_hashtag);
        holder.lin_item.setTag(position);
        holder.lin_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos= (int) v.getTag();
                Intent intent = new Intent(context, OwnVideoDetailActivity.class);
                intent.putExtra("newsid", moviesList.get(pos).getNewsId());
                intent.putExtra("self", "self");
                intent.putExtra("type","save");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
