package com.android.nmnewsagency.fragment;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.HomeAdapter;
import com.android.nmnewsagency.adapter.LocationAdapter;
import com.android.nmnewsagency.adapter.ViewPagerAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.model.CountryModel;
import com.android.nmnewsagency.modelclass.GetNewsListModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.service.NewsViewsCount;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements Callback<Object> {
    View view;
    ViewPager2 recyclerView;
    // HomeAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<GetNewsListModel.DataBean.PagedRecordBean> arrayList;
    List<String> imgList;
    Rest rest;
    ViewPagerAdapter locationAdapter;
    private static int selectedPosition = 0;

    OnActivityStateChanged onActivityStateChanged = null;

    public interface OnActivityStateChanged {
        void onResumed();

        void onPaused();

        void onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        rest = new Rest(getActivity(), this);
        recyclerView = (ViewPager2) view.findViewById(R.id.viewPager2);

        // callServicegetCityList();
        return view;
    }

    private void callServicegetCityList() {

        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getNewsList(Prefrence.getCountryName(), Prefrence.getStateName(), Prefrence.getCityName(),
                Prefrence.gettahsil(), Prefrence.getUserId(), "0", "10");
    }


    private void inItItemRecycle() {

        // imgList.add("https://www.rmp-streaming.com/media/bbb-360p.mp4");
        // imgList.add("http://nmnews.uislick.com/videos/84eb325e-3f33-45a5-93c8-a3379a417711.mp4");
        if (getActivity() != null) {
            locationAdapter = new ViewPagerAdapter(getActivity(), arrayList, recyclerView);
            onActivityStateChanged = locationAdapter.registerActivityState();
            recyclerView.setAdapter(locationAdapter);
            recyclerView.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            if (Prefrence.getisUpload()) {
                setCurrentItem(0);
                Prefrence.setisUpload(false);
            } else {
                setCurrentItem(selectedPosition);
            }
            //  setCurrentItem(Prefrence.getPosition());
            recyclerView.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    //   Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                    Prefrence.setnewsId(String.valueOf(arrayList.get(position).getNewsId()));
                    // displayMetaInfo(position);
                    Log.d("original", String.valueOf(position));
                    getActivity().startService(new Intent(getActivity(), NewsViewsCount.class));
                    // Prefrence.setPosition(position);
                    selectedPosition = position;
                }
            });

        }


       /* locationAdapter = new HomeAdapter(getActivity(), arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
                //  Toast.makeText(getActivity(), arrayList.get(position) + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof GetNewsListModel) {
                GetNewsListModel loginModel = (GetNewsListModel) obj;
                if (loginModel.isStatus()) {
                    arrayList = loginModel.getData().getPagedRecord();
                    inItItemRecycle();
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    @Override
    public void onResume() {
        if (onActivityStateChanged != null) {
            onActivityStateChanged.onResumed();
        }
        super.onResume();
        callServicegetCityList();

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        if (onActivityStateChanged != null) {
            onActivityStateChanged.onPaused();
        }
        super.onPause();
        getActivity().stopService(new Intent(getActivity(), NewsViewsCount.class));
    }

    @Override
    public void onStop() {

        if (onActivityStateChanged != null) {
            onActivityStateChanged.onStop();
        }
        super.onStop();
    }

    private void setCurrentItem(int position) {
        recyclerView.setCurrentItem(position, false);
        // displayMetaInfo(selectedPosition);
    }
}