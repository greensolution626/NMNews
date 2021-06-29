package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.CovrageQualityAdapter;
import com.android.nmnewsagency.adapter.FollowersAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class FollowersActivituy extends AppCompatActivity {
    RecyclerView recyclerView;
    FollowersAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<Integer> img_List;
    ImageView iamge_back_folowers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_followers_activituy);
        recyclerView = (RecyclerView) findViewById(R.id.recy_folowers);
        iamge_back_folowers = (ImageView) findViewById(R.id.iamge_back_folowers);
        arrayList = new ArrayList<>();
        img_List = new ArrayList<>();
        inItItemRecycle();
        iamge_back_folowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowersActivituy.this.finish();
            }
        });
    }
    private void inItItemRecycle() {
        img_List.add(R.drawable.searchimage);
        img_List.add(R.drawable.search5);
        img_List.add(R.drawable.search4);
        img_List.add(R.drawable.search3);
        img_List.add(R.drawable.search2);
        img_List.add(R.drawable.search1);
        img_List.add(R.drawable.searchimage);
        img_List.add(R.drawable.search4);
        img_List.add(R.drawable.search3);

        locationAdapter = new FollowersAdapter(arrayList,FollowersActivituy.this,img_List);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}