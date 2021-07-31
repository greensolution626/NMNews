package com.android.nmnewsagency.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.HashTagDetailActivity;
import com.android.nmnewsagency.adapter.HomeAdapter;
import com.android.nmnewsagency.adapter.SearchTopBarPeopleAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.modelclass.SearchTopSearchModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearchTopTab extends Fragment {
      RecyclerView recyclerView;
      SearchTopBarPeopleAdapter locationAdapter;
      List<SearchTopSearchModel.DataBeanX.DataBean.PagedRecordBean> arrayList;
    View view;

    public static FragmentSearchTopTab newInstance() {
        //Bundle args = new Bundle();
        FragmentSearchTopTab fragment = new FragmentSearchTopTab();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchtopfragment, container, false);
        return view;
    }

    private  void inItItemRecycle() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_search_hashtag);
        locationAdapter = new SearchTopBarPeopleAdapter(getActivity(), arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
               // Intent intent = new Intent(getActivity(), HashTagDetailActivity.class);
                //startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

   /* public  void setSataOnViews(List<SearchTopSearchModel.DataBeanX.DataBean.PagedRecordBean> beanArrayList) {
        System.out.println(String.valueOf(beanArrayList));
        arrayList=beanArrayList;
        inItItemRecycle();
    }*/
}