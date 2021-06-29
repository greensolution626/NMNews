package com.android.nmnewsagency.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.EditProfileActivity;
import com.android.nmnewsagency.activity.FollowersActivituy;
import com.android.nmnewsagency.activity.FollowingActivituy;
import com.android.nmnewsagency.activity.MessageActivity;
import com.android.nmnewsagency.activity.PerformanceReporter;
import com.android.nmnewsagency.activity.SettingActivity;
import com.android.nmnewsagency.activity.UploadDocumentActivity;
import com.android.nmnewsagency.activity.UserProfileActivity;
import com.android.nmnewsagency.adapter.HashTagDetailAdapter;
import com.android.nmnewsagency.adapter.ProfileAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    View view;
    RecyclerView recyclerView;
    ProfileAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    List<Integer> imAGE;
    RelativeLayout rel_mesage, rel_covrage_quality, rel_upload_doc;
    FrameLayout frame_editprofile;
    LinearLayout lin_folowing, lin_folowers, lin_profile_video,
            lin_profile_share, lin_profile_contact, lin_profile_video_video;
    ImageView image_setting, img_profile_video_video,
            img_profile_contact, img_profile_share, img_profile_video;
    Animation myAnim;
    SharedPreferences sh;
    TextView txt_nodata_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        sh = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);

        myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy_profile);
        rel_mesage = (RelativeLayout) view.findViewById(R.id.rel_mesage);
        rel_upload_doc = (RelativeLayout) view.findViewById(R.id.rel_upload_doc);
        rel_covrage_quality = (RelativeLayout) view.findViewById(R.id.rel_covrage_quality);
        frame_editprofile = (FrameLayout) view.findViewById(R.id.frame_editprofile);
        lin_folowing = (LinearLayout) view.findViewById(R.id.lin_folowing);
        lin_folowers = (LinearLayout) view.findViewById(R.id.lin_folowers);

        lin_profile_video = (LinearLayout) view.findViewById(R.id.lin_profile_video);
        lin_profile_share = (LinearLayout) view.findViewById(R.id.lin_profile_share);
        lin_profile_contact = (LinearLayout) view.findViewById(R.id.lin_profile_contact);
        lin_profile_video_video = (LinearLayout) view.findViewById(R.id.lin_profile_video_video);
        image_setting = (ImageView) view.findViewById(R.id.image_setting);

        img_profile_video_video = (ImageView) view.findViewById(R.id.img_profile_video_video);
        img_profile_contact = (ImageView) view.findViewById(R.id.img_profile_contact);
        img_profile_share = (ImageView) view.findViewById(R.id.img_profile_share);
        img_profile_video = (ImageView) view.findViewById(R.id.img_profile_video);
        txt_nodata_profile = (TextView) view.findViewById(R.id.txt_nodata_profile);


        iNiTonClick();
        inItItemRecycle();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       /* String s1 = sh.getString("whichone", "");
        if (s1.equals("")) {
            rel_covrage_quality.setVisibility(View.GONE);
            rel_upload_doc.setVisibility(View.GONE);
        }
        if (s1.equals("user")) {
            rel_covrage_quality.setVisibility(View.GONE);
            rel_upload_doc.setVisibility(View.GONE);
        }
        if (s1.equals("reporter")) {
            rel_covrage_quality.setVisibility(View.VISIBLE);
            rel_upload_doc.setVisibility(View.VISIBLE);
        }*/
    }

    private void iNiTonClick() {
        rel_mesage.setOnClickListener(this);
        rel_covrage_quality.setOnClickListener(this);
        rel_upload_doc.setOnClickListener(this);
        frame_editprofile.setOnClickListener(this);
        lin_folowing.setOnClickListener(this);
        lin_folowers.setOnClickListener(this);
        image_setting.setOnClickListener(this);

        lin_profile_video_video.setOnClickListener(this);
        lin_profile_contact.setOnClickListener(this);
        lin_profile_share.setOnClickListener(this);
        lin_profile_video.setOnClickListener(this);
    }

    private void inItItemRecycle() {
        arrayList = new ArrayList<>();
        imAGE = new ArrayList<>();
        imAGE.add(R.drawable.profileimage);
        imAGE.add(R.drawable.profileimage1);
        imAGE.add(R.drawable.profileimage2);
        imAGE.add(R.drawable.profileimage3);
        imAGE.add(R.drawable.profileimage4);
        imAGE.add(R.drawable.profileimage5);
        arrayList.add("");
        arrayList.add("");
        locationAdapter = new ProfileAdapter(getActivity(), arrayList, imAGE);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Movie movie = movieList.get(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rel_mesage:
                rel_mesage.startAnimation(myAnim);
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_covrage_quality:
                rel_covrage_quality.startAnimation(myAnim);
                Intent intent1 = new Intent(getActivity(), PerformanceReporter.class);
                startActivity(intent1);
                break;
            case R.id.rel_upload_doc:
                rel_upload_doc.startAnimation(myAnim);
                Intent intent2 = new Intent(getActivity(), UploadDocumentActivity.class);
                startActivity(intent2);
                break;
            case R.id.frame_editprofile:
                frame_editprofile.startAnimation(myAnim);
                Intent intent3 = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent3);
                break;
            case R.id.lin_folowers:
                lin_folowers.startAnimation(myAnim);
                Intent intent4 = new Intent(getActivity(), FollowersActivituy.class);
                startActivity(intent4);
                break;
            case R.id.lin_folowing:
                lin_folowing.startAnimation(myAnim);
                Intent intent5 = new Intent(getActivity(), FollowingActivituy.class);
                startActivity(intent5);
                break;
            case R.id.image_setting:
                image_setting.startAnimation(myAnim);
                Intent intent6 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent6);
                break;
            case R.id.lin_profile_video:
                txt_nodata_profile.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                changebagroundimagebycondition(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                break;
            case R.id.lin_profile_share:
                txt_nodata_profile.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                changebagroundimagebycondition1(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                break;
            case R.id.lin_profile_contact:
                txt_nodata_profile.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                changebagroundimagebycondition2(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                break;
            case R.id.lin_profile_video_video:
                txt_nodata_profile.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                changebagroundimagebycondition3(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                break;
        }
    }

    public void changebagroundimagebycondition(ImageView view1, ImageView view2,
                                               ImageView view3, ImageView view4) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view1.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_7830));
            view2.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_bookmark));
            view3.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_7829));
            view4.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_video));
        } else {
            view1.setImageResource
                    (R.drawable.ic_group_7830);
            view2.setImageResource
                    (R.drawable.ic_bookmark);
            view3.setImageResource
                    (R.drawable.ic_group_7829);
            view4.setImageResource
                    (R.drawable.ic_video);
        }

    }

    public void changebagroundimagebycondition1(ImageView view1, ImageView view2, ImageView view3, ImageView view4) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view1.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_78301));
            view2.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_bookmark1));
            view3.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_7829));
            view4.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_video));
        } else {
            view1.setImageResource
                    (R.drawable.ic_group_78301);
            view2.setImageResource
                    (R.drawable.ic_bookmark1);
            view3.setImageResource
                    (R.drawable.ic_group_7829);
            view4.setImageResource
                    (R.drawable.ic_video);
        }

    }

    public void changebagroundimagebycondition2(ImageView view1, ImageView view2, ImageView view3, ImageView view4) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view1.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_78301));
            view2.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_bookmark));
            view3.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_78291));
            view4.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_video));
        } else {
            view1.setImageResource
                    (R.drawable.ic_group_78301);
            view2.setImageResource
                    (R.drawable.ic_bookmark);
            view3.setImageResource
                    (R.drawable.ic_group_78291);
            view4.setImageResource
                    (R.drawable.ic_video);
        }

    }

    public void changebagroundimagebycondition3(ImageView view1, ImageView view2, ImageView view3, ImageView view4) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view1.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_78301));
            view2.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_bookmark));
            view3.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_group_7829));
            view4.setBackgroundDrawable
                    (ContextCompat.getDrawable
                            (getActivity(), R.drawable.ic_video11));
        } else {
            view1.setImageResource
                    (R.drawable.ic_group_78301);
            view2.setImageResource
                    (R.drawable.ic_bookmark);
            view3.setImageResource
                    (R.drawable.ic_group_7829);
            view4.setImageResource
                    (R.drawable.ic_video11);
        }

    }

}