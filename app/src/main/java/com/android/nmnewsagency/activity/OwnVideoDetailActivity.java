package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.HomeAdapter;
import com.android.nmnewsagency.adapter.OwnVideoDetailAdapter;
import com.android.nmnewsagency.adapter.RejectedVideoAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class OwnVideoDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    OwnVideoDetailAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_own_video_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recy_ownvideodetail);
        arrayList = new ArrayList<>();
        inItItemRecycle();
    }
    private void inItItemRecycle() {
        arrayList.add("");
        locationAdapter = new OwnVideoDetailAdapter(this,arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
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