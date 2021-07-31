package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.adapter.HomeAdapter;
import com.android.nmnewsagency.adapter.OwnVideoDetailAdapter;
import com.android.nmnewsagency.adapter.RejectedVideoAdapter;
import com.android.nmnewsagency.listner.RecyclerTouchListener;
import com.android.nmnewsagency.modelclass.AddNewsModel;
import com.android.nmnewsagency.modelclass.DeleteNewsByIdModel;
import com.android.nmnewsagency.modelclass.GetNewsByIdModel;
import com.android.nmnewsagency.modelclass.GetProfileDataModel;
import com.android.nmnewsagency.modelclass.GetUserSaveNewsModel;
import com.android.nmnewsagency.modelclass.ReportModelClass;
import com.android.nmnewsagency.modelclass.SaveModelClass;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.Utils;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnVideoDetailActivity extends AppCompatActivity implements Callback<Object> {
    RecyclerView recyclerView;
    OwnVideoDetailAdapter locationAdapter;
    List<GetNewsByIdModel.DataBean> arrayList;
    int newsId;
    String whichOne, whichType;
    Rest rest;
    public TextView title, txt_own_name, txt_own_subname, current, total, txt_owndescription, txt_ownviews, txt_ownlocation, txt_ownviewsqq,
            txt_owndatetime, txt_ownlike, txt_owncoments, txt_own_dddname;
    public LinearLayout lin_chat_qwnvideo, lin_create, lin_own_share, lin_own_comnt;
    RelativeLayout rel_ownvideo_profile;
    ImageView img_delete, image_own_profile, video_thuimbnail, img_playpause, img_ownlike;
    VideoView videoView;
    SeekBar seekbar;
    int current_pos, total_duration;
    boolean show = true, isLike = false, isSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // overridePendingTransition(R.anim.enter, R.anim.exit);
        setContentView(R.layout.item_ownvideodetail);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            newsId = (int) bundle.get("newsid");
            whichOne = (String) bundle.get("self");
            if (whichOne.equals("self")) {
                whichType = (String) bundle.get("type");
            }
        }
        rest = new Rest(this, this);
        inIt();
    }

    private void inIt() {

        lin_chat_qwnvideo = (LinearLayout) findViewById(R.id.lin_chat_qwnvideo);
        lin_create = (LinearLayout) findViewById(R.id.lin_create);
        lin_own_share = (LinearLayout) findViewById(R.id.lin_own_share);
        lin_own_comnt = (LinearLayout) findViewById(R.id.lin_own_comnt);
        rel_ownvideo_profile = (RelativeLayout) findViewById(R.id.rel_ownvideo_profile);
        img_delete = (ImageView) findViewById(R.id.img_delete);
        image_own_profile = (ImageView) findViewById(R.id.image_own_profile);
        img_ownlike = (ImageView) findViewById(R.id.img_ownlike);
        video_thuimbnail = (ImageView) findViewById(R.id.video_thuimbnail);
        img_playpause = (ImageView) findViewById(R.id.img_playpause);
        txt_own_name = (TextView) findViewById(R.id.txt_own_name);
        txt_own_dddname = (TextView) findViewById(R.id.txt_own_dddname);
        txt_owncoments = (TextView) findViewById(R.id.txt_owncoments);
        current = (TextView) findViewById(R.id.current);
        txt_ownviewsqq = (TextView) findViewById(R.id.txt_ownviewsqq);
        txt_owndatetime = (TextView) findViewById(R.id.txt_owndatetime);
        txt_ownlike = (TextView) findViewById(R.id.txt_ownlike);
        txt_ownlocation = (TextView) findViewById(R.id.txt_ownlocation);
        txt_owndescription = (TextView) findViewById(R.id.txt_owndescription);
        total = (TextView) findViewById(R.id.total);
        videoView = (VideoView) findViewById(R.id.vid_own);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        if (whichOne.equals("self")) {
        if (whichType.equals("hash")) {
            img_delete.setVisibility(View.GONE);
        }}
        lin_chat_qwnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnVideoDetailActivity.this, MessageDetailActivity.class);
                startActivity(intent);
            }
        });
       /* rel_ownvideo_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnVideoDetailActivity.this, UserOwnDegtailProfileActivity.class);
                startActivity(intent);
            }
        });*/
        lin_own_comnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OwnVideoDetailActivity.this, CommentsActivity.class);
                intent.putExtra("newsId", arrayList.get(0).getNewsId());
                startActivity(intent);
            }
        });
        lin_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBoxCreate();
            }
        });

        lin_own_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFeed();
            }
        });
        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBoxDelete();
            }
        });
        img_ownlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike) {
                    int no = Integer.parseInt(txt_ownlike.getText().toString());
                    if (no > 0) {
                        int set = no - 1;
                        txt_ownlike.setText(String.valueOf(set));
                        arrayList.get(0).setLikesCount(Integer.parseInt(txt_ownlike.getText().toString()));
                    }
                    img_ownlike.setImageResource(R.drawable.ic_like);
                    callServicegetDisLike(arrayList.get(0).getNewsId());
                } else {
                    int no = Integer.parseInt(txt_ownlike.getText().toString());

                    txt_ownlike.setText(String.valueOf(no + 1));
                    arrayList.get(0).setLikesCount(Integer.parseInt(txt_ownlike.getText().toString()));
                    img_ownlike.setImageResource(R.drawable.ic_dislike);
                    callServicegetLike(arrayList.get(0).getNewsId());
                }
            }
        });
        callserviceaGetNewsByID();
    }

    private void callserviceaGetNewsByID() {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.getNewsById(newsId);
    }

    private void callServicegetLike(int newid) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.likeUser1(newid);
    }

    private void callServicegetDisLike(int newid) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.DislikeUser(newid);
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof GetNewsByIdModel) {
                GetNewsByIdModel loginModel = (GetNewsByIdModel) obj;
                if (loginModel.isStatus()) {
                    arrayList = loginModel.getData();
                    setDataOnViws();
                }
            }
            if (obj instanceof ReportModelClass) {
                ReportModelClass loginModel = (ReportModelClass) obj;
                if (loginModel.isStatus()) {
                    Toast.makeText(OwnVideoDetailActivity.this, "Report successfully added", Toast.LENGTH_SHORT).show();
                }
            }
            if (obj instanceof DeleteNewsByIdModel) {
                DeleteNewsByIdModel loginModel = (DeleteNewsByIdModel) obj;
                if (loginModel.isStatus()) {
                    // Toast.makeText(OwnVideoDetailActivity.this, "News Deleted successfully ", Toast.LENGTH_SHORT).show();
                    OwnVideoDetailActivity.this.finish();
                }
            }
            if (obj instanceof SaveModelClass) {
                SaveModelClass loginModel = (SaveModelClass) obj;
                if (loginModel.isStatus()) {
                    OwnVideoDetailActivity.this.finish();
                    // notifyDataSetChanged();
                }
            }
        }
    }

    private void setDataOnViws() {
        if (arrayList.get(0).isIsLike()) {
            img_ownlike.setImageResource(R.drawable.ic_dislike);
            isLike = true;
        } else {
            img_ownlike.setImageResource(R.drawable.ic_like);
            isLike = false;
        }
        new Handler().post(new Runnable() {
            public void run() {
                Glide.with(OwnVideoDetailActivity.this)
                        .load(arrayList.get(0).getImageUrl())
                        .into(video_thuimbnail);
            }
        });
        new Handler().post(new Runnable() {
            public void run() {
                Glide.with(OwnVideoDetailActivity.this)
                        .load(arrayList.get(0).getAvatar())
                        .into(image_own_profile);
            }
        });
        txt_own_name.setText(arrayList.get(0).getUserName());
        txt_own_dddname.setText(arrayList.get(0).getDescription());
        txt_owndescription.setText(arrayList.get(0).getTitle());
        txt_ownlocation.setText(arrayList.get(0).getState_Name() + " " + arrayList.get(0).getCountry_Name());
        txt_ownviewsqq.setText(String.valueOf(arrayList.get(0).getViewCount()));
        txt_owndatetime.setText(Utils.parseDateToddMMyyyy(arrayList.get(0).getAddedOn()));
        txt_ownlike.setText(String.valueOf(arrayList.get(0).getLikesCount()));
        txt_owncoments.setText(String.valueOf(arrayList.get(0).getCommentCount()));

        Uri videouri = Uri.parse(arrayList.get(0).getVideoUrl());
        videoView.setVideoURI(videouri);
        videoView.requestFocus();
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoView.start();

            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        Log.d("", "onInfo, what = " + what);
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            // video started; hide the placeholder.
                            video_thuimbnail.setVisibility(View.GONE);
                            total_duration = videoView.getDuration();
                            seekbar.setMax((int) total_duration);
                            total.setText(Utils.timeConversion((long) current_pos));
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    img_playpause.setVisibility(View.VISIBLE);
                    show = false;
                } else {
                    img_playpause.setVisibility(View.GONE);
                    show = true;
                }
            }
        });
        img_playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    img_playpause.setImageResource(R.drawable.ic_play_circle_filled_black_30dp);
                } else {
                    videoView.start();
                    img_playpause.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
                }
            }
        });


        current_pos = videoView.getCurrentPosition();

        //display video duration
        current.setText(Utils.timeConversion((long) current_pos));
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = videoView.getCurrentPosition();
                    current.setText(Utils.timeConversion((long) current_pos));
                    //  Log.e("pos===================",String.valueOf((int)current_pos));
                    seekbar.setProgress(current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
        //seekbar change listner
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                current_pos = seekBar.getProgress();
                videoView.seekTo((int) current_pos);
            }
        });

    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    private void openDialogBoxCreate() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_createvideo);

        bottomSheetDialog.show();
    }

    private void openDialogBoxDelete() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_delete);
        TextView txt_delete_report = (TextView) bottomSheetDialog.findViewById(R.id.txt_delete_report);
        if (!whichOne.equals("")) {
            if (whichOne.equals("self")) {
                txt_delete_report.setText("Delete");
            } else {
                txt_delete_report.setText("Report");
            }
        }
        txt_delete_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (whichOne.equals("self")) {
                    if (whichType.equals("own")) {
                        callserviceDeleteNewsById();
                    } else if (whichType.equals("save")) {
                        callServicegetDeleteSave(arrayList.get(0).getNewsId());
                    }
                } else {
                    openDialogBoxReport();
                }
                bottomSheetDialog.cancel();
            }
        });
        TextView txt_cancel = (TextView) bottomSheetDialog.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    private void callserviceDeleteNewsById() {
        rest.ShowDialogue(OwnVideoDetailActivity.this.getResources().getString(R.string.pleaseWait));
        rest.getDeleteNewsById(arrayList.get(0).getNewsId());
    }

    private void callServicegetDeleteSave(int newid) {
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.deleteSaveUser(newid);
    }

    public void shareFeed() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my news app.");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void openDialogBoxReport() {
        Dialog dialog = new Dialog(OwnVideoDetailActivity.this);
        dialog.setContentView(R.layout.dialog_report);
        ImageView img_close_dialog = dialog.findViewById(R.id.img_close_dialog);
        TextView txt_report_subtitle = dialog.findViewById(R.id.txt_report_subtitle);
        RadioGroup radio_group = dialog.findViewById(R.id.radio_group);
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
                RadioButton selectedRadioButton = null;
                if (radio_group.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(OwnVideoDetailActivity.this, "Please select report reason", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    // get selected radio button from radioGroup
                    int selectedId = radio_group.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    selectedRadioButton = (RadioButton) dialog.findViewById(selectedId);
                    // Toast.makeText(context, selectedRadioButton.getText().toString() + " is selected", Toast.LENGTH_SHORT).show();
                }
                if (txt_report_subtitle.getText().toString().equals("")) {
                    txt_report_subtitle.setError("Please enter sub reason!");
                    return;
                }
                if (!txt_report_subtitle.getText().toString().equals("") &&
                        !selectedRadioButton.getText().toString().equals("")) {
                    //delete
                    callServicegetReport(txt_report_subtitle.getText().toString(),
                            selectedRadioButton.getText().toString(), arrayList.get(0).getNewsId());
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void callServicegetReport(String folow, String isfollow, int newid) {
        rest.ShowDialogue(OwnVideoDetailActivity.this.getResources().getString(R.string.pleaseWait));
        rest.reportUser(folow, isfollow, newid);
    }

}