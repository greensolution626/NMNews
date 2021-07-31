package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.GetProfileDataModel;
import com.android.nmnewsagency.modelclass.UpdateProfileModel;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.FileUtilsss;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements  Callback<Object> {
    ImageView iamge_back_editprofile, img_editprofile;
    TextView txt_editprofile_save;
    String image, frstanem, lastname, aboutme;
    TextInputEditText edit_aboutme, edit_surname, edit_frstname;
    private static final int IMAGE_PICKER_SELECT = 111;
    boolean changeProfile=false,setImageAvtar=false;
    String sendFile;
    Rest rest;
    Animation myAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_edit_profile);
        rest=new Rest(this,this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            image = bundle.getString("profileimage");
            frstanem = bundle.getString("firstname");
            lastname = bundle.getString("lastname");
            aboutme = bundle.getString("aboutme");
        }
        inIt();

    }

    private void inIt() {
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        edit_frstname = (TextInputEditText) findViewById(R.id.edit_frstname);
        edit_surname = (TextInputEditText) findViewById(R.id.edit_surname);
        edit_aboutme = (TextInputEditText) findViewById(R.id.edit_aboutme);
        iamge_back_editprofile = (ImageView) findViewById(R.id.iamge_back_editprofile);
        img_editprofile = (ImageView) findViewById(R.id.img_editprofile);
        txt_editprofile_save = (TextView) findViewById(R.id.txt_editprofile_save);
        txt_editprofile_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_editprofile_save.startAnimation(myAnim);
                String fname=edit_frstname.getText().toString();
                String lname=edit_surname.getText().toString();
                if(fname.equals("")){
                    edit_frstname.setError("Please enter First Name");
                    return;
                }
                if(lname.equals("")){
                    edit_surname.setError("Please enter Surname");
                    return;
                }
                if(changeProfile ){
                callServiceEditProfiel(fname,lname,edit_aboutme.getText().toString(),sendFile,image);
                }else{
                    callServiceEditProfiel(fname,lname,edit_aboutme.getText().toString(),"",image);
                }
            }
        });
        iamge_back_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.finish();
            }
        });
        img_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });
        setDataOnViews();
    }

    private void callServiceEditProfiel(String fname, String lname, String toString, String sendFile, String image) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.updateProfile(fname,lname,toString,sendFile,image);
    }

    private void getImageFromAlbum(){
        try{
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, IMAGE_PICKER_SELECT);
        }catch(Exception exp){
            Log.i("Error",exp.toString());
        }
    }
    public void setDataOnViews() {
        edit_frstname.setText(frstanem);
        edit_surname.setText(lastname);
        if (aboutme != null) {
            edit_aboutme.setText((String) aboutme);
        }
        if (image != null) {
            if (!image.equals("null")) {
                setImageAvtar=true;
                new Handler().post(new Runnable() {
                    public void run() {
                        Glide.with(EditProfileActivity.this)
                                .load(image)
                                .into(img_editprofile);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                sendFile = FileUtilsss.getRealPath(this, imageUri);
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Glide.with(EditProfileActivity.this)
                        .asBitmap()
                        .load(selectedImage)
                        .into(img_editprofile);
              //  img_editprofile.setImageBitmap(selectedImage);
                changeProfile=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(EditProfileActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(EditProfileActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof UpdateProfileModel) {
                UpdateProfileModel loginModel = (UpdateProfileModel) obj;
                if (loginModel.isStatus()) {
                    EditProfileActivity.this.finish();
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }
}