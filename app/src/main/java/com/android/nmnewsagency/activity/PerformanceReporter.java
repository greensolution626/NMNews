package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.PagerTabCovrageQualityAdapter;
import com.android.nmnewsagency.adapter.Performance_DistricAdapter;
import com.android.nmnewsagency.adapter.Performance_SelfAdapter;
import com.android.nmnewsagency.fragment.Performance_SelfFragment;
import com.android.nmnewsagency.fragment.Performce_DistrictFragment;
import com.athbk.slidingtablayout.SlidingTabLayout;
import com.athbk.slidingtablayout.TabLayout;
import com.athbk.slidingtablayout.model.TabInfo;

import java.util.ArrayList;

public class PerformanceReporter extends AppCompatActivity {
    private ViewPager viewPager;
   // private TabLayout tabLayout;
   private TabLayout tabLayout;
   ImageView iamge_back_performnce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_performance_reporter);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        iamge_back_performnce = (ImageView) findViewById(R.id.iamge_back_performnce);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ArrayList<TabInfo> listTab = new ArrayList<>();
        TabInfo tabInfo1 = new TabInfo("Self");
        TabInfo tabInfo2 = new TabInfo("District");

        listTab.add(tabInfo1);
        listTab.add(tabInfo2);

        ArrayList<Fragment> listFragment = new ArrayList<>();
        listFragment.add(Performance_SelfFragment.newInstance());
        listFragment.add(Performce_DistrictFragment.newInstance());

        PagerTabCovrageQualityAdapter adapter = new PagerTabCovrageQualityAdapter(getSupportFragmentManager(), listFragment, listTab);
        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager, adapter);
        iamge_back_performnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}