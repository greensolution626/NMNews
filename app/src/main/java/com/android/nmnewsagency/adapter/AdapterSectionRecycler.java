package com.android.nmnewsagency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.holder.ChildViewHolder;
import com.android.nmnewsagency.holder.SectionViewHolder;
import com.android.nmnewsagency.model.Child;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.List;

/**
 * Created by apple on 11/7/16.
 */

public class AdapterSectionRecycler extends SectionRecyclerViewAdapter<SectionHeader, Child, SectionViewHolder, ChildViewHolder> {

    Context context;

    public AdapterSectionRecycler(Context context, List<SectionHeader> sectionHeaderItemList) {
        super(context, sectionHeaderItemList);
        this.context = context;
    }

    @Override
    public SectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_item, sectionViewGroup, false);
        return new SectionViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, childViewGroup, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewHolder sectionViewHolder, int sectionPosition, SectionHeader sectionHeader) {
        sectionViewHolder.name.setText(sectionHeader.sectionText);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int sectionPosition, int childPosition, Child child) {
       //childViewHolder.name.setText(child.getName());
        Glide.with(context)
                .load(child.getImg(childPosition))
                .placeholder(R.color.adbag)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(childViewHolder.image_profile_noti);

    }
}
