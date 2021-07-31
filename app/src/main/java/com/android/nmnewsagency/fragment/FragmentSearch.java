package com.android.nmnewsagency.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.MainActivity;
import com.android.nmnewsagency.activity.SearchTopBarTabingActivity;
import com.android.nmnewsagency.adapter.HashTagAdapter;
import com.android.nmnewsagency.adapter.HomeAdapter;
import com.android.nmnewsagency.adapter.UsersAdapter;
import com.android.nmnewsagency.adapter.VideosAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {
    View view;
    RecyclerView recyclerView,recyclerView_users,recyclerView_video;
    HashTagAdapter locationAdapter;
    UsersAdapter userAdapter;
    VideosAdapter videoAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList,arrayList_users,arrayList_video;
    List<Integer> imgList,imgList1;
    EditText search_edit;
    LinearLayout lin_onlysearch;
    Animation myAnim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_hashtag);
        recyclerView_users = (RecyclerView) view.findViewById(R.id.recy_users);
        recyclerView_video = (RecyclerView) view.findViewById(R.id.recy_video);
        lin_onlysearch = (LinearLayout) view.findViewById(R.id.lin_onlysearch);
        myAnim = AnimationUtils.loadAnimation(getActivity(),R.anim.bounce);
        arrayList = new ArrayList<>();
        arrayList_users = new ArrayList<>();
        arrayList_video = new ArrayList<>();
        imgList = new ArrayList<>();
        imgList1 = new ArrayList<>();
        inItItemRecycle();
        lin_onlysearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin_onlysearch.startAnimation(myAnim);
                Intent intent = new Intent(getActivity(), SearchTopBarTabingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void inItItemRecycle() {
        arrayList.add("#corona");
        arrayList.add("#modi");
        arrayList.add("#covid19");
        arrayList.add("#india");
        arrayList.add("#congress");
        arrayList.add("#recivevideo");
        arrayList.add("#foodvideo");
        arrayList.add("#lockdown");
        arrayList.add("#sunami");

        arrayList_users.add("Sunil Kumar");
        arrayList_users.add("Rajesh Kumar");
        arrayList_users.add("Asheesh Kumar");
        arrayList_users.add("Vishesh Kumar");
        arrayList_users.add("Avdesh Kumar");

        arrayList_video.add("");
        arrayList_video.add("");
        arrayList_video.add("");
        arrayList_video.add("");
        arrayList_video.add("");

        imgList.add(R.drawable.search1);
        imgList.add(R.drawable.search2);
        imgList.add(R.drawable.search3);
        imgList.add(R.drawable.search4);
        imgList.add(R.drawable.search5);

        imgList1.add(R.drawable.homeimagevideo);
        imgList1.add(R.drawable.home1);
        imgList1.add(R.drawable.homeimagevideo);
        imgList1.add(R.drawable.home1);

        locationAdapter = new HashTagAdapter(arrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(locationAdapter);

        userAdapter = new UsersAdapter(getActivity(),arrayList_users,imgList);
        recyclerView_users.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        recyclerView_users.setItemAnimator(new DefaultItemAnimator());
        recyclerView_users.setNestedScrollingEnabled(false);
        recyclerView_users.setHasFixedSize(false);
        recyclerView_users.setAdapter(userAdapter);

        videoAdapter = new VideosAdapter(getActivity(),arrayList_video,imgList1);
        recyclerView_video.setLayoutManager(new GridLayoutManager(getActivity(),1 , GridLayoutManager.HORIZONTAL, false));
        recyclerView_video.setItemAnimator(new DefaultItemAnimator());
        recyclerView_video.setNestedScrollingEnabled(false);
        recyclerView_video.setHasFixedSize(false);
        recyclerView_video.setAdapter(videoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
               // Toast.makeText(getActivity(), arrayList.get(position) + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_loc, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}
