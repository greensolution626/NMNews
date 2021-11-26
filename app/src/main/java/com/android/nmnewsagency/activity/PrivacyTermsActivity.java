package com.android.nmnewsagency.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.PagesModel;
import com.android.nmnewsagency.rest.Rest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyTermsActivity extends AppCompatActivity implements
        Callback<Object> {
    Rest rest;
    TextView txt_contents, txt_type;
    ImageView iamge_back_terms;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_privacy_terms);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = (int) bundle.get("pageId");
        }
        rest = new Rest(this, this);
        inIt();
    }

    private void inIt() {
        txt_type = findViewById(R.id.txt_type);
        txt_contents = findViewById(R.id.txt_contents);
        iamge_back_terms = findViewById(R.id.iamge_back_terms);
        iamge_back_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyTermsActivity.this.finish();
            }
        });
        callServicegetPAGES();
    }

    private void callServicegetPAGES() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getpages(id);
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof PagesModel) {
                PagesModel loginModel = (PagesModel) obj;
                if (loginModel.isStatus()) {
                    if (!loginModel.getData().getPageTitle().equals("") && !loginModel.getData().getPageDescription().equals("")) {
                        txt_type.setText(loginModel.getData().getPageTitle());
                        txt_contents.setText(loginModel.getData().getPageDescription());
                    }
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }
}