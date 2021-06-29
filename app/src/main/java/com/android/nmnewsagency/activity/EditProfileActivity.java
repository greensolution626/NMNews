package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nmnewsagency.R;

public class EditProfileActivity extends AppCompatActivity {
ImageView iamge_back_editprofile;
TextView txt_editprofile_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_edit_profile);
        iamge_back_editprofile=(ImageView)findViewById(R.id.iamge_back_editprofile);
        txt_editprofile_save=(TextView) findViewById(R.id.txt_editprofile_save);
        txt_editprofile_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.finish();
            }
        });
        iamge_back_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileActivity.this.finish();
            }
        });
    }
}