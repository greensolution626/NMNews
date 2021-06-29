package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.CommentsAdapter;
import com.android.nmnewsagency.adapter.MessageDetailAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CommentsAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    ImageView iamge_back_msgdetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_comments);
        recyclerView = (RecyclerView) findViewById(R.id.recy_comment_detail);
        iamge_back_msgdetail = (ImageView) findViewById(R.id.iamge_back_comment);
        arrayList = new ArrayList<>();
        iamge_back_msgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inItItemRecycle();
    }
    private void inItItemRecycle() {
        arrayList.add("");
        arrayList.add("");
        locationAdapter = new CommentsAdapter(arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}