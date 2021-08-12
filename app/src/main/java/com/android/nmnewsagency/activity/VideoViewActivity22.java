package com.android.nmnewsagency.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.abedelazizshe.lightcompressorlibrary.config.Configuration;
import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.LoginModel;
import com.android.nmnewsagency.modelclass.UploadNewsModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.Utils;
import com.camerakit.CameraKit;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoViewActivity22 extends AppCompatActivity implements Callback<Object> {
    private static final int MEDIA_TYPE_IMAGE = 222;
    private static Camera c = null;
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
    Rest rest;
    FrameLayout preview;
    LinearLayout lin_tapheare;
    String outputPaTH = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + System.nanoTime() + "compress.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view22);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        rest = new Rest(this, this);

        processPickImage();
        iniiT();
        img_cameraview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_cameraview.startAnimation(myAnim);

                if (count == 1) {
                    releaseCamera();
                    mCamera =  Camera.open(1);
                    mCamera.setDisplayOrientation(90);
                    // Create our Preview view and set it as the content of our activity.
                    mPreview = new CameraPreview(VideoViewActivity22.this, mCamera);
                    preview.addView(mPreview);
                    count--;
                } else {
                    releaseCamera();
                    mCamera =  Camera.open(0);
                    mCamera.setDisplayOrientation(90);
                    // Create our Preview view and set it as the content of our activity.
                    mPreview = new CameraPreview(VideoViewActivity22.this, mCamera);
                    preview.addView(mPreview);
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
                        lin_tapheare.setVisibility(View.GONE);
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
                lin_tapheare.setVisibility(View.VISIBLE);

                //txt_timer_done.setVisibility(View.VISIBLE);
                txt_timer.setVisibility(View.GONE);
                img_cameraview.setVisibility(View.VISIBLE);
                img_stop.setVisibility(View.GONE);
                captureButton.setVisibility(View.VISIBLE);

                // stop recording and release camera
                mediaRecorder.stop();  // stop the recording
                releaseMediaRecorder(); // release the MediaRecorder object
                mCamera.lock();

                /*Bitmap bitmap=null;
                // take camera access back from MediaRecorder
                try {
                    bitmap=retriveVideoFrameFromVideo(String.valueOf(mediaFile));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }*/

                // inform the user that recording has stopped
                isRecording = false;
                if (mediaFile != null) {
                   // Toast.makeText(VideoViewActivity22.this, String.valueOf(mediaFile), Toast.LENGTH_SHORT).show();
                    String compree=GetVieeoPath(VideoViewActivity22.this,String.valueOf(mediaFile),outputPaTH);
                    Log.e("compressPath",compree);

                }
            }
        });
        txt_timer_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // nextActivityGoing();

            }
        });
        iamge_back_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finish();
            }
        });
    }

    private void nextActivityGoing(UploadNewsModel.DataBean data) {
        Intent intent = new Intent(VideoViewActivity22.this, NewVideoActivity.class);
        intent.putExtra("video",data);
        startActivity(intent);
        finish();
    }

    private void iniiT() {
        lin_tapheare = (LinearLayout) findViewById(R.id.lin_tapheare);
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

       // mCamera = getCameraInstance();

       // mCamera.setDisplayOrientation(90);
        mediaRecorder.setOrientationHint(90);
        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);

        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
          // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_480P));
        //size 20 mb
       // mediaRecorder.setMaxFileSize(20000000);
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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
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
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
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
        if(mCamera!=null) {
            mCamera.setDisplayOrientation(90);
        }
        mediaRecorder = new MediaRecorder();
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
         preview = (FrameLayout) findViewById(R.id.camera_preview);
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
        countDownTimer = new CountDownTimer(300000, 1000) {
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
                // txt_timer_done.setVisibility(View.VISIBLE);
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
                   Prefrence.setVideoFIle(mediaFile.getAbsolutePath());
                    String compree=GetVieeoPath(VideoViewActivity22.this,String.valueOf(mediaFile),outputPaTH);
                    Log.e("compressPath",compree);
                  // callServiceUploadNews(compree);
                }
            }
        }.start();
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {

            Object obj = response.body();
            if (obj instanceof UploadNewsModel) {

                UploadNewsModel loginModel = (UploadNewsModel) obj;
                //editdelete
               // nextActivityGoing(loginModel.getData());
                if (loginModel.isStatus()) {
                    nextActivityGoing(loginModel.getData());
                }
            }

        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }

    private void callServiceUploadNews(String path) {
        Prefrence.setVideoFIle(mediaFile.getAbsolutePath());
        rest.ShowDialogue(getResources().getString(R.string.pleaseWait));
        rest.uploadNews(path);

    }
    private int findFrontFacingCamera() {

        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
               // cameraId = i;
               // cameraFront = true;
                break;
            }
        }
        int cameraId = 0;
        return cameraId;
    }
    public  String GetVieeoPath(Context context, String uri, String outputPaTH) {

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
                                Log.e("VideoCompreser", outputPaTH);
                                pBar.dismiss();
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
        return outputPaTH;
    }

    private  void callmethod() {
        callServiceUploadNews(outputPaTH);
    }


    @SuppressLint("NewApi")
    public static Bitmap retriveVideoFrameFromVideo(String p_videoPath)
            throws Throwable
    {
        Bitmap m_bitmap = null;
        MediaMetadataRetriever m_mediaMetadataRetriever = null;
        try
        {
            m_mediaMetadataRetriever = new MediaMetadataRetriever();
            m_mediaMetadataRetriever.setDataSource(p_videoPath);
            m_bitmap = m_mediaMetadataRetriever.getFrameAtTime();
        }
        catch (Exception m_e)
        {
            throw new Throwable(
                    "Exception in retriveVideoFrameFromVideo(String p_videoPath)"
                            + m_e.getMessage());
        }
        finally
        {
            if (m_mediaMetadataRetriever != null)
            {
                m_mediaMetadataRetriever.release();
            }
        }
        return m_bitmap;
    }
}