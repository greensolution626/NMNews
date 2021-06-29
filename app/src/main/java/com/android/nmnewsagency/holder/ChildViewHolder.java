package com.android.nmnewsagency.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;

/**
 * Created by apple on 11/7/16.
 */

public class ChildViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public ImageView image_profile_noti;

    public ChildViewHolder(View itemView) {
        super(itemView);
      //  name = (TextView) itemView.findViewById(R.id.child);
        image_profile_noti = (ImageView) itemView.findViewById(R.id.image_profile_noti);
    }
}
