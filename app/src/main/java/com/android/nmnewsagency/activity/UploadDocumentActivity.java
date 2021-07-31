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
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.AddUserDocumentModel;
import com.android.nmnewsagency.modelclass.GetDocumentModel;
import com.android.nmnewsagency.modelclass.UpdateProfileModel;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.FileUtilsss;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadDocumentActivity extends AppCompatActivity implements View.OnClickListener,
        Callback<Object> {
    ImageView iamge_back_uploaddoc, img_bank, img_pancard, img_presid;
    static int count = 0;
    int REQUEST_GET_SINGLE_FILE = 99;
    LinearLayout lin_bank, lin_pancard, lin_presid;
    String cliocl, sendFile, type;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    Rest rest;
    List<GetDocumentModel.DataBean> getdocModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_upload_document);
        rest=new Rest(this,this);
        INIT();
        callservicegetDocument();
    }

    private void INIT() {
        iamge_back_uploaddoc = (ImageView) findViewById(R.id.iamge_back_uploaddoc);
        img_presid = (ImageView) findViewById(R.id.img_presid);
        img_pancard = (ImageView) findViewById(R.id.img_pancard);
        img_bank = (ImageView) findViewById(R.id.img_bank);
        lin_presid = (LinearLayout) findViewById(R.id.lin_presid);
        lin_bank = (LinearLayout) findViewById(R.id.lin_bank);
        lin_pancard = (LinearLayout) findViewById(R.id.lin_pancard);

        iamge_back_uploaddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadDocumentActivity.this.finish();
            }
        });
        lin_bank.setOnClickListener(this);
        lin_pancard.setOnClickListener(this);
        lin_presid.setOnClickListener(this);
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
                        sendFile = FileUtilsss.getRealPath(this, selectedImageUri);

                        callserviceUploadDoc(sendFile);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    private void callserviceUploadDoc(String sendFile) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.addUserDocument(cliocl,sendFile);
    }
    private void callservicegetDocument() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getDocument();
    }

    private void changeImageBagroundfile() {
        if (cliocl.equals("PRESSIDCARD")) {
            changebagroundimagebycondition(img_presid);
        } else if (cliocl.equals("BANKPASSBOOK")) {
            changebagroundimagebycondition(img_bank);
        } else if (cliocl.equals("PANCARD")) {
            changebagroundimagebycondition(img_pancard);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_presid:
                cliocl = "PRESSIDCARD";
                openGallery();
                break;
            case R.id.lin_bank:
                cliocl = "BANKPASSBOOK";
                openGallery();
                break;
            case R.id.lin_pancard:
                cliocl = "PANCARD";
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

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof GetDocumentModel) {
                GetDocumentModel loginModel = (GetDocumentModel) obj;
                if (loginModel.isStatus()) {
                    getdocModel=loginModel.getData();
                    setDocumentDataBaseonResponce();
                }
            }
            if (obj instanceof AddUserDocumentModel) {
                AddUserDocumentModel loginModel = (AddUserDocumentModel) obj;
                if (loginModel.isStatus()) {
                    Toast.makeText(this, "Document Upload Successfully", Toast.LENGTH_SHORT).show();
                    changeImageBagroundfile();
                }
            }

        }
    }

    private void setDocumentDataBaseonResponce() {
        int i1;
        if(getdocModel.size()>0){
        for(i1=0;i1<getdocModel.size();i1++){
            if(getdocModel.get(i1).getDocumentType().equals("PRESSIDCARD")){
                changebagroundimagebycondition(img_presid);
            }
            else if(getdocModel.get(i1).getDocumentType().equals("BANKPASSBOOK")){
                changebagroundimagebycondition(img_bank);
            }
            else if(getdocModel.get(i1).getDocumentType().equals("PANCARD")){
                changebagroundimagebycondition(img_pancard);
            }

        }}



    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }
}