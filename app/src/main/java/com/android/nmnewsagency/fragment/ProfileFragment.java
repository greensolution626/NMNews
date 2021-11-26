package com.android.nmnewsagency.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.EditProfileActivity;
import com.android.nmnewsagency.activity.FollowersActivituy;
import com.android.nmnewsagency.activity.FollowingActivituy;
import com.android.nmnewsagency.activity.MessageActivity;
import com.android.nmnewsagency.activity.PerformanceReporter;
import com.android.nmnewsagency.activity.SettingActivity;
import com.android.nmnewsagency.activity.UploadDocumentActivity;
import com.android.nmnewsagency.adapter.GetUserHashNewsAdapter;
import com.android.nmnewsagency.adapter.GetUserOwnNewsAdapter;
import com.android.nmnewsagency.adapter.GetUserSaveNewsAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.modelclass.GetProfileDataModel;
import com.android.nmnewsagency.modelclass.GetUserHashTagModel;
import com.android.nmnewsagency.modelclass.GetUserOwnNewsModel;
import com.android.nmnewsagency.modelclass.GetUserSaveNewsModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener, Callback<Object> {
    View view;
    RecyclerView recyclerView;
    GetUserSaveNewsAdapter locationAdapter;
    GetUserOwnNewsAdapter locationAdapter1;
    GetUserHashNewsAdapter locationAdapter2;
    List<GetUserSaveNewsModel.DataBeanX.DataBean.PagedRecordBean> arrayList;
    List<GetUserOwnNewsModel.DataBeanX.DataBean.PagedRecordBean> arrayListOwn;
    List<GetUserHashTagModel.DataBeanX.DataBean.PagedRecordBean> arrayListHash;
    List<Integer> imAGE;
    RelativeLayout rel_mesage, rel_covrage_quality, rel_upload_doc;
    FrameLayout frame_editprofile;
    LinearLayout lin_folowing, lin_folowers, lin_profile_video,
            lin_profile_share, lin_profile_contact, lin_profile_video_video;
    ImageView image_setting, img_profile_video_video,
            img_profile_contact, img_profile_share, img_profile_video, img_profile;
    Animation myAnim;
    TextView txt_nodata_profile, text_name, txt_gat, txt_profilefolowinmg, txt_profilefolow, txt_aboutus,
            txt_userid,txt_covragescore,txt_hashvideo,txt_savevideo,txt_ownvideo,txt_covrge;
    Rest rest;
    GetProfileDataModel.DataBean.AspNetUserBean dataBean;
    String typeWhich="own",typeUserReporter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        iniT();
        return view;
    }

    private void iniT() {
        rest = new Rest(getActivity(), this);
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
        img_profile = (ImageView) view.findViewById(R.id.img_profile);
        txt_nodata_profile = (TextView) view.findViewById(R.id.txt_nodata_profile);
        txt_covragescore = (TextView) view.findViewById(R.id.txt_covragescore);
        txt_gat = (TextView) view.findViewById(R.id.txt_gat);
        txt_profilefolow = (TextView) view.findViewById(R.id.txt_profilefolow);
        txt_profilefolowinmg = (TextView) view.findViewById(R.id.txt_profilefolowinmg);
        text_name = (TextView) view.findViewById(R.id.text_name);
        txt_aboutus = (TextView) view.findViewById(R.id.txt_aboutus);
        txt_userid = (TextView) view.findViewById(R.id.txt_userid);
        txt_hashvideo = (TextView) view.findViewById(R.id.txt_hashvideo);
        txt_savevideo = (TextView) view.findViewById(R.id.txt_savevideo);
        txt_ownvideo = (TextView) view.findViewById(R.id.txt_ownvideo);
        txt_covrge = (TextView) view.findViewById(R.id.txt_covrge);
        txt_covrge.setSelected(true);
       /* if (!Prefrence.getName().equals("") && Prefrence.getName() != null) {
           // text_name.setText(Prefrence.getName());
        }*/
        iNiTonClick();

        // inItItemRecycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        callServicegetProfiel();
    }

    private void callServicegetProfiel() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getProfileList();
    }

    private void callServicegetUserOwnNews() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getuserOwnVideo();
    }

    private void callServicegetHashtagVideo() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getuserHashtagVideo(Prefrence.getUserId());
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

    private void inItItemRecycle(String type) {
        if (type.equals("save")) {
            locationAdapter = new GetUserSaveNewsAdapter(getActivity(), arrayList);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
           // gridLayoutManager.setReverseLayout(true);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setAdapter(locationAdapter);
            recyclerView.smoothScrollToPosition(recyclerView.getBottom());
        }
       else  if (type.equals("own")) {
            locationAdapter1 = new GetUserOwnNewsAdapter(getActivity(), arrayListOwn);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
           // gridLayoutManager.setReverseLayout(true);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(locationAdapter1);
            recyclerView.smoothScrollToPosition(recyclerView.getBottom());
        }
        else if (type.equals("hash")) {
            locationAdapter2 = new GetUserHashNewsAdapter(getActivity(), arrayListHash);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
           // gridLayoutManager.setReverseLayout(true);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
            recyclerView.setAdapter(locationAdapter2);
            recyclerView.smoothScrollToPosition(recyclerView.getBottom());
        }
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
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
                intent3.putExtra("profileimage", dataBean.getAvatar());
                intent3.putExtra("firstname", dataBean.getFirstName());
                intent3.putExtra("lastname", dataBean.getLastName());
                intent3.putExtra("aboutme", (String) dataBean.getAboutMe());
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
                intent6.putExtra("type",typeUserReporter);

                startActivity(intent6);
                break;
            case R.id.lin_profile_video:
                // txt_nodata_profile.setVisibility(View.GONE);
                // recyclerView.setVisibility(View.VISIBLE);
                changebagroundimagebycondition(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                typeWhich="own";
                callServicegetUserOwnNews();
                break;
            case R.id.lin_profile_share:
                //  txt_nodata_profile.setVisibility(View.VISIBLE);
                //  recyclerView.setVisibility(View.GONE);
                changebagroundimagebycondition1(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                typeWhich="save";
                callServicegetUserSaveVideo();
                break;
            case R.id.lin_profile_contact:
                //  txt_nodata_profile.setVisibility(View.VISIBLE);
                //  recyclerView.setVisibility(View.GONE);
                changebagroundimagebycondition2(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                typeWhich="hash";
                callServicegetHashtagVideo();
                break;
            case R.id.lin_profile_video_video:
                // txt_nodata_profile.setVisibility(View.VISIBLE);
                // recyclerView.setVisibility(View.GONE);
                changebagroundimagebycondition3(img_profile_video, img_profile_share, img_profile_contact, img_profile_video_video);
                break;
        }
    }

    private void callServicegetUserSaveVideo() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getusersaveNews(Prefrence.getUserId());
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

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof GetProfileDataModel) {
                GetProfileDataModel loginModel = (GetProfileDataModel) obj;
                if (loginModel.isStatus()) {
                    this.dataBean = loginModel.getData().getAspNetUser();
                    setDTaontextView(loginModel.getData().getAspNetUser());
                }
            }
            if (obj instanceof GetUserSaveNewsModel) {
                GetUserSaveNewsModel loginModel = (GetUserSaveNewsModel) obj;
                if (loginModel.isStatus()) {
                    arrayList = loginModel.getData().getData().getPagedRecord();
                    inItItemRecycle("save");
                }
            }
            if (obj instanceof GetUserOwnNewsModel) {
                GetUserOwnNewsModel loginModel = (GetUserOwnNewsModel) obj;
                if (loginModel.isStatus()) {
                    arrayListOwn = loginModel.getData().getData().getPagedRecord();
                  //  Log.e("frstindex==",""+arrayListOwn.get(0).getNewsId());
                    inItItemRecycle("own");
                }
            }
            if (obj instanceof GetUserHashTagModel) {
                GetUserHashTagModel loginModel = (GetUserHashTagModel) obj;
                if (loginModel.isStatus()) {
                    arrayListHash = loginModel.getData().getData().getPagedRecord();
                    inItItemRecycle("hash");
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    public void setDTaontextView(GetProfileDataModel.DataBean.AspNetUserBean aspNetUser) {
        text_name.setText(aspNetUser.getFullName());
        txt_userid.setText("@" + aspNetUser.getUserName());
        txt_profilefolow.setText(aspNetUser.getFollowersSuffix());
        txt_ownvideo.setText(String.valueOf(aspNetUser.getNewsCount()));
        txt_savevideo.setText(String.valueOf(aspNetUser.getSavedNewsCount()));
        txt_hashvideo.setText(String.valueOf(aspNetUser.getUserTagVideo()));
        txt_profilefolowinmg.setText(aspNetUser.getFollowingsSuffix());
        txt_covragescore.setText(String.valueOf(aspNetUser.getProfile_Score())+" %");
        if (aspNetUser.getAboutMe() != null) {
            txt_aboutus.setText((String) aspNetUser.getAboutMe());
        }
        if (aspNetUser.getAvatar() != null) {
            if (!aspNetUser.getAvatar().equals("null")) {
                Glide.with(requireActivity())
                        .load(aspNetUser.getAvatar())
                        .into(img_profile);
               /* new Handler().post(new Runnable() {
                    public void run() {

                    }
                });*/
            }
        }
        typeUserReporter=aspNetUser.getUserType();
       if(aspNetUser.isSendNotification()){
           Prefrence.setSetNotication("true");
       }else{
           Prefrence.setSetNotication("false");
       }
        if(aspNetUser.getUserType().equalsIgnoreCase("user")){
            rel_covrage_quality.setVisibility(View.GONE);
            rel_upload_doc.setVisibility(View.GONE);
        }
        callServicegetUserOwnNews();
    }

}