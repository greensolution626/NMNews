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

import java.util.ArrayList;
import java.util.List;

public class FragmentSearchTopTab extends Fragment {
    RecyclerView recyclerView;
    SearchTopBarPeopleAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<Integer> imgList;
    View view;

    public static FragmentSearchTopTab newInstance() {
        Bundle args = new Bundle();
        FragmentSearchTopTab fragment = new FragmentSearchTopTab();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchtopfragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_search_hashtag);
        arrayList = new ArrayList<>();
        imgList = new ArrayList<>();
        inItItemRecycle();
        return view;
    }

    private void inItItemRecycle() {
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        imgList.add(R.drawable.msg1);
        imgList.add(R.drawable.msg2);
        imgList.add(R.drawable.msg3);
        imgList.add(R.drawable.searchimage);
        imgList.add(R.drawable.msg1);
        imgList.add(R.drawable.msg2);
        imgList.add(R.drawable.msg3);
        imgList.add(R.drawable.searchimage);
        locationAdapter = new SearchTopBarPeopleAdapter(getActivity(),arrayList,imgList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
                Intent intent = new Intent(getActivity(), HashTagDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}