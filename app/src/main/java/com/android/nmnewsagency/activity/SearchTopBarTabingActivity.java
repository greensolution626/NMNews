package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.PagerTabHashTagAdapter;
import com.android.nmnewsagency.fragment.FragmentSearchHashTagTab;
import com.android.nmnewsagency.fragment.FragmentSearchTopTab;
import com.athbk.slidingtablayout.TabLayout;
import com.athbk.slidingtablayout.model.TabInfo;

import java.util.ArrayList;

public class SearchTopBarTabingActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView iamge_back_searchtopbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_search_top_bar_tabing);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
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

        PagerTabHashTagAdapter adapter = new PagerTabHashTagAdapter(this.getSupportFragmentManager(), listFragment, listTab);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, adapter);
        iamge_back_searchtopbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}