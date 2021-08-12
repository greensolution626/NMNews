package com.android.nmnewsagency.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.MessageAdapter;
import com.android.nmnewsagency.adapter.MessageDetailAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.pref.Prefrence;
import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDetailActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MessageDetailAdapter locationAdapter;
    //  List<LocationModel> arrayList;
    List<String> arrayList;
    ImageView iamge_back_msgdetail, img_msg_detail,img_send;
    RelativeLayout rel_msgdetail_profile;
    String chatWith;
    TextView txt_chatwith;
    Firebase reference1, reference2;
    EditText  edit_msgarea;
    LinearLayout lin_mainlayouyt;
    ScrollView scollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_message_detail);
        Firebase.setAndroidContext(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
           chatWith= (String) bundle.get("chat");
        }
        inIt();

    }

    private void inIt() {
        scollview = (ScrollView) findViewById(R.id.scollview);
        recyclerView = (RecyclerView) findViewById(R.id.recy_mesage_detail);
        rel_msgdetail_profile = (RelativeLayout) findViewById(R.id.rel_msgdetail_profile);
        lin_mainlayouyt = (LinearLayout) findViewById(R.id.lin_mainlayouyt);
        iamge_back_msgdetail = (ImageView) findViewById(R.id.iamge_back_msgdetail);
        img_msg_detail = (ImageView) findViewById(R.id.img_msg_detail);
        img_send = (ImageView) findViewById(R.id.img_send);
        txt_chatwith = (TextView) findViewById(R.id.txt_chatwith);
        edit_msgarea = (EditText) findViewById(R.id.edit_msgarea);
        txt_chatwith.setText(chatWith);
        arrayList = new ArrayList<>();
        reference1 = new Firebase("https://nmnewsagency-24e4e-default-rtdb.firebaseio.com/messages/"
                + Prefrence.getName() + "_" + chatWith);
        reference2 = new Firebase("https://nmnewsagency-24e4e-default-rtdb.firebaseio.com/messages/"
                + chatWith + "_" + Prefrence.getName());
        iamge_back_msgdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDetailActivity.this.finish();
            }
        });
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = edit_msgarea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", Prefrence.getName());
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    edit_msgarea.setText("");
                }
            }
        });
        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(Prefrence.getName())){
                    addMessageBox(message, 1);
                }
                else{
                    addMessageBox(message, 2);
                }
            }

            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        img_msg_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBox();
            }
        });
        rel_msgdetail_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MessageDetailActivity.this,UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void inItItemRecycle() {
        arrayList.add("");
        arrayList.add("");
        locationAdapter = new MessageDetailAdapter(arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(locationAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void openDialogBox() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_userprofile);
        LinearLayout lin_dia_cancel = bottomSheetDialog.findViewById(R.id.lin_dia_cancel);
        LinearLayout lin_dia_report = bottomSheetDialog.findViewById(R.id.lin_dia_report);
        lin_dia_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBoxReport();
                bottomSheetDialog.cancel();
            }
        });
        lin_dia_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.cancel();
            }
        });
        bottomSheetDialog.show();
    }

    private void openDialogBoxReport() {
        Dialog dialog = new Dialog(MessageDetailActivity.this);
        dialog.setContentView(R.layout.dialog_report);
        ImageView img_close_dialog = dialog.findViewById(R.id.img_close_dialog);
        img_close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button but_submit = dialog.findViewById(R.id.but_submit);
        but_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void addMessageBox(String message, int type){
        TextView textView = new TextView(MessageDetailActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //lp2.weight = 7.0f;
lp2.topMargin=20;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.msgdetailbar);
            textView.setPadding(50,20,20,20);
        }
        else{
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.searchtopbar);
            textView.setPadding(20,20,50,20);
        }
        textView.setLayoutParams(lp2);
        lin_mainlayouyt.addView(textView);
        scollview.fullScroll(View.FOCUS_DOWN);
        lin_mainlayouyt.setNestedScrollingEnabled(true);
    }
}