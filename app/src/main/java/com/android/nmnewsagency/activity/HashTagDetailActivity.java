package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.HashTagAdapter;
import com.android.nmnewsagency.adapter.HashTagDetailAdapter;
import com.android.nmnewsagency.adapter.HomeAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class HashTagDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView iamge_back_hashtagdeatil;
    HashTagDetailAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<Integer> imAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.search_hashtag_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recy_hashtag_detail);
        iamge_back_hashtagdeatil = (ImageView) findViewById(R.id.iamge_back_hashtagdeatil);
        arrayList = new ArrayList<>();
        inItItemRecycle();
        iamge_back_hashtagdeatil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inItItemRecycle() {
        arrayList.add("");
        arrayList.add("");
        imAGE = new ArrayList<>();
        imAGE.add(R.drawable.profileimage);
        imAGE.add(R.drawable.profileimage1);
        imAGE.add(R.drawable.profileimage2);
        imAGE.add(R.drawable.profileimage3);
        imAGE.add(R.drawable.profileimage4);
        imAGE.add(R.drawable.profileimage5);
        imAGE.add(R.drawable.profileimage);
        imAGE.add(R.drawable.profileimage1);
        imAGE.add(R.drawable.profileimage2);
        imAGE.add(R.drawable.profileimage3);
        imAGE.add(R.drawable.profileimage4);
        imAGE.add(R.drawable.profileimage5);
        locationAdapter = new HashTagDetailAdapter(HashTagDetailActivity.this,arrayList,imAGE);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
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