package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.HashTagDetailAdapter;
import com.android.nmnewsagency.adapter.MessageAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView iamge_back_msg;
    MessageAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<Integer> imgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_message);
        recyclerView = (RecyclerView) findViewById(R.id.recy_mesage);
        iamge_back_msg = (ImageView) findViewById(R.id.iamge_back_msg);
        arrayList = new ArrayList<>();
        imgList = new ArrayList<>();
        inItItemRecycle();
        iamge_back_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageActivity.this.finish();
            }
        });
    }
    private void inItItemRecycle() {
        imgList.add(R.drawable.msg1);
        imgList.add(R.drawable.msg2);
        imgList.add(R.drawable.msg3);
        imgList.add(R.drawable.searchimage);

        locationAdapter = new MessageAdapter(this,arrayList,imgList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent=new Intent(MessageActivity.this,MessageDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}