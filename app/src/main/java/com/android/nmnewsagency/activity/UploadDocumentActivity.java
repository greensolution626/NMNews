package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.nmnewsagency.R;

public class UploadDocumentActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView iamge_back_uploaddoc, img_bank, img_pancard, img_presid;
    static int count = 0;
    int REQUEST_GET_SINGLE_FILE = 99;
    LinearLayout lin_bank, lin_pancard, lin_presid;
    String cliocl;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_upload_document);
        iamge_back_uploaddoc = (ImageView) findViewById(R.id.iamge_back_uploaddoc);
        img_presid = (ImageView) findViewById(R.id.img_presid);
        img_pancard = (ImageView) findViewById(R.id.img_pancard);
        img_bank = (ImageView) findViewById(R.id.img_bank);
        lin_presid = (LinearLayout) findViewById(R.id.lin_presid);
        lin_bank = (LinearLayout) findViewById(R.id.lin_bank);
        lin_pancard = (LinearLayout) findViewById(R.id.lin_pancard);

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        iamge_back_uploaddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadDocumentActivity.this.finish();
            }
        });
        lin_bank.setOnClickListener(this);
        lin_pancard.setOnClickListener(this);
        lin_presid.setOnClickListener(this);
        setDataOnLocation();
    }

    private void setDataOnLocation() {
        //  String s1 = sharedPreferences.getString("tahsil", "");
        String whichOne = sharedPreferences.getString("whichone", "");
        if (whichOne.equals("reporter")) {
            changebagroundimagebycondition1();
        }

    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GET_SINGLE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    if (selectedImageUri != null) {
                        count++;
                        changeImageBagroundfile();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    private void changeImageBagroundfile() {
        if (cliocl.equals("press")) {
            changebagroundimagebycondition(img_presid);
        } else if (cliocl.equals("bank")) {
            changebagroundimagebycondition(img_bank);
        } else if (cliocl.equals("pancard")) {
            changebagroundimagebycondition(img_pancard);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_presid:
                cliocl = "press";
                openGallery();
                break;
            case R.id.lin_bank:
                cliocl = "bank";
                openGallery();
                break;
            case R.id.lin_pancard:
                cliocl = "pancard";
                openGallery();
                break;
        }
    }

    public void changebagroundimagebycondition(View view) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
        } else {
            view.setBackground
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
        }
        myEdit.putString("whichone", "reporter");
        myEdit.commit();
    }

    public void changebagroundimagebycondition1() {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            img_presid.setBackgroundDrawable
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
            img_bank.setBackgroundDrawable
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
            img_pancard.setBackgroundDrawable
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
        } else {
            img_presid.setBackground
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
            img_bank.setBackground
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
            img_pancard.setBackground
                    (ContextCompat.getDrawable(UploadDocumentActivity.this, R.drawable.ic_tick_square));
        }

    }
}