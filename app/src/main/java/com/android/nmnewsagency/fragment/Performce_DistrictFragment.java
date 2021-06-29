package com.android.nmnewsagency.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.AdapterSectionRecycler;
import com.android.nmnewsagency.adapter.Performance_DistricAdapter;
import com.android.nmnewsagency.adapter.SectionHeader;
import com.android.nmnewsagency.model.Child;

import java.util.ArrayList;
import java.util.List;


public class Performce_DistrictFragment extends Fragment {

   View view;
    RecyclerView recyclerView;
    Performance_DistricAdapter adapterRecycler;
    List<SectionHeader> sectionHeaders;

    public static Performce_DistrictFragment newInstance() {
        Bundle args = new Bundle();
        Performce_DistrictFragment fragment = new Performce_DistrictFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_performce__district, container, false);
        recyclerView = (RecyclerView)view. findViewById(R.id.recy_perf_district);
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
        childList.add(new Child("1",1));
        childList.add(new Child("2",9));
        childList.add(new Child("3",9));
        childList.add(new Child("4",9));

        //Create a List of SectionHeader DataModel implements SectionHeader
        sectionHeaders = new ArrayList<>();
        sectionHeaders.add(new SectionHeader(childList, "Your District Top Stories", 6));

        childList = new ArrayList<>();
        childList.add(new Child("1",9));
        childList.add(new Child("2",9));
        childList.add(new Child("3",9));
        sectionHeaders.add(new SectionHeader(childList, "Your State Top Stories", 2));

        adapterRecycler = new Performance_DistricAdapter(getActivity(), sectionHeaders);
        recyclerView.setAdapter(adapterRecycler);
    }
}