package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.MessageAdapter;
import com.android.nmnewsagency.adapter.MessageDetailAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MessageDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MessageDetailAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    ImageView iamge_back_msgdetail, img_msg_detail;
    RelativeLayout rel_msgdetail_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_message_detail);
        recyclerView = (RecyclerView) findViewById(R.id.recy_mesage_detail);
        rel_msgdetail_profile = (RelativeLayout) findViewById(R.id.rel_msgdetail_profile);
        iamge_back_msgdetail = (ImageView) findViewById(R.id.iamge_back_msgdetail);
        img_msg_detail = (ImageView) findViewById(R.id.img_msg_detail);
        arrayList = new ArrayList<>();
        iamge_back_msgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDetailActivity.this.finish();
            }
        });
        inItItemRecycle();
        img_msg_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBox();
            }
        });
        rel_msgdetail_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MessageDetailActivity.this,UserProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inItItemRecycle() {
        arrayList.add("");
        arrayList.add("");
        locationAdapter = new MessageDetailAdapter(arrayList);

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

    private void openDialogBox() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_userprofile);
        LinearLayout lin_dia_cancel = bottomSheetDialog.findViewById(R.id.lin_dia_cancel);
        LinearLayout lin_dia_report = bottomSheetDialog.findViewById(R.id.lin_dia_report);
        lin_dia_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBoxReport();
                bottomSheetDialog.cancel();
            }
        });
        lin_dia_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.show();
    }

    private void openDialogBoxReport() {
        Dialog dialog = new Dialog(MessageDetailActivity.this);
        dialog.setContentView(R.layout.dialog_report);
        ImageView img_close_dialog = dialog.findViewById(R.id.img_close_dialog);
        img_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button but_submit = dialog.findViewById(R.id.but_submit);
        but_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}