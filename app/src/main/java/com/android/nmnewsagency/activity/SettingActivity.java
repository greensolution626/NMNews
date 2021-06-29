package com.android.nmnewsagency.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.pref.Prefrence;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout lin_dialog;
    ImageView iamge_back_seting;
    Button but_logout;
    BottomSheetDialog bottomSheetDialog;
    TextView txt_setng_location;
    RelativeLayout rel_uploaddoc;
     static String whichOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_setting);
        iniIt();
       // setDataOnLocation();
    }

    private void iniIt() {
        lin_dialog = (LinearLayout) findViewById(R.id.lin_dialog);
        rel_uploaddoc = (RelativeLayout) findViewById(R.id.rel_uploaddoc);
        but_logout = (Button) findViewById(R.id.but_logout);
        txt_setng_location = (TextView) findViewById(R.id.txt_setng_location);
        iamge_back_seting = (ImageView) findViewById(R.id.iamge_back_seting);
        lin_dialog.setOnClickListener(this);
        but_logout.setOnClickListener(this);
        rel_uploaddoc.setOnClickListener(this);
        txt_setng_location.setOnClickListener(this);
        iamge_back_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
    }

    private void setDataOnLocation() {
        String s1 = Prefrence.gettahsil();
       // whichOne = sh.getString("whichone", "");
        txt_setng_location.setText(s1);
        /*if(whichOne.equals("reporter")){
            rel_uploaddoc.setVisibility(View.GONE);
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataOnLocation();
    }

    private void openDialogBox() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_seting);
        ImageView on = bottomSheetDialog.findViewById(R.id.img_dialg_on);
        ImageView wifi = bottomSheetDialog.findViewById(R.id.img_dialg_wifi);
        ImageView off = bottomSheetDialog.findViewById(R.id.img_dialg_off);
        TextView txton = bottomSheetDialog.findViewById(R.id.txt_on);
        TextView txtwifi = bottomSheetDialog.findViewById(R.id.txt_wifi);
        TextView txtoff = bottomSheetDialog.findViewById(R.id.txt_off);
        LinearLayout linon = bottomSheetDialog.findViewById(R.id.lin_dia_on);
        LinearLayout linwifi = bottomSheetDialog.findViewById(R.id.lin_dia_wifi);
        LinearLayout linoff = bottomSheetDialog.findViewById(R.id.lin_dia_off);
        linon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                on.setVisibility(View.VISIBLE);
                txton.setTextColor(getResources().getColor(R.color.hashtagfolow));
                wifi.setVisibility(View.INVISIBLE);
                txtwifi.setTextColor(getResources().getColor(R.color.loctitle));
                off.setVisibility(View.INVISIBLE);
                txtoff.setTextColor(getResources().getColor(R.color.loctitle));
            }
        });
        linwifi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                on.setVisibility(View.INVISIBLE);
                txton.setTextColor(getResources().getColor(R.color.loctitle));
                wifi.setVisibility(View.VISIBLE);
                txtwifi.setTextColor(getResources().getColor(R.color.hashtagfolow));
                off.setVisibility(View.INVISIBLE);
                txtoff.setTextColor(getResources().getColor(R.color.loctitle));
            }
        });
        linoff.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                on.setVisibility(View.INVISIBLE);
                txton.setTextColor(getResources().getColor(R.color.loctitle));
                wifi.setVisibility(View.INVISIBLE);
                txtwifi.setTextColor(getResources().getColor(R.color.loctitle));
                off.setVisibility(View.VISIBLE);
                txtoff.setTextColor(getResources().getColor(R.color.hashtagfolow));
            }
        });
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_dialog:
                openDialogBox();
                break;
            case R.id.but_logout:
                closrAllActivity();
                break;
            case R.id.txt_setng_location:
                closrAllActivityLoc();
                break;
            case R.id.rel_uploaddoc:
                Intent intent = new Intent(SettingActivity.this,
                        UploadDocumentActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void closrAllActivity() {
       Prefrence.removeOrClearAllPerferanceData();
        Intent intent = new Intent(SettingActivity.this,
                LocationReqActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    private void closrAllActivityLoc() {
        Intent intent = new Intent(SettingActivity.this,
                LocationReqActivity.class);
        startActivity(intent);
    }

}