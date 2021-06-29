package com.android.nmnewsagency.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.CovrageQualityActivity;
import com.android.nmnewsagency.activity.MessageActivity;
import com.android.nmnewsagency.activity.MessageDetailActivity;
import com.android.nmnewsagency.adapter.MessageAdapter;
import com.android.nmnewsagency.adapter.Performance_SelfAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;


public class Performance_SelfFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    Performance_SelfAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<Integer> imgList;

    public static Performance_SelfFragment newInstance() {
        Bundle args = new Bundle();
        Performance_SelfFragment fragment = new Performance_SelfFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_performance__self, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_performance);
        arrayList = new ArrayList<>();
        imgList = new ArrayList<>();
        inItItemRecycle();
        return view;
    }

    private void inItItemRecycle() {
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
        locationAdapter = new Performance_SelfAdapter(getActivity(), arrayList, imgList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CovrageQualityActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}