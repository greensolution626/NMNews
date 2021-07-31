package com.android.nmnewsagency.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.AutoCompleteSearchBarAdapter;
import com.android.nmnewsagency.adapter.AutoCompleteSearchBarAdapter1;
import com.android.nmnewsagency.adapter.SearchTopBarHashtagAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.modelclass.AddNewsModel;
import com.android.nmnewsagency.modelclass.LoginModel;
import com.android.nmnewsagency.modelclass.NewVideoHashtagModelClass;
import com.android.nmnewsagency.modelclass.NewVideoMentionModelClass;
import com.android.nmnewsagency.modelclass.RequestModel.RequestAddNews;
import com.android.nmnewsagency.modelclass.UploadNewsModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.Utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewVideoActivity extends AppCompatActivity implements TextWatcher, Callback<Object> {
    LinearLayout lin_brekingnews, lin_showlayout;
    ImageView iamge_back_newvideo, img_newvideo_url;
    int count = 0;
    TextView txt_newvideo_submit, txt_remainin, txt_userid;
    MultiAutoCompleteTextView autoCompleteTextView;
    AutoCompleteSearchBarAdapter autoCompleteSearchBarAdapter;
    AutoCompleteSearchBarAdapter1 autoCompleteSearchBarAdapter1;
    SearchTopBarHashtagAdapter topBarHashtagAdapter;
    List<NewVideoHashtagModelClass.DataBean.PagedRecordBean> arrayHashTag;
    List<NewVideoMentionModelClass.DataBean.PagedRecordBean> arrayMention;
    ArrayList<Integer> imgList;
    RecyclerView recy_newVideo;
    RelativeLayout lin_attherate, lin_hashtag, rel_addvoice;
    Rest rest;
    private UploadNewsModel.DataBean dataBean = null;
    private RequestAddNews.NewsObjBean newsObjBean = new RequestAddNews.NewsObjBean();
    private boolean isBreakingNews = false;
    RadioButton radio_frst, radio_second, radio_thrd;
    FrameLayout frame_video_open;
    ;
    static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_new_video);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dataBean = (UploadNewsModel.DataBean) bundle.get("video");
            Log.e("datavideo==", String.valueOf(dataBean.getVideoUrl()));

        }
        rest = new Rest(NewVideoActivity.this, this);
        iniiT();
    }

    private void iniiT() {
        frame_video_open = (FrameLayout) findViewById(R.id.frame_video_open);
        radio_frst = (RadioButton) findViewById(R.id.radio_frst);
        radio_second = (RadioButton) findViewById(R.id.radio_second);
        radio_thrd = (RadioButton) findViewById(R.id.radio_thrd);
        radio_frst.setChecked(true);
        lin_brekingnews = (LinearLayout) findViewById(R.id.lin_brekingnews);
        lin_showlayout = (LinearLayout) findViewById(R.id.lin_showlayout);
        lin_hashtag = (RelativeLayout) findViewById(R.id.lin_hashtag);
        rel_addvoice = (RelativeLayout) findViewById(R.id.rel_addvoice);
        lin_attherate = (RelativeLayout) findViewById(R.id.lin_attherate);
        iamge_back_newvideo = (ImageView) findViewById(R.id.iamge_back_newvideo);
        img_newvideo_url = (ImageView) findViewById(R.id.img_newvideo_url);
        txt_newvideo_submit = (TextView) findViewById(R.id.txt_newvideo_submit);
        txt_remainin = (TextView) findViewById(R.id.txt_remainin);
        txt_userid = (TextView) findViewById(R.id.txt_userid);
        autoCompleteTextView = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(3);
        autoCompleteTextView.addTextChangedListener(this);
        recy_newVideo = (RecyclerView) findViewById(R.id.recy_newVideo);
        iamge_back_newvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewVideoActivity.this.finish();
            }
        });
        txt_newvideo_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServiceddNews();
            }

        });
        lin_brekingnews.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    lin_brekingnews.setBackground(NewVideoActivity.this.getResources().getDrawable(R.drawable.newvideo1));
                    newsObjBean.setIsBreakingNews(true);
                    count = 1;
                } else {
                    lin_brekingnews.setBackground(NewVideoActivity.this.getResources().getDrawable(R.drawable.newvideo));
                    newsObjBean.setIsBreakingNews(false);
                    count = 0;
                }
            }
        });
        lin_attherate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.setText(autoCompleteTextView.getText().toString() + "@");
                autoCompleteTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        autoCompleteTextView.setSelection(autoCompleteTextView.getText().toString().length());
                    }
                });
            }
        });
        lin_hashtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.setText(autoCompleteTextView.getText().toString() + "#");
                autoCompleteTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        autoCompleteTextView.setSelection(autoCompleteTextView.getText().toString().length());
                    }
                });
            }
        });

        txt_userid.setText(Utils.getUserIdfromdata());
        new ImageLoadTask(dataBean.getImageFullPath(),
                img_newvideo_url).execute();
        frame_video_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewVideoActivity.this, RecordingPreviewActivity.class);
                startActivity(intent);
            }
        });
        rel_addvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewVideoActivity.this, AudioRecordActivity.class);
                 intent.putExtra("video",dataBean);
                startActivity(intent);
            }
        });
    }

    private void nextActivityCall() {
        Intent i = new Intent(NewVideoActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }


    private void callServiceddNews() {
        int newsType = 0;
        newsObjBean.setNewsType(1);
        if (radio_frst.isChecked()) {
            newsObjBean.setNewsType(1);
            newsType = 1;
        } else if (radio_second.isChecked()) {
            newsObjBean.setNewsType(2);
            newsType = 2;
        } else if (radio_thrd.isChecked()) {
            newsObjBean.setNewsType(3);
            newsType = 3;
        }
        String val = "" + autoCompleteTextView.getText();
        if (!val.equals("")) {
            newsObjBean.setTitle(String.valueOf(autoCompleteTextView.getText()));
            newsObjBean.setUserId(Prefrence.getUserId());

            newsObjBean.setZipCode("");
            if (!Prefrence.gettahsilIdd().equals("")) {
                newsObjBean.setTahsilId(Integer.parseInt(Prefrence.gettahsilIdd()));
                newsObjBean.setStateId(Integer.parseInt(Prefrence.getStateIdd()));
                newsObjBean.setCountryId(Integer.parseInt(Prefrence.getCountryIdd()));
                newsObjBean.setCityId(Integer.parseInt(Prefrence.getCityIdd()));
            } else {
                newsObjBean.setTahsilId(0);
                newsObjBean.setStateId(0);
                newsObjBean.setCountryId(0);
                newsObjBean.setCityId(0);
            }
            if (Prefrence.gettahsil().isEmpty()) {
                newsObjBean.setTahsil_Name(Prefrence.getCityName());
            } else {
                newsObjBean.setTahsil_Name(Prefrence.gettahsil());
            }
            newsObjBean.setSuggestion("News Updated");

            newsObjBean.setState_Name(Prefrence.getStateName());

            newsObjBean.setLong(0.0);
            newsObjBean.setLat(0.0);

            newsObjBean.setId(0);
            newsObjBean.setDescription("News");

            newsObjBean.setCountry_Name(Prefrence.getCountryName());

            newsObjBean.setCity_Name(Prefrence.getCityName());
            newsObjBean.setAddressLine_1(Prefrence.getCityName());
            newsObjBean.setAddressLin_2("");
            rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
            rest.addNews(dataBean, newsObjBean);
        } else if (val.equals("")) {
            autoCompleteTextView.setError("Please enter a proper heading");
        } else if (newsType == 0) {
            Toast.makeText(this, "Please enter a post type", Toast.LENGTH_SHORT).show();
        }


    }

    public void callServiceMention(String query) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.menTion(query);
    }

    public void callServiceHashtag(String query) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.HashTag(query);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!autoCompleteTextView.getText().toString().equals("")) {
            char first = autoCompleteTextView.getText().toString().charAt(0);
            int adtheate = Character.compare(first, '@');
            int hashtag = Character.compare(first, '#');
            if (adtheate == 0) {
                callServiceMention(autoCompleteTextView.getText().toString());

            } else if (hashtag == 0) {
                callServiceHashtag(autoCompleteTextView.getText().toString());

            } else if (reversearray(autoCompleteTextView.getText().toString()).equals("#")) {
                String substr = autoCompleteTextView.getText().toString().
                        substring(index, autoCompleteTextView.getText().toString().length());
                if (!substr.equals("")) {
                    callServiceHashtag(substr);
                } else {
                    return;
                }
            } else if (reversearray(autoCompleteTextView.getText().toString()).equals("@")) {
                String substr = autoCompleteTextView.getText().toString().
                        substring(index, autoCompleteTextView.getText().toString().length());
                if (!substr.equals("")) {
                    callServiceMention(substr);
                } else {
                    return;
                }
            }
        }
    }

    public static String reverse(String input) {
        StringBuilder input1 = new StringBuilder();
        // append a string into StringBuilder input1
        input1.append(input);
        // reverse StringBuilder input1
        input1.reverse();
        return new String(input1);
    }

    public static String reversearray(String input) {
        // convert String to character array
        // by using toCharArray
        String send = "";
        char[] try1 = input.toCharArray();

        for (index = try1.length - 1; index >= 0; index--) {
            System.out.print(try1[index]);
            if (try1[index] == '@') {
                send = "@";
                break;
            } else if (try1[index] == '#') {
                send = "#";
                break;
            }
        }

        return send;
    }

    public void afterServiceCallHashTag() {
        recy_newVideo.setVisibility(View.VISIBLE);
        lin_showlayout.setVisibility(View.GONE);
    }

    @Override
    public void afterTextChanged(Editable s) {
        //Toast.makeText(NewVideoActivity.this, "afterTextChanged", Toast.LENGTH_SHORT).show();
        if (autoCompleteTextView.getText().toString().equals("")) {
            recy_newVideo.setVisibility(View.GONE);
            lin_showlayout.setVisibility(View.VISIBLE);
        }
        int charLength = autoCompleteTextView.getText().toString().length();
        if (charLength <= 120) {
            txt_remainin.setText("Remaining : " + String.valueOf(120 - charLength));
        } else if (charLength > 120) {
            //autoCompleteTextView.setEnabled(false);
            // autoCompleteTextView.setText(autoCompleteTextView.getText().toString()+"");

        }
    }


    private void inItItemRecycle(String type) {
        boolean which;
        afterServiceCallHashTag();
        if (type.equals("hashtag")) {
            which = true;
            autoCompleteSearchBarAdapter = new AutoCompleteSearchBarAdapter(NewVideoActivity.this, arrayHashTag);
            recy_newVideo.setAdapter(autoCompleteSearchBarAdapter);
        } else {
            which = false;
            autoCompleteSearchBarAdapter1 = new AutoCompleteSearchBarAdapter1(NewVideoActivity.this, arrayMention);
            recy_newVideo.setAdapter(autoCompleteSearchBarAdapter1);
        }

        recy_newVideo.setLayoutManager(new LinearLayoutManager(NewVideoActivity.this));
        recy_newVideo.setItemAnimator(new DefaultItemAnimator());
        recy_newVideo.addOnItemTouchListener(new RecyclerTouchListener(NewVideoActivity.this, recy_newVideo, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (which) {
                    String newStr = autoCompleteTextView.getText().toString().substring(0, index) + "" + arrayHashTag.get(position).getTitle();
                    autoCompleteTextView.setText(newStr);
                } else {
                    String newStr = autoCompleteTextView.getText().toString().substring(0, index) + "" + arrayMention.get(position).getTitle();
                    autoCompleteTextView.setText(newStr);
                }
                autoCompleteTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        autoCompleteTextView.setSelection(autoCompleteTextView.getText().toString().length());
                    }
                });
                recy_newVideo.setVisibility(View.GONE);
                lin_showlayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {

            Object obj = response.body();
            if (obj instanceof AddNewsModel) {

                AddNewsModel loginModel = (AddNewsModel) obj;
                if (loginModel.isStatus()) {
                    finish();
                    Prefrence.setisUpload(true);
                }
            }
            if (obj instanceof NewVideoHashtagModelClass) {

                NewVideoHashtagModelClass loginModel = (NewVideoHashtagModelClass) obj;
                if (loginModel.isStatus()) {
                    arrayHashTag = (ArrayList<NewVideoHashtagModelClass.DataBean.PagedRecordBean>) loginModel.getData().getPagedRecord();
                    if (arrayHashTag != null && arrayHashTag.size() > 0) {
                        inItItemRecycle("hashtag");
                    } else {
                        recy_newVideo.setVisibility(View.GONE);
                        lin_showlayout.setVisibility(View.VISIBLE);
                    }
                }
            }
            if (obj instanceof NewVideoMentionModelClass) {

                NewVideoMentionModelClass loginModel = (NewVideoMentionModelClass) obj;
                if (loginModel.isStatus()) {
                    arrayMention = (ArrayList<NewVideoMentionModelClass.DataBean.PagedRecordBean>) loginModel.getData().getPagedRecord();
                    if (arrayMention != null && arrayMention.size() > 0) {
                        inItItemRecycle("mention");
                    } else {
                        recy_newVideo.setVisibility(View.GONE);
                        lin_showlayout.setVisibility(View.VISIBLE);
                    }

                }
            }

        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    //setimage on imageview
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}