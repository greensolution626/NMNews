package com.android.nmnewsagency.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsViewsCount extends Service implements Callback<Object> {
    Rest rest;

    @Override
    public void onCreate() {
        super.onCreate();
        rest=new Rest(this,this);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        callServicenewsCount();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void callServicenewsCount() {
     //   rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.setNewsCount(Integer.parseInt(Prefrence.getnewsId()));
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {

    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }
}
