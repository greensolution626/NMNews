package com.android.nmnewsagency.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.FollowersActivituy;
import com.android.nmnewsagency.activity.OwnVideoDetailActivity;
import com.android.nmnewsagency.adapter.AdapterSectionRecycler;
import com.android.nmnewsagency.adapter.HashTagAdapter;
import com.android.nmnewsagency.adapter.SectionHeader;
import com.android.nmnewsagency.adapter.UsersAdapter;
import com.android.nmnewsagency.adapter.VideosAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.model.Child;

import java.util.ArrayList;
import java.util.List;

public class FragmentNotification extends Fragment {
    View view;

    RecyclerView recyclerView;
    AdapterSectionRecycler adapterRecycler;
    List<SectionHeader> sectionHeaders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.recy_notification);
        inItRecycle();
        return view;
    }

    private void inItRecycle() {
        //setLayout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(true);

        //Create a List of Child DataModel
        List<Child> childList = new ArrayList<>();
        childList.add(new Child("April",R.drawable.noti1));
        childList.add(new Child("Austin",R.drawable.noti2));
        childList.add(new Child("Alex",R.drawable.noti3));
        childList.add(new Child("Aakash",R.drawable.notiimage));

        //Create a List of SectionHeader DataModel implements SectionHeader
        sectionHeaders = new ArrayList<>();
        sectionHeaders.add(new SectionHeader(childList, "Today", 6));

        childList = new ArrayList<>();
        childList.add(new Child("Bill Gates",R.drawable.noti4));
        childList.add(new Child("Bob Proctor",R.drawable.noti5));
        childList.add(new Child("Bryan Tracy",R.drawable.noti6));
        sectionHeaders.add(new SectionHeader(childList, "This week", 2));

        adapterRecycler = new AdapterSectionRecycler(getActivity(), sectionHeaders);
        recyclerView.setAdapter(adapterRecycler);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent4 = new Intent(getActivity(), OwnVideoDetailActivity.class);
                startActivity(intent4);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}
