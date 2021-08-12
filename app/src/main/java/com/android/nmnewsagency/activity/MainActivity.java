package com.android.nmnewsagency.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.abedelazizshe.lightcompressorlibrary.config.Configuration;
import com.android.nmnewsagency.R;
import com.android.nmnewsagency.fragment.FragmentNotification;
import com.android.nmnewsagency.fragment.FragmentSearch;
import com.android.nmnewsagency.fragment.FragmentSearchTab;
import com.android.nmnewsagency.fragment.HomeFragment;
import com.android.nmnewsagency.fragment.ProfileFragment;
import com.android.nmnewsagency.modelclass.UploadNewsModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<Object> {
    private static final int IMAGE_PICKER_SELECT = 111;
    ImageView botom_home, botom_search, botom_add, botom_noti, botom_profile, iamge_back;
    TextView botom_home_text, botom_search_text, botom_noti_text, botom_profile_text;
    LinearLayout lin_home, lin_search, lin_add, lin_noti, lin_profile, lin_top, lin_onlysearch, lin_searchwithedit;
    EditText search_edit;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    BottomSheetDialog bottomSheetDialog;
    Animation myAnim;
    Rest rest;
    boolean statusCompress=false;
    String outputPaTH = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + System.nanoTime() + "compress.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        rest = new Rest(this, this);
        inItViews();
        setOnClickListner();
        myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        loadFragment(new HomeFragment());
        search_edit = (EditText) findViewById(R.id.search_edit);
        lin_top.setVisibility(View.GONE);
        search_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // search_edit.setEnabled(true);
                // loadFragment1(new FragmentSearchTab());
                /*Intent intent = new Intent(MainActivity.this, SearchTopBarTabingActivity.class);
                startActivity(intent);*/
            }
        });
        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    hideKeyboard(MainActivity.this);
                    lin_searchwithedit.setVisibility(View.GONE);
                    lin_onlysearch.setVisibility(View.VISIBLE);
                    search_edit.setText("");
                    return true;
                }
                return false;
            }


        });
    }

    private void setOnClickListner() {
        lin_home.setOnClickListener(this);
        lin_search.setOnClickListener(this);
        lin_add.setOnClickListener(this);
        lin_noti.setOnClickListener(this);
        lin_profile.setOnClickListener(this);
        lin_top.setOnClickListener(this);
        lin_onlysearch.setOnClickListener(this);
        lin_searchwithedit.setOnClickListener(this);
        iamge_back.setOnClickListener(this);

        botom_home.setImageResource(R.drawable.ic_home_active);
        botom_search.setImageResource(R.drawable.ic_contest);
        botom_noti.setImageResource(R.drawable.ic_wallet);
        botom_profile.setImageResource(R.drawable.ic_more);

        botom_home_text.setTextColor(getResources().getColor(R.color.locbutonbag));
        botom_search_text.setTextColor(getResources().getColor(R.color.loctitle));
        botom_noti_text.setTextColor(getResources().getColor(R.color.loctitle));
        botom_profile_text.setTextColor(getResources().getColor(R.color.loctitle));
    }

    private void inItViews() {
        iamge_back = (ImageView) findViewById(R.id.iamge_back);
        botom_home = (ImageView) findViewById(R.id.botom_home);
        botom_search = (ImageView) findViewById(R.id.botom_search);
        botom_add = (ImageView) findViewById(R.id.botom_add);
        botom_noti = (ImageView) findViewById(R.id.botom_noti);
        botom_profile = (ImageView) findViewById(R.id.botom_profile);
        botom_home_text = (TextView) findViewById(R.id.botom_home_text);
        botom_search_text = (TextView) findViewById(R.id.botom_search_text);
        botom_noti_text = (TextView) findViewById(R.id.botom_noti_text);
        botom_profile_text = (TextView) findViewById(R.id.botom_profile_text);
        lin_home = (LinearLayout) findViewById(R.id.lin_home);
        lin_search = (LinearLayout) findViewById(R.id.lin_search);
        lin_add = (LinearLayout) findViewById(R.id.lin_add);
        lin_noti = (LinearLayout) findViewById(R.id.lin_noti);
        lin_profile = (LinearLayout) findViewById(R.id.lin_profile);
        lin_top = (LinearLayout) findViewById(R.id.lin_top);
        lin_searchwithedit = (LinearLayout) findViewById(R.id.lin_searchwithedit);
        lin_onlysearch = (LinearLayout) findViewById(R.id.lin_onlysearch);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_home:
                lin_home.startAnimation(myAnim);
                lin_top.setVisibility(View.GONE);
                botom_home.setImageResource(R.drawable.ic_home_active);
                botom_search.setImageResource(R.drawable.ic_contest);
                botom_noti.setImageResource(R.drawable.ic_wallet);
                botom_profile.setImageResource(R.drawable.ic_more);

                botom_home_text.setTextColor(getResources().getColor(R.color.locbutonbag));
                botom_search_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_noti_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_profile_text.setTextColor(getResources().getColor(R.color.loctitle));

                loadFragment(new HomeFragment());
                break;
            case R.id.lin_search:
                lin_search.startAnimation(myAnim);
                lin_top.setVisibility(View.VISIBLE);
                botom_search.setImageResource(R.drawable.ic_contest__1_);
                botom_home.setImageResource(R.drawable.ic_home_active__1_);
                botom_noti.setImageResource(R.drawable.ic_wallet);
                botom_profile.setImageResource(R.drawable.ic_more);

                botom_home_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_search_text.setTextColor(getResources().getColor(R.color.locbutonbag));
                botom_noti_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_profile_text.setTextColor(getResources().getColor(R.color.loctitle));

                loadFragment(new FragmentSearch());
                break;
            case R.id.lin_add:
                lin_add.startAnimation(myAnim);
                lin_top.setVisibility(View.GONE);
                botom_search.setImageResource(R.drawable.ic_contest);
                botom_home.setImageResource(R.drawable.ic_home_active__1_);
                botom_noti.setImageResource(R.drawable.ic_wallet);
                botom_profile.setImageResource(R.drawable.ic_more);

                botom_home_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_search_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_noti_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_profile_text.setTextColor(getResources().getColor(R.color.loctitle));
                //  dispatchTakeVideoIntent();
                openDialogBox();
                /*Intent intentn = new Intent(MainActivity.this, NewVideoActivity.class);
                startActivity(intentn);*/
                break;
            case R.id.lin_noti:
                lin_noti.startAnimation(myAnim);
                lin_top.setVisibility(View.GONE);
                botom_noti.setImageResource(R.drawable.ic_wallet__1_);
                botom_home.setImageResource(R.drawable.ic_home_active__1_);
                botom_search.setImageResource(R.drawable.ic_contest);
                botom_profile.setImageResource(R.drawable.ic_more);

                botom_home_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_search_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_noti_text.setTextColor(getResources().getColor(R.color.locbutonbag));
                botom_profile_text.setTextColor(getResources().getColor(R.color.loctitle));
                loadFragment(new FragmentNotification());
                break;
            case R.id.lin_profile:
                lin_profile.startAnimation(myAnim);
                lin_top.setVisibility(View.GONE);
                botom_profile.setImageResource(R.drawable.ic_more__2_);
                botom_home.setImageResource(R.drawable.ic_home_active__1_);
                botom_search.setImageResource(R.drawable.ic_contest);
                botom_noti.setImageResource(R.drawable.ic_wallet);

                botom_home_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_search_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_noti_text.setTextColor(getResources().getColor(R.color.loctitle));
                botom_profile_text.setTextColor(getResources().getColor(R.color.locbutonbag));
                loadFragment(new ProfileFragment());
                break;
            case R.id.lin_onlysearch:
                // lin_onlysearch.startAnimation(myAnim);
               /* lin_searchwithedit.setVisibility(View.VISIBLE);
                search_edit.setFocusable(true);
                lin_onlysearch.setVisibility(View.GONE);*/
                // Intent intent = new Intent(MainActivity.this, SearchTopBarTabingActivity.class);
                //startActivity(intent);
                break;
            case R.id.iamge_back:
                iamge_back.startAnimation(myAnim);
                lin_searchwithedit.setVisibility(View.GONE);
                lin_onlysearch.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame_loc, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    private void loadFragment1(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frame_loc, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideKeyboard(MainActivity.this);
        lin_searchwithedit.setVisibility(View.GONE);
        lin_onlysearch.setVisibility(View.VISIBLE);
        search_edit.setText("");
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            //videoView.setVideoURI(videoUri);
            Intent intentn = new Intent(MainActivity.this, NewVideoActivity.class);
            intentn.putExtra("video", String.valueOf(videoUri));
            startActivity(intentn);
        }
        if (requestCode == IMAGE_PICKER_SELECT && resultCode == RESULT_OK) {
            Uri selectedMediaUri = intent.getData();
           /* if (selectedMediaUri.toString().contains("image")) {
                //handle image
            } else if (selectedMediaUri.toString().contains("video")) {*/
                Uri selectedImageUri = intent.getData();

                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();
                Log.e("filemanagerstring==", filemanagerstring);
                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);
                Log.e("selectedImagePath==", selectedImagePath);
                if (selectedImagePath != null) {
                    File file = new File(selectedImagePath);
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
                    retriever.setDataSource(this, Uri.fromFile(file));
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long timeInMillisec = Long.parseLong(time);
                    retriever.release();
                    Log.e("timeInMillisec==", String.valueOf(timeInMillisec));
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(timeInMillisec);
                    Log.e("seconds==", String.valueOf(seconds));

                if (seconds<=300) {
                    boolean compree= GetVieeoPath(MainActivity.this,String.valueOf(selectedImagePath),outputPaTH);
                    Log.e("compressPathMain",String.valueOf(compree));

                }else{
                    showalertDialog();
                }}
           // }
        }
    }

    private void callServiceUploadNews(String selectedImagePath) {
        File file = new File(selectedImagePath);
        Prefrence.setVideoFIle(String.valueOf(file));
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.uploadNews(selectedImagePath);

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private void openDialogBox() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_createvideo);
        LinearLayout lin_addVideo = bottomSheetDialog.findViewById(R.id.lin_addVideo);
        lin_addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //throw new RuntimeException("Test Crash"); // Force a crash
                Intent intent = new Intent(MainActivity.this, VideoViewActivity22.class);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });
        LinearLayout lin_pickVideo = bottomSheetDialog.findViewById(R.id.lin_pickVideo);
        lin_pickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("video/*");
                startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }


    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {

            Object obj = response.body();
            if (obj instanceof UploadNewsModel) {

                UploadNewsModel loginModel = (UploadNewsModel) obj;
                if (loginModel.isStatus()) {
                    nextActivityGoing(loginModel.getData());
                }
            }

        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    private void nextActivityGoing(UploadNewsModel.DataBean data) {
        Intent intent = new Intent(MainActivity.this, NewVideoActivity.class);
        intent.putExtra("video", data);
        startActivity(intent);
    }
    public void showalertDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Select Video")
                .setMessage("Please select video length less  then 30 sec")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickIntent.setType("video/*");
                        startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public  boolean GetVieeoPath(Context context, String uri, String outputPaTH) {
        new Thread() {
            ProgressDialog pBar;
            @Override
            public void run() {
                super.run();
                VideoCompressor.start(
                        null, // => This is required if srcUri is provided. If not, pass null.
                        null, // => Source can be provided as content uri, it requires context.
                        uri, // => This could be null if srcUri and context are provided.
                        outputPaTH,
                        new CompressionListener() {
                            @Override
                            public void onStart() {
                                // Compression start
                                pBar = new ProgressDialog(context);
                                pBar.setMessage("Please wait...It is downloading");
                                pBar.setIndeterminate(false);
                                pBar.setCancelable(false);
                                pBar.show();
                                Log.e("VideoCompreser", "onStart");
                            }

                            @Override
                            public void onSuccess() {
                                // On Compression success
                                Log.e("VideoCompreser", "onSuccess");
                                int lent = outputPaTH.length();
                                Log.e("VideoCompreser", String.valueOf(lent));
                                pBar.dismiss();
                                statusCompress=true;
                                callmethod();
                            }

                            @Override
                            public void onFailure(String failureMessage) {
                                // On Failure
                                Log.e("VideoCompreser", failureMessage);
                            }

                            @Override
                            public void onProgress(float v) {
                                // Update UI with progress value
                                Log.e("VideoCompreser", "onProgress");
                            }

                            @Override
                            public void onCancelled() {
                                // On Cancelled
                                Log.e("VideoCompreser", "onCancelled");
                            }
                        }, new Configuration(
                                VideoQuality.MEDIUM,
                                false,
                                false,
                                null /*videoHeight: double, or null*/,
                                null /*videoWidth: double, or null*/,
                                null /*videoBitrate: int, or null*/
                        )
                );

            }
        }.run();
        return statusCompress;
    }
    private  void callmethod() {
        callServiceUploadNews(outputPaTH);
    }
    //6.21---2.17
    //7.22---1.06
}