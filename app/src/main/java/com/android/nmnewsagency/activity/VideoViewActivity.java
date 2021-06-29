package com.android.nmnewsagency.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.projection.MediaProjectionManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nmnewsagency.R;
import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;


public class VideoViewActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private CameraKitView cameraKitView;
    ImageView img_caPTURE, img_cameraview, iamge_back_video, img_stop;
    TextView txt_timer, txt_timer_done;
    static int count = 1;
    Animation myAnim;
    CountDownTimer countDownTimer;
    static final int REQUEST_VIDEO_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        cameraKitView = (CameraKitView) findViewById(R.id.camera);
        img_caPTURE = (ImageView) findViewById(R.id.img_caPTURE);
        iamge_back_video = (ImageView) findViewById(R.id.iamge_back_video);
        img_stop = (ImageView) findViewById(R.id.img_stop);
        img_cameraview = (ImageView) findViewById(R.id.img_cameraview);
        txt_timer = (TextView) findViewById(R.id.txt_timer);
        txt_timer_done = (TextView) findViewById(R.id.txt_timer_done);
        myAnim = AnimationUtils.loadAnimation(VideoViewActivity.this, R.anim.bounce);

        //cameraKitView.setFlash(CameraKit.FLASH_OFF);

        img_cameraview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                img_cameraview.startAnimation(myAnim);
                if (count == 1) {
                    cameraKitView.setFacing(CameraKit.FACING_FRONT);
                    count--;
                } else {
                    cameraKitView.setFacing(CameraKit.FACING_BACK);
                    count++;
                }
            }
        });
        img_stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                txt_timer.setText("00:00");
                img_caPTURE.setVisibility(View.VISIBLE);
                img_stop.setVisibility(View.GONE);
                img_cameraview.setVisibility(View.VISIBLE);
            }
        });
        txt_timer_done.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VideoViewActivity.this,NewVideoActivity.class);
                startActivity(intent);
            }
        });
        img_caPTURE.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                img_caPTURE.setVisibility(View.GONE);
                // img_caPTURE.startAnimation(myAnim);
                img_stop.setVisibility(View.VISIBLE);
                showtimerVideoCapture();

                dispatchTakeVideoIntent();

                cameraKitView.captureVideo(new CameraKitView.VideoCallback() {
                    @Override
                    public void onVideo(CameraKitView cameraKitView, Object o) {
                        Log.e("CKDemo", String.valueOf(o));
                        /*File savedPhoto = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
                        try {
                            FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                            outputStream.write(photo);
                            outputStream.close();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                            Log.e("CKDemo", "Exception in photo callback");
                        }*/

                        cameraKitView.startVideo();
                        cameraKitView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //cameraKitView.stopVideo();
                                Toast.makeText(VideoViewActivity.this, "video stoped", Toast.LENGTH_SHORT).show();
                            }
                        }, 2500);
                    }
                });
            }
        });
        iamge_back_video.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        processPickImage();
    }


    private void processPickImage() {
        if (hasCameraPermission()) {
            onResume11();
        } else {
            requestCameraPermission();
        }
    }

    private void onResume11() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startRecordingScreen();
    }

    private void startRecordingScreen() {

    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(VideoViewActivity.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA},
                MY_CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraKitView.onResume();
    }

    @Override
    protected void onPause() {
        cameraKitView.onPause();
        super.onPause();
    }

    public void showtimerVideoCapture() {
        img_cameraview.setVisibility(View.GONE);
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                txt_timer.setText(f.format(min) + ":" + f.format(sec));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                txt_timer_done.setVisibility(View.VISIBLE);
                txt_timer.setVisibility(View.GONE);
                img_cameraview.setVisibility(View.VISIBLE);
                img_stop.setVisibility(View.GONE);
                img_caPTURE.setVisibility(View.VISIBLE);
                cameraKitView.stopVideo();
            }
        }.start();
    }
   /* private View.OnClickListener flashOnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cameraView.getFlash() != CameraKit.FLASH_ON) {
                cameraView.setFlash(CameraKit.FLASH_ON);
                updateInfoText();
            }
        }
    };

    private View.OnClickListener flashOffOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (cameraView.getFlash() != CameraKit.FLASH_OFF) {
                cameraView.setFlash(CameraKit.FLASH_OFF);
                updateInfoText();
            }
        }
    };*/
   private void dispatchTakeVideoIntent() {
       Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
       if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
           startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
       }
   }
}