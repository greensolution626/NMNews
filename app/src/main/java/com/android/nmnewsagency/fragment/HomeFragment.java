package com.android.nmnewsagency.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.ViewPagerAdapter;
import com.android.nmnewsagency.modelclass.GetNewsListModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.service.NewsViewsCount;
import com.android.nmnewsagency.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements Callback<Object>, SwipeRefreshLayout.OnRefreshListener {
    View view;
    ViewPager2 pager2;
    List<GetNewsListModel.DataBean.PagedRecordBean> arrayList;
    GetNewsListModel.DataBean databeanMain;
    List<String> imgList;
    Rest rest;
    ViewPagerAdapter locationAdapter;
    private static int selectedPosition = 0;
    SwipeRefreshLayout pullrefresh;
    ProgressDialog dialog;
    LinearLayout lin_tophome;
    boolean autoPlaySet = true;
    boolean callApi = false;
    private int LastIndex;

    OnActivityStateChanged onActivityStateChanged = null;

    @Override
    public void onRefresh() {
        arrayList.clear();
        callServicegetNewsList("0", "50", "0", "1", "01-Jan-1901");
    }

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
        pager2 = (ViewPager2) view.findViewById(R.id.viewPager2);
        arrayList = new ArrayList<>();
        lin_tophome = view.findViewById(R.id.lin_tophome);
        pullrefresh = (SwipeRefreshLayout) view.findViewById(R.id.pullrefresh);
        pullrefresh.setOnRefreshListener(this);
        pullrefresh.setColorSchemeResources(R.color.color_grey,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        pullrefresh.post(new Runnable() {
            @Override
            public void run() {
               /* pullrefresh.setRefreshing(true);
               callServicegetNewsList();*/
            }
        });
        return view;
    }

    private void callServicegetNewsList(String pageIndex, String pageOffset, String currentIndex, String tableIndex, String loopDate) {
        if (isInterentAvaliable()) {
           // setProgressSet();
            rest.ShowDialogue("Please Wait ..");
            rest.getNewsList(Prefrence.getCountryName(), Prefrence.getStateName(), Prefrence.getCityName(),
                  Prefrence.gettahsil(), Prefrence.getUserId(), pageIndex, pageOffset, currentIndex, tableIndex, loopDate);
            //rest.getNewsList(Prefrence.getCountryName(), "RAJASTHAN", "jaipur",
            //      "jaipur", "f0523348-0c2f-44e4-b0cd-c5d5d4f66ada", pageIndex, pageOffset, currentIndex, tableIndex, loopDate);
        } else {
            AlertForInternet();
        }
    }

    private void callServicegetNewsListNext(String pageIndex, String pageOffset, String currentIndex, String tableIndex, String loopDate) {
        if (isInterentAvaliable()) {
            // setProgressSet();
            rest.getNewsList(Prefrence.getCountryName(), Prefrence.getStateName(), Prefrence.getCityName(),
                    Prefrence.gettahsil(), Prefrence.getUserId(), pageIndex, pageOffset, currentIndex, tableIndex, loopDate);
        } else {
            AlertForInternet();
        }
    }

    public boolean isInterentAvaliable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected();
    }

    public void AlertForInternet() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage("Internet Not avalible");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.show();
    }

    private void inItItemRecycle() {
        if (getActivity() != null) {
            locationAdapter = new ViewPagerAdapter(getActivity(), arrayList, pager2, getActivity());
            onActivityStateChanged = locationAdapter.registerActivityState();
            // pager2.setOffscreenPageLimit(1);
            pager2.setAdapter(locationAdapter);
            pager2.setPageTransformer(new ViewPager2.PageTransformer() {
                private static final float MIN_SCALE = 0.85f;
                private static final float MIN_ALPHA = 0.5f;

                public void transformPage(View view, float position) {
                    int pageWidth = view.getWidth();
                    int pageHeight = view.getHeight();

                    if (position < -1) { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        view.setAlpha(0f);

                    } else if (position <= 1) { // [-1,1]
                        // Modify the default slide transition to shrink the page as well
                        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                        float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                        float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                        if (position < 0) {
                            view.setTranslationX(horzMargin - vertMargin / 2);
                        } else {
                            view.setTranslationX(-horzMargin + vertMargin / 2);
                        }

                        // Scale the page down (between MIN_SCALE and 1)
                        view.setScaleX(scaleFactor);
                        view.setScaleY(scaleFactor);

                        // Fade the page relative to its size.
                        view.setAlpha(MIN_ALPHA +
                                (scaleFactor - MIN_SCALE) /
                                        (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                    } else { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        view.setAlpha(0f);
                    }
                }
            });
            Log.e("totalpage===", "" + pager2.getAdapter().getItemCount());
            pager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            if (Prefrence.getisUpload()) {
                setCurrentItem(0);
                Prefrence.setisUpload(false);

            } else {
                setCurrentItem(selectedPosition);
            }
            pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if(arrayList.size()>0) {
                        Prefrence.setnewsId(String.valueOf(arrayList.get(position).getNewsId()));
                    }
                    Log.e("original", String.valueOf(position));

                    selectedPosition = position;
                    // Toast.makeText(getActivity(), ""+pager2.getAdapter().getItemCount(), Toast.LENGTH_SHORT).show();
                    int totalCount = pager2.getAdapter().getItemCount();
                    // if(position+1 == pager2.getAdapter().getItemCount()){

                    if (callApi) {
                        callApi=false;
                    } else {

                        if (position + 1 == totalCount - 1) {
                            Log.e("original", String.valueOf(position));
                            getActivity().startService(new Intent(getActivity(), NewsViewsCount.class));
                            // int pageOffset=pager2.getAdapter().getItemCount()+10;
                            callServicegetNewsListNext("" + LastIndex, "50",
                                    "" + databeanMain.getCurrentIndex(), "" + databeanMain.getTableIndex(),
                                    // ""+databeanMain.getLoopDate());
                                    Utils.parseDateToddMMyyyy1(databeanMain.getLoopDate()));
                            //  Prefrence.setisUpload(true);
                            callApi = true;
                        }
                    }
                }
            });
        }
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
                    LastIndex=loginModel.getData().getLastIndex();
                   // dialog.setProgress(100);
                  //  dialog.dismiss();
                    pullrefresh.setRefreshing(false);
                    // arrayList = loginModel.getData().getPagedRecord();
                    arrayList.addAll(loginModel.getData().getPagedRecord());
                    databeanMain = loginModel.getData();
                    if (arrayList.size() > 0) {
                        autoPlaySet = arrayList.get(0).isAutoPlay();
                    }
                    inItItemRecycle();
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {
        Log.e("error", t.toString());
       // dialog.dismiss();
        Utils.showSnakBarDialog(getActivity(), lin_tophome, t.toString(), R.color.alert);
    }

    @Override
    public void onResume() {
        if (onActivityStateChanged != null) {
            onActivityStateChanged.onResumed();
        }
        super.onResume();
        arrayList.clear();
        callServicegetNewsList("0", "50", "0", "1", "01-Jan-1901");
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
        pager2.setCurrentItem(position, false);
    }

    public void setProgressSet() {
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait for a sec ..");
        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.show();
        final int totalProgressTime = 95;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(2000);
                        jumpTime += 2;
                        dialog.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }
    public class FadePageTransformer implements ViewPager.PageTransformer {
        public void transformPage(View view, float position) {

            if (position < -1 || position > 1) {
                view.setAlpha(0);
            }
            else if (position <= 0 || position <= 1) {
                // Calculate alpha. Position is decimal in [-1,0] or [0,1]
                float alpha = (position <= 0) ? position + 1 : 1 - position;
                view.setAlpha(alpha);
            }
            else if (position == 0) {
                view.setAlpha(1);
            }
        }
    }
}