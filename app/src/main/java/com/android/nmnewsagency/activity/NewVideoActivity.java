package com.android.nmnewsagency.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.AutoCompleteSearchBarAdapter;
import com.android.nmnewsagency.adapter.HashTagAdapter;
import com.android.nmnewsagency.adapter.SearchTopBarHashtagAdapter;
import com.android.nmnewsagency.adapter.SearchTopBarPeopleAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;

public class NewVideoActivity extends AppCompatActivity implements TextWatcher {
    LinearLayout lin_brekingnews, lin_showlayout;
    ImageView iamge_back_newvideo;
    int count = 0;
    TextView txt_newvideo_submit, txt_remainin;
    MultiAutoCompleteTextView autoCompleteTextView;
    AutoCompleteSearchBarAdapter autoCompleteSearchBarAdapter;
    SearchTopBarHashtagAdapter topBarHashtagAdapter;
    ArrayList<String> arrayList;
    ArrayList<Integer> imgList;
    RecyclerView recy_newVideo;
    RelativeLayout lin_attherate, lin_hashtag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_new_video);
        imgList = new ArrayList<>();
        arrayList = new ArrayList<>();
        lin_brekingnews = (LinearLayout) findViewById(R.id.lin_brekingnews);
        lin_showlayout = (LinearLayout) findViewById(R.id.lin_showlayout);
        lin_hashtag = (RelativeLayout) findViewById(R.id.lin_hashtag);
        lin_attherate = (RelativeLayout) findViewById(R.id.lin_attherate);
        iamge_back_newvideo = (ImageView) findViewById(R.id.iamge_back_newvideo);
        txt_newvideo_submit = (TextView) findViewById(R.id.txt_newvideo_submit);
        txt_remainin = (TextView) findViewById(R.id.txt_remainin);
        autoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.addTextChangedListener(this);
        recy_newVideo = (RecyclerView) findViewById(R.id.recy_newVideo);
        iamge_back_newvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewVideoActivity.this.finish();
            }
        });
        txt_newvideo_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewVideoActivity.this, "Your Video Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(NewVideoActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        lin_brekingnews.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    lin_brekingnews.setBackground(NewVideoActivity.this.getResources().getDrawable(R.drawable.newvideo1));
                    count = 1;
                } else {
                    lin_brekingnews.setBackground(NewVideoActivity.this.getResources().getDrawable(R.drawable.newvideo));
                    count = 0;
                }
            }
        });
        lin_attherate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.setText(autoCompleteTextView.getText().toString() + "@");
            }
        });
        lin_hashtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.setText(autoCompleteTextView.getText().toString() + "#");
            }
        });
        inItItemRecycle();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!autoCompleteTextView.getText().toString().equals("")) {
            char first = autoCompleteTextView.getText().toString().charAt(0);
            int adtheate = Character.compare(first, '@');
            int hashtag = Character.compare(first, '#');
            if (adtheate == 0) {
                recy_newVideo.setAdapter(autoCompleteSearchBarAdapter);
                recy_newVideo.setVisibility(View.VISIBLE);
                lin_showlayout.setVisibility(View.GONE);
            } else if (hashtag == 0) {
                recy_newVideo.setAdapter(topBarHashtagAdapter);
                recy_newVideo.setVisibility(View.VISIBLE);
                lin_showlayout.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        //Toast.makeText(NewVideoActivity.this, "afterTextChanged", Toast.LENGTH_SHORT).show();
        if (autoCompleteTextView.getText().toString().equals("")) {
            recy_newVideo.setVisibility(View.GONE);
            lin_showlayout.setVisibility(View.VISIBLE);
        }
        int charLength = autoCompleteTextView.getText().toString().length();
        if (charLength <= 120) {
            txt_remainin.setText("Remaining : " + String.valueOf(120 - charLength));
        } else if (charLength > 120) {
          //  autoCompleteTextView.setEnabled(false);

        }
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
        autoCompleteSearchBarAdapter = new AutoCompleteSearchBarAdapter(NewVideoActivity.this, arrayList, imgList);
        topBarHashtagAdapter = new SearchTopBarHashtagAdapter(NewVideoActivity.this, arrayList, imgList);

        recy_newVideo.setLayoutManager(new LinearLayoutManager(NewVideoActivity.this));
        recy_newVideo.setItemAnimator(new DefaultItemAnimator());
        recy_newVideo.addOnItemTouchListener(new RecyclerTouchListener(NewVideoActivity.this, recy_newVideo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
                //  Intent intent = new Intent(NewVideoActivity.this, HashTagDetailActivity.class);
                // startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}