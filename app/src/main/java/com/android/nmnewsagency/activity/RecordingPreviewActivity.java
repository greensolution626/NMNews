package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.pref.Prefrence;

public class RecordingPreviewActivity extends AppCompatActivity {
VideoView vid_view;
ImageView iamge_back_video_preview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording_preview);
        vid_view=(VideoView)findViewById(R.id.vid_view);
        iamge_back_video_preview=(ImageView) findViewById(R.id.iamge_back_video_preview);
        vid_view.setVideoPath(Prefrence.getVideoFile());
        vid_view.start();
        iamge_back_video_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        vid_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
               vid_view.start();

            }
        });
    }
}