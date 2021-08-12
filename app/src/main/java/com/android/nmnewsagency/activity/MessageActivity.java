package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.HashTagDetailAdapter;
import com.android.nmnewsagency.adapter.MessageAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView iamge_back_msg;
    MessageAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    int totalUsers = 0;
    ProgressDialog pd;
    QBUser qbUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_message);
        qbUsers = new QBUser();
        inIt();
        getListMessage();
    }

    private void getListMessage() {
        pd = new ProgressDialog(MessageActivity.this);
        pd.setMessage("Loading...");
        pd.show();

        QBUsers.getUser(129878309).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Log.e("qbuser==",qbUser.toString());
                pd.dismiss();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.e("qbuserERROR==",e.toString());
                pd.dismiss();
            }
        });
    }

    private void inIt() {
        recyclerView = (RecyclerView) findViewById(R.id.recy_mesage);
        iamge_back_msg = (ImageView) findViewById(R.id.iamge_back_msg);
        arrayList = new ArrayList<>();
        iamge_back_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageActivity.this.finish();
            }
        });
    }

    private void inItItemRecycle() {
        locationAdapter = new MessageAdapter(this, arrayList);
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