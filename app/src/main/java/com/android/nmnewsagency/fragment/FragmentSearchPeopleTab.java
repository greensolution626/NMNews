package com.android.nmnewsagency.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;

import com.android.nmnewsagency.R;

public class FragmentSearchPeopleTab extends Fragment {

    public static FragmentSearchPeopleTab newInstance() {
        Bundle args = new Bundle();
        FragmentSearchPeopleTab fragment = new FragmentSearchPeopleTab();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.searchpeoplefragment, container, false);
    }
}