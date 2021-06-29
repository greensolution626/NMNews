package com.android.nmnewsagency.fragment;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.HomeAdapter;
import com.android.nmnewsagency.adapter.LocationAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    HomeAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<Integer> imgList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_loc);
        arrayList = new ArrayList<>();
        imgList = new ArrayList<>();
        inItItemRecycle();
        return view;
    }

    private void inItItemRecycle() {
        arrayList.add("");
        arrayList.add("");
        imgList.add(R.drawable.homeimagevideo);
        imgList.add(R.drawable.home1);
        locationAdapter = new HomeAdapter(getActivity(),arrayList,imgList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
              //  Toast.makeText(getActivity(), arrayList.get(position) + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}