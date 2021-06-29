package com.android.nmnewsagency.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.holder.ChildViewHolder;
import com.android.nmnewsagency.holder.ChildViewPerformanceHolder;
import com.android.nmnewsagency.holder.SectionViewHolder;
import com.android.nmnewsagency.holder.SectionViewPerformnceHolder;
import com.android.nmnewsagency.model.Child;
import com.intrusoft.sectionedrecyclerview.SectionRecyclerViewAdapter;

import java.util.List;

/**
 * Created by apple on 11/7/16.
 */

public class Performance_DistricAdapter extends SectionRecyclerViewAdapter<SectionHeader, Child, SectionViewPerformnceHolder, ChildViewPerformanceHolder> {

    Context context;

    public Performance_DistricAdapter(Context context, List<SectionHeader> sectionHeaderItemList) {
        super(context, sectionHeaderItemList);
        this.context = context;
    }

    @Override
    public SectionViewPerformnceHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.section_perfodistr_item, sectionViewGroup, false);
        return new SectionViewPerformnceHolder(view);
    }

    @Override
    public ChildViewPerformanceHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_perfor_district, childViewGroup, false);
        return new ChildViewPerformanceHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(SectionViewPerformnceHolder sectionViewHolder, int sectionPosition, SectionHeader sectionHeader) {
        sectionViewHolder.name.setText(sectionHeader.sectionText);
    }

    @Override
    public void onBindChildViewHolder(ChildViewPerformanceHolder childViewHolder, int sectionPosition, int childPosition, Child child) {
       childViewHolder.name.setText(child.getName());
    }
}
