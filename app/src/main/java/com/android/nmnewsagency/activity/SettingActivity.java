package com.android.nmnewsagency.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.NotificationSet;
import com.android.nmnewsagency.modelclass.UploadNewsModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements
        View.OnClickListener, Callback<Object> {
    LinearLayout lin_dialog, lin_topseting;
    ImageView iamge_back_seting;
    Button but_logout;
    BottomSheetDialog bottomSheetDialog;
    TextView txt_setng_location, txt_autoplay;
    RelativeLayout rel_uploaddoc, rel_privacy, rel_terms;
    static String whichOne;
    SwitchCompat chkState;
    Rest rest;
    String typeUserReporte = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_setting);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            typeUserReporte = (String) bundle.get("type");
        }

        rest = new Rest(this, this);
        iniIt();
        // setDataOnLocation();
    }

    private void iniIt() {
        lin_dialog = (LinearLayout) findViewById(R.id.lin_dialog);
        chkState = findViewById(R.id.chkState);
        lin_topseting = (LinearLayout) findViewById(R.id.lin_topseting);
        rel_uploaddoc = (RelativeLayout) findViewById(R.id.rel_uploaddoc);
        rel_terms = (RelativeLayout) findViewById(R.id.rel_terms);
        rel_privacy = (RelativeLayout) findViewById(R.id.rel_privacy);
        but_logout = (Button) findViewById(R.id.but_logout);
        txt_setng_location = (TextView) findViewById(R.id.txt_setng_location);
        txt_autoplay = (TextView) findViewById(R.id.txt_autoplay);
        if (Prefrence.getSetAutoplay().equalsIgnoreCase("true")) {
            txt_autoplay.setText("ON");
        } else {
            txt_autoplay.setText("OFF");
        }
        if (Prefrence.getSetNotification().equalsIgnoreCase("true")) {
            chkState.setChecked(true);
        } else {
            chkState.setChecked(false);
        }

        iamge_back_seting = (ImageView) findViewById(R.id.iamge_back_seting);
        lin_dialog.setOnClickListener(this);
        but_logout.setOnClickListener(this);
        rel_uploaddoc.setOnClickListener(this);
        txt_setng_location.setOnClickListener(this);
        rel_privacy.setOnClickListener(this);
        rel_terms.setOnClickListener(this);
        iamge_back_seting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        chkState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Prefrence.setSetNotication("true");
                    callServicegetNotification();

                } else {
                    Prefrence.setSetNotication("false");
                    callServicegetNotification();

                }
            }
        });
    }

    private void setDataOnLocation() {
        String s1 = Prefrence.gettahsil();
        // whichOne = sh.getString("whichone", "");
        if (s1.isEmpty()) {
            txt_setng_location.setText(Prefrence.getCityName());
        } else {
            txt_setng_location.setText(s1);
        }
        /*if(whichOne.equals("reporter")){
            rel_uploaddoc.setVisibility(View.GONE);
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(EventBus.getDefault().isRegistered(this)){}
        else{
            EventBus.getDefault().register(this);
        }
        setDataOnLocation();
    }

    private void openDialogBox() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_seting);
        ImageView on = bottomSheetDialog.findViewById(R.id.img_dialg_on);
        ImageView wifi = bottomSheetDialog.findViewById(R.id.img_dialg_wifi);
        ImageView off = bottomSheetDialog.findViewById(R.id.img_dialg_off);
        TextView txton = bottomSheetDialog.findViewById(R.id.txt_on);
        TextView txtwifi = bottomSheetDialog.findViewById(R.id.txt_wifi);
        TextView txtoff = bottomSheetDialog.findViewById(R.id.txt_off);
        LinearLayout linon = bottomSheetDialog.findViewById(R.id.lin_dia_on);
        LinearLayout linwifi = bottomSheetDialog.findViewById(R.id.lin_dia_wifi);
        LinearLayout linoff = bottomSheetDialog.findViewById(R.id.lin_dia_off);
        if (txt_autoplay.getText().toString().equalsIgnoreCase("on")) {
            on.setVisibility(View.VISIBLE);
            txton.setTextColor(getResources().getColor(R.color.hashtagfolow));
            wifi.setVisibility(View.INVISIBLE);
            txtwifi.setTextColor(getResources().getColor(R.color.loctitle));
            off.setVisibility(View.INVISIBLE);
            txtoff.setTextColor(getResources().getColor(R.color.loctitle));
        } else {
            on.setVisibility(View.INVISIBLE);
            txton.setTextColor(getResources().getColor(R.color.loctitle));
            wifi.setVisibility(View.INVISIBLE);
            txtwifi.setTextColor(getResources().getColor(R.color.loctitle));
            off.setVisibility(View.VISIBLE);
            txtoff.setTextColor(getResources().getColor(R.color.hashtagfolow));
        }
        linon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                on.setVisibility(View.VISIBLE);
                txton.setTextColor(getResources().getColor(R.color.hashtagfolow));
                wifi.setVisibility(View.INVISIBLE);
                txtwifi.setTextColor(getResources().getColor(R.color.loctitle));
                off.setVisibility(View.INVISIBLE);
                txtoff.setTextColor(getResources().getColor(R.color.loctitle));
                txt_autoplay.setText("ON");
                Prefrence.setSetAutoplay("true");
                callServicegetAutoplay();
                bottomSheetDialog.cancel();

            }
        });
        linwifi.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                on.setVisibility(View.INVISIBLE);
                txton.setTextColor(getResources().getColor(R.color.loctitle));
                wifi.setVisibility(View.VISIBLE);
                txtwifi.setTextColor(getResources().getColor(R.color.hashtagfolow));
                off.setVisibility(View.INVISIBLE);
                txtoff.setTextColor(getResources().getColor(R.color.loctitle));
            }
        });
        linoff.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                on.setVisibility(View.INVISIBLE);
                txton.setTextColor(getResources().getColor(R.color.loctitle));
                wifi.setVisibility(View.INVISIBLE);
                txtwifi.setTextColor(getResources().getColor(R.color.loctitle));
                off.setVisibility(View.VISIBLE);
                txtoff.setTextColor(getResources().getColor(R.color.hashtagfolow));
                txt_autoplay.setText("OFF");
                Prefrence.setSetAutoplay("false");
                callServicegetAutoplay();
                bottomSheetDialog.cancel();

            }
        });
        bottomSheetDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_dialog:
                openDialogBox();
                break;
            case R.id.but_logout:
                closrAllActivity();
                break;
            case R.id.txt_setng_location:
                closrAllActivityLoc();
                break;
            case R.id.rel_uploaddoc:
                Intent intent = new Intent(SettingActivity.this,
                        UploadDocumentActivity.class);
                startActivity(intent);
                break;
            case R.id.rel_terms:
                Intent intent1 = new Intent(SettingActivity.this,
                        PrivacyTermsActivity.class);
                intent1.putExtra("pageId", 1);
                startActivity(intent1);
                break;
            case R.id.rel_privacy:
                Intent intent2 = new Intent(SettingActivity.this,
                        PrivacyTermsActivity.class);
                intent2.putExtra("pageId", 2);
                startActivity(intent2);
                break;
        }
    }

    private void closrAllActivity() {
        Prefrence.removeOrClearAllPerferanceData();
        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                Intent intent = new Intent(SettingActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    private void closrAllActivityLoc() {
        if (!typeUserReporte.equals("")) {
            if (typeUserReporte.equalsIgnoreCase("user")) {
                Intent intent = new Intent(SettingActivity.this,
                        LocationReqActivity.class);
                startActivity(intent);
            }
        }

    }

    private void callServicegetNotification() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.setnotification();
    }

    private void callServicegetAutoplay() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.setAutoplay();
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof NotificationSet) {
                NotificationSet loginModel = (NotificationSet) obj;
                if (loginModel.isStatus()) {
                    Utils.showSnakBarDialog(this, lin_topseting, "Successfully set", R.color.msgresponce);
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResultReceived(UploadNewsModel.DataBean dataBean) {
        Intent intent = new Intent(SettingActivity.this, NewVideoActivity.class);
        intent.putExtra("video", dataBean);
        startActivity(intent);
        // finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}