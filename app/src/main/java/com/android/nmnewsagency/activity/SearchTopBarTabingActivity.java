package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.PagerTabHashTagAdapter;
import com.android.nmnewsagency.fragment.FragmentSearchHashTagTab;
import com.android.nmnewsagency.fragment.FragmentSearchTopTab;
import com.android.nmnewsagency.model.CountryModel;
import com.android.nmnewsagency.modelclass.SearchTopSearchModel;
import com.android.nmnewsagency.rest.Rest;
import com.athbk.slidingtablayout.TabLayout;
import com.athbk.slidingtablayout.model.TabInfo;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTopBarTabingActivity extends AppCompatActivity implements Callback<Object> {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView iamge_back_searchtopbar;
    Rest rest;
    EditText search_edit;
    EventBus eventBus;
    PagerTabHashTagAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_search_top_bar_tabing);
        rest = new Rest(this, this);
        inIt();
    }

    private void inIt() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        search_edit = (EditText) findViewById(R.id.search_edit);
        iamge_back_searchtopbar = (ImageView) findViewById(R.id.iamge_back_searchtopbar);
        tabLayout = findViewById(R.id.tabLayout);
        ArrayList<TabInfo> listTab = new ArrayList<>();
        TabInfo tabInfo1 = new TabInfo("Top");
        TabInfo tabInfo2 = new TabInfo("People");
        TabInfo tabInfo3 = new TabInfo("Hashtag");

        listTab.add(tabInfo1);
        listTab.add(tabInfo2);
        listTab.add(tabInfo3);

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(FragmentSearchTopTab.newInstance());
        listFragment.add(FragmentSearchTopTab.newInstance());
        listFragment.add(FragmentSearchHashTagTab.newInstance());

        adapter = new PagerTabHashTagAdapter(this, this.getSupportFragmentManager(), listFragment, listTab);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, adapter);

        iamge_back_searchtopbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Toast.makeText(SearchTopBarTabingActivity.this, String.valueOf(count), Toast.LENGTH_SHORT).show();
                if (count > 2) {
                    callServicegetTopSaerch(search_edit.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // callServicegetTopSaerch("");
    }

    private void callServicegetTopSaerch(String query) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getNewsTopSearch(1, query);
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof SearchTopSearchModel) {
                SearchTopSearchModel loginModel = (SearchTopSearchModel) obj;
                if (loginModel.isStatus()) {

                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


/*
    // FragmentSearchTopTab.setSataOnViews(loginModel.getData().getData().getPagedRecord());
    FragmentSearchTopTab fragmentSearchTopTab = FragmentSearchTopTab.newInstance();
                    fragmentSearchTopTab.setSataOnViews(loginModel.getData().getData().getPagedRecord());
    //FragmentSearchTopTab.newInstance().setSataOnViews(loginModel.getData().getData().getPagedRecord());
                    Toast.makeText(this, String.valueOf(fragmentSearchTopTab.isResumed()), Toast.LENGTH_SHORT).show();
    ;*/
}