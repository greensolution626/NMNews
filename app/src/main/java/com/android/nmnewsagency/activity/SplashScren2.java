package com.android.nmnewsagency.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.utils.Utils;

public class SplashScren2 extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        Utils.setStatusBar(this);
        setContentView(R.layout.activity_splash2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the MainActivity. */
                setDataOnLocation();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void setDataOnLocation() {
        @SuppressLint("WrongConstant")
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
       // myEdit.putString("whichone", "user");
       // myEdit.commit();
        String s1 = sh.getString("login", "");
        if (!s1.equals("")) {
            Intent mainIntent = new Intent(SplashScren2.this, MainActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        } else {
            Intent mainIntent = new Intent(SplashScren2.this, LoginActivity.class);
            startActivity(mainIntent);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        }
           /*Intent mainIntent = new Intent(SplashScren2.this, CheckUserOrReporterActivity.class);
           startActivity(mainIntent);
           overridePendingTransition(R.anim.enter, R.anim.exit);
           finish();*/
    }
}