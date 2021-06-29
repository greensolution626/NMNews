package com.android.nmnewsagency.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.nmnewsagency.R;
import com.camerakit.CameraKit;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoViewActivity22 extends AppCompatActivity {
    private static final int MEDIA_TYPE_IMAGE = 222;
    private static Camera c=null;
    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    private static final int MEDIA_TYPE_VIDEO = 111;
    private static final int MY_CAMERA_REQUEST_CODE = 1111;
    private static final int MY_CAMERA_REQUEST_CODE1 = 2222;
    static File mediaFile;

    ImageView captureButton, img_cameraview, iamge_back_video, img_stop;
    TextView txt_timer, txt_timer_done;
    static int count = 1;
    Animation myAnim;
    CountDownTimer countDownTimer;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view22);
        processPickImage();
        iniiT();
        img_cameraview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_cameraview.startAnimation(myAnim);
                if (count == 1) {
                  // mCamera.setfac
                    count--;
                } else {
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    count++;
                }
            }
        });
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        captureButton.setVisibility(View.GONE);
                        // img_caPTURE.startAnimation(myAnim);
                        img_stop.setVisibility(View.VISIBLE);
                        showtimerVideoCapture();

                            // initialize video camera
                            if (prepareVideoRecorder()) {
                                // Camera is available and unlocked, MediaRecorder is prepared,
                                // now you can start recording
                                mediaRecorder.start();

                                // inform the user that recording has started
                               // captureButton.setText("Stop");
                                isRecording = true;
                            } else {
                                // prepare didn't work, release the camera
                                releaseMediaRecorder();
                                // inform user
                            }
                        }
                }
        );
        img_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                txt_timer.setText("00:00");
                captureButton.setVisibility(View.VISIBLE);
                img_stop.setVisibility(View.GONE);
                img_cameraview.setVisibility(View.VISIBLE);
            }
        });
        txt_timer_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VideoViewActivity22.this,NewVideoActivity.class);
                startActivity(intent);
            }
        });
        iamge_back_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void iniiT() {
        captureButton = (ImageView) findViewById(R.id.button_capture);
        iamge_back_video = (ImageView) findViewById(R.id.iamge_back_video);
        img_stop = (ImageView) findViewById(R.id.img_stop);
        img_cameraview = (ImageView) findViewById(R.id.img_cameraview);
        txt_timer = (TextView) findViewById(R.id.txt_timer);
        txt_timer_done = (TextView) findViewById(R.id.txt_timer_done);
        myAnim = AnimationUtils.loadAnimation(VideoViewActivity22.this, R.anim.bounce);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    private boolean prepareVideoRecorder() {

        mCamera = getCameraInstance();
        mediaRecorder = new MediaRecorder();
        mCamera.setDisplayOrientation(90);
        mediaRecorder.setOrientationHint(90);
        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_1080P));
        //duration
      //  mediaRecorder.setMaxDuration(50000);
        // Step 4: Set output file
        mediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());

        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());

        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d("", "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d("", "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {

        try {
            c = Camera.open();
            // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(String.valueOf(Environment.getExternalStorageDirectory()));

        // File mediaStorageDir = new File(
        //      Environment.getExternalStorageDirectory().getAbsolutePath() + "/myvideo.mp4");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd").format(new Date());

        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "NMNews" + File.separator +
                    "VID_" + "NMNews" + timeStamp + ".mp4");
            Log.e("mediafile==  ", String.valueOf(mediaFile));
        } else {
            return null;
        }

        return mediaFile;
    }

    private void processPickImage() {
        if (hasCameraPermission()) {
            onResume11();
        } else {
            requestCameraPermission();
        }
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(VideoViewActivity22.this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(VideoViewActivity22.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
        ContextCompat.checkSelfPermission(VideoViewActivity22.this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO},
                MY_CAMERA_REQUEST_CODE);
    }

    private void onResume11() {

        if (checkPermission()) {
            startRecordingScreen();

        } else {
            requestCameraPermission();
        }

    }
    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }
    private void startRecordingScreen() {
        mCamera = getCameraInstance();
        mCamera.setDisplayOrientation(90);
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                  //  Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    startRecordingScreen();
                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestCameraPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(VideoViewActivity22.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
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
                captureButton.setVisibility(View.VISIBLE);

                // stop recording and release camera
                mediaRecorder.stop();  // stop the recording
                releaseMediaRecorder(); // release the MediaRecorder object
                mCamera.lock();         // take camera access back from MediaRecorder

                // inform the user that recording has stopped
                isRecording = false;
                if (mediaFile != null) {
                    Toast.makeText(VideoViewActivity22.this, String.valueOf(mediaFile), Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }
}