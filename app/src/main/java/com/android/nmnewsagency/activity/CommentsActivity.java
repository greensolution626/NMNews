package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.CommentsAdapter;
import com.android.nmnewsagency.adapter.MessageDetailAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.modelclass.AddNewsModel;
import com.android.nmnewsagency.modelclass.GetCommentsModel;
import com.android.nmnewsagency.modelclass.ReportModelClass;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity implements Callback<Object> {
    RecyclerView recyclerView;
    CommentsAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<GetCommentsModel.DataBean> arrayList;
    ImageView iamge_back_msgdetail,img_send;
    EditText edittext_coments;
    Rest rest;
    int newsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_comments);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newsId = bundle.getInt("newsId");
        }
        rest=new Rest(this,this);
        iniIt();
        callServiceGetComments();
    }

    private void callServiceGetComments() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.CommentsUser(newsId);
    }

    private void callServiceAddComments(String comments,String parentCommentId) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.AddCommentsUser(newsId,comments,parentCommentId);
    }

    private void iniIt() {
        recyclerView = (RecyclerView) findViewById(R.id.recy_comment_detail);
        iamge_back_msgdetail = (ImageView) findViewById(R.id.iamge_back_comment);
        img_send = (ImageView) findViewById(R.id.img_send);
        edittext_coments = (EditText ) findViewById(R.id.edittext_coments);
        iamge_back_msgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext_coments.getText().toString().equals("")){
                    edittext_coments.setError("Please enter comments");
                    return;
                }else {
                    callServiceAddComments(edittext_coments.getText().toString(), "1");
                }
            }
        });
    }

    private void inItItemRecycle() {
        locationAdapter = new CommentsAdapter(this,arrayList);

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

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof GetCommentsModel) {
                GetCommentsModel loginModel = (GetCommentsModel) obj;
                if (loginModel.isStatus()) {
                    arrayList = loginModel.getData();
                    if (arrayList != null) {
                       inItItemRecycle();
                    }
                }
            }
            if (obj instanceof ReportModelClass) {
                ReportModelClass loginModel = (ReportModelClass) obj;
                if (loginModel.isStatus()) {
                    edittext_coments.setText("");
                   callServiceGetComments();
                }
            }
            if (obj instanceof ReportModelClass) {
                ReportModelClass loginModel = (ReportModelClass) obj;
                if (loginModel.isStatus()) {
                    //Toast.makeText(context, "Report successfully added", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }
}