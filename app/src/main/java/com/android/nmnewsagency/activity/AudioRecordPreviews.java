package com.android.nmnewsagency.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.UploadNewsModel;
import com.android.nmnewsagency.pref.Prefrence;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.service.NewsUplaodInBagroundService;
import com.android.nmnewsagency.utils.Utils;
import com.github.piasy.rxandroidaudio.AudioRecorder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import me.tankery.lib.circularseekbar.CircularSeekBar;
import nl.bravobit.ffmpeg.ExecuteBinaryResponseHandler;
import nl.bravobit.ffmpeg.FFprobe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vimeoextractor.OnVimeoExtractionListener;
import vimeoextractor.VimeoExtractor;
import vimeoextractor.VimeoVideo;

public class AudioRecordPreviews extends AppCompatActivity implements Callback<Object> {
    Button button_audiocapture, button_save;
    ImageView img_thumbnail, iamge_back_audio_record;
    AudioRecorder mAudioRecorder;
    File mAudioFile;
    CircularSeekBar circularSeekBar;
    VideoView vidview_audiorecord;
    boolean startRecord = true;
    String fileofaudio,thumbanilVimeo;
    CountDownTimer countDownTimer;
    int duration;
    TextView txt_timeer;
    private UploadNewsModel.DataBean dataBean = null;
    ProgressDialog dialog;
    File outputFile,muxingVideopath;
    LinearLayout lin_tapheare_audio;
    private FFprobe ffmpeg;
    SeekBar seekbar;
    int current_pos;
    boolean muxing = false;
    Rest rest;
    RelativeLayout rel_audiotop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dataBean = (UploadNewsModel.DataBean) bundle.get("video");
        }

        iniIt();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void iniIt() {
        rest=new Rest(this,this);
        loadFFMpegBinary();
        muxingVideopath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + System.nanoTime() + "muxing_video.mp4");
        circularSeekBar = findViewById(R.id.seek_bar);
        rel_audiotop = findViewById(R.id.rel_audiotop);
        mAudioRecorder = AudioRecorder.getInstance();
        fileofaudio = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + System.nanoTime() + "edit.mp4";
        /*fileofVideo = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + System.nanoTime() + "editaac.mp4");*/
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        button_audiocapture = findViewById(R.id.button_audiocapture);
        txt_timeer = findViewById(R.id.txt_timeer);
        img_thumbnail = findViewById(R.id.img_thumbnail);
        lin_tapheare_audio = findViewById(R.id.lin_tapheare_audio);
        button_save = findViewById(R.id.button_save);
        iamge_back_audio_record = findViewById(R.id.iamge_back_audio_record);
        vidview_audiorecord = findViewById(R.id.vidview_audiorecord);
        vidview_audiorecord.setVideoPath(Prefrence.getVideoFile());
        vidview_audiorecord.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
               // mp.setVolume(0f, 0f);
               // mp.setLooping(true);
                if (!muxing) {
                    mp.setVolume(0f, 0f);
                }
            }
        });
        vidview_audiorecord.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                if (muxing) {
                    vidview_audiorecord.start();
                }
                // vidview_audiorecord.start();

            }
        });
        iamge_back_audio_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(muxing){
                    iniIt();
                }
                else {*/
                    AudioRecordPreviews.this.finish();
              //  }
            }
        });
        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                String message = String.format("Progress changed to %.2f, fromUser %s", progress, fromUser);
                //  Log.e("Main", message);
                //  Log.e("progress", String.valueOf(progress));
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                Log.d("Main", "onStopTrackingTouch");
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                Log.d("Main", "onStartTrackingTouch");
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callServiceUploadNews(muxingVideopath.getAbsolutePath());
            }
        });
        getDuration();
        circularSeekBar.setMax(duration);
        seekbar.setMax((int) duration);
        Log.e("videoduration", getDuration());
        txt_timeer.setText(getDuration());

        if (dataBean.getImageUrl().equals("")) {
            getThumbnail(dataBean.getVideoId());
        } else {
            new ImageLoadTask(dataBean.getImageUrl(), img_thumbnail).execute();
        }

        button_audiocapture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // Button is pressed
                        if (startRecord) {
                           // Toast.makeText(AudioRecordPreviews.this, "audiostart", Toast.LENGTH_SHORT).show();
                            lin_tapheare_audio.setVisibility(View.GONE);
                            vidview_audiorecord.setVisibility(View.VISIBLE);
                           // txt_timeer.setVisibility(View.VISIBLE);
                            img_thumbnail.setVisibility(View.GONE);
                            showtimerAudioRecording();
                            vidview_audiorecord.start();
                            circularSeekBar.setVisibility(View.VISIBLE);
                            mAudioFile = new File(
                                    Environment.getExternalStorageDirectory().getAbsolutePath() +
                                            File.separator + System.nanoTime() + ".m4a");
                            mAudioRecorder.prepareRecord(MediaRecorder.AudioSource.MIC,
                                    MediaRecorder.OutputFormat.DEFAULT, MediaRecorder.AudioEncoder.AAC,
                                    mAudioFile);
                            mAudioRecorder.startRecord();
                            startRecord = false;
                        } else {

                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        // Button is not pressed
                        countDownTimer.cancel();
                        txt_timeer.setVisibility(View.GONE);
                        img_thumbnail.setVisibility(View.VISIBLE);
                        circularSeekBar.setVisibility(View.GONE);
                        lin_tapheare_audio.setVisibility(View.VISIBLE);
                        // stop recording and release
                        Log.e("circlevalue===",String.valueOf(circularSeekBar.getMax()));
                       // Toast.makeText(AudioRecordPreviews.this, String.valueOf(circularSeekBar.getMax()), Toast.LENGTH_SHORT).show();
                        vidview_audiorecord.stopPlayback();
                        vidview_audiorecord.setVisibility(View.GONE);
                        mAudioRecorder.stopRecord();
                        startRecord = true;
                        if(!muxing) {
                            Utils.showSnakBarDialog(AudioRecordPreviews.this, rel_audiotop,
                                    "Your audio duration is very short. Please record audio of " + getDuration() + " duration",
                                    R.color.alert);
                        }
                    }
                }
                return false;

            }

        });
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    current_pos = vidview_audiorecord.getCurrentPosition();
                    //  Log.e("pos===================",String.valueOf((int)current_pos));
                    seekbar.setProgress(current_pos);
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);

        vidview_audiorecord.setVisibility(View.GONE);
        img_thumbnail.setVisibility(View.VISIBLE);
        button_audiocapture.setVisibility(View.VISIBLE);
        lin_tapheare_audio.setVisibility(View.VISIBLE);
        button_save.setVisibility(View.GONE);
    }



    public void nextactivityGoing() {

        Intent intent = new Intent(AudioRecordPreviews.this, RecordingPreviewActivity.class);
        intent.putExtra("videopath", outputFile.getAbsolutePath());
        startActivity(intent);

    }

    private void callServiceUploadNews(String path) {
        if (rest.isInterentAvaliable()) {
        Prefrence.setVideoFIle(path);
       // setProgressSet();
       // rest.uploadNews(path);
            startService(new Intent(this, NewsUplaodInBagroundService.class));
            Intent returnIntent = new Intent();
           // returnIntent.putExtra("video", data);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        } else {
            rest.AlertForInternet();
        }

    }
    public void setProgressSet() {
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait for a sec ..");
        dialog.setProgress(0);
        dialog.setMax(100);
        dialog.show();
        final int totalProgressTime = 95;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(2000);
                        jumpTime += 2;
                        dialog.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {

            Object obj = response.body();
            if (obj instanceof UploadNewsModel) {
                UploadNewsModel loginModel = (UploadNewsModel) obj;
                if (loginModel.isStatus()) {
                    dialog.setProgress(100);
                    dialog.dismiss();
                    nextActivityGoing(loginModel.getData());
                }
            }

        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }
    private void nextActivityGoing(UploadNewsModel.DataBean data) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("video", data);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private static class BufferedWritableFileByteChannel implements WritableByteChannel {
        private static final int BUFFER_CAPACITY = 1000000;

        private boolean isOpen = true;
        private final OutputStream outputStream;
        private final ByteBuffer byteBuffer;
        private final byte[] rawBuffer = new byte[BUFFER_CAPACITY];

        private BufferedWritableFileByteChannel(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.byteBuffer = ByteBuffer.wrap(rawBuffer);
        }


        @Override
        public boolean isOpen() {
            return isOpen;
        }

        @Override
        public void close() throws IOException {
            dumpToFile();
            isOpen = false;
        }

        private void dumpToFile() {
            try {
                outputStream.write(rawBuffer, 0, byteBuffer.position());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public int write(ByteBuffer inputBuffer) throws IOException {
            int inputBytes = inputBuffer.remaining();

            if (inputBytes > byteBuffer.remaining()) {
                dumpToFile();
                byteBuffer.clear();

                if (inputBytes > byteBuffer.remaining()) {
                    throw new BufferOverflowException();
                }
            }

            byteBuffer.put(inputBuffer);

            return inputBytes;
        }
    }

    @SuppressLint("DefaultLocale")
    public String getDuration() {
        MediaPlayer mp = MediaPlayer.create(this, Uri.parse(Prefrence.getVideoFile()));
        duration = mp.getDuration();
        mp.release();
        /*convert millis to appropriate time*/
        return String.format("0%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))

        );
        // return String.valueOf(duration);
    }

    public void showtimerAudioRecording() {
        // button_audiocapture.setVisibility(View.GONE);
        // audio_stop.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                txt_timeer.setText(f.format(min) + ":" + f.format(sec));
                circularSeekBar.setProgress(millisUntilFinished);
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                // txt_timer_done.setVisibility(View.VISIBLE);
                txt_timeer.setVisibility(View.GONE);
                button_audiocapture.setVisibility(View.VISIBLE);
                // audio_stop.setVisibility(View.GONE);

                // stop recording and release
              //  Toast.makeText(AudioRecordPreviews.this, "audioend", Toast.LENGTH_SHORT).show();
                vidview_audiorecord.stopPlayback();
                vidview_audiorecord.setVisibility(View.GONE);
                img_thumbnail.setVisibility(View.VISIBLE);
                circularSeekBar.setVisibility(View.GONE);
                mAudioRecorder.stopRecord();
                startRecord = true;
                muxing = true;
                //  boolean statusEdigt=mux(Prefrence.getVideoFile(), mAudioFile.getAbsolutePath(), outputPaTH);
                //  Toast.makeText(AudioRecordActivity.this, String.valueOf(statusEdigt), Toast.LENGTH_SHORT).show();
                // mergingAUdioVideo();
                execFFmpegBinaryShortest();
            }
        }.start();
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
     /* try {

        AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(mAudioFile.toString()));
            H264TrackImpl h264Track = new H264TrackImpl(new FileDataSourceImpl(Prefrence.getVideoFile()));
        Movie movie = new Movie();
        movie.addTrack(h264Track);
        movie.addTrack(aacTrack);

            Container mp4file = new DefaultMp4Builder().build(movie);
            FileChannel fc = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString() + "/video.mp4")).getChannel();
            mp4file.writeContainer(fc);
            fc.close();
        } catch (IOException e) {
            Log.d("", "Mixer Error 1 " + e.getMessage());
        }*/


    private void execFFmpegBinaryShortest() {
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        //  dialog.setMessage("Proccesing ..");
        outputFile = new File(Environment.getExternalStorageDirectory().
                getAbsolutePath() + File.separator + System.nanoTime() + "ffmpegmergingvideo.mp4");

        String[] cmd = new String[]{"-i", Prefrence.getVideoFile(), "-i", mAudioFile.getAbsolutePath(),
                "-filter_complex", "[0:a][1:a]amerge=inputs=2[a]", "-map", "0:v", "-map", "[a]", "-c:v", "copy", "-ac", "2",
                muxingVideopath.getAbsolutePath()};


        ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {
            @Override
            public void onFailure(String s) {
                System.out.println("on failure----" + s);
            }

            @Override
            public void onSuccess(String s) {
                System.out.println("on success-----" + s);
                Log.e("succesffmpeg", "SUCCESS with output : " + s);
               // nextactivityGoing();
                afterMuxingVideoShow();
            }

            @Override
            public void onProgress(String s) {
                //Log.d(TAG, "Started command : ffmpeg "+command);
                System.out.println("Started---" + s);
                dialog.setMessage("Please Wait ...");
            }

            @Override
            public void onStart() {
               // dialog.setMessage("Start...");
                dialog.show();
                //Log.d(TAG, "Started command : ffmpeg " + command);
                System.out.println("Start----");

            }

            @Override
            public void onFinish() {
                System.out.println("Finish-----");
                dialog.dismiss();

            }
        });


    }

    private void loadFFMpegBinary() {
        try {
            if (FFprobe.getInstance(this).isSupported()) {
                ffmpeg = FFprobe.getInstance(this);
            } else {
                showUnsupportedExceptionDialog();
            }

        } catch (Exception e) {
            Log.e("ffmpegcatchexception", "EXception no controlada : " + e);
        }
    }

    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(AudioRecordPreviews.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Not Supported")
                .setMessage("Device Not Supported")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AudioRecordPreviews.this.finish();
                    }
                })
                .create()
                .show();

    }
    public void afterMuxingVideoShow() {

        vidview_audiorecord.setVisibility(View.VISIBLE);
        img_thumbnail.setVisibility(View.GONE);
        button_audiocapture.setVisibility(View.GONE);
        lin_tapheare_audio.setVisibility(View.GONE);
        button_save.setVisibility(View.VISIBLE);
        vidview_audiorecord.setVideoPath(muxingVideopath.getAbsolutePath());
        vidview_audiorecord.start();

    }
    public void getThumbnail(String vimeoUrl) {
        // Log.e("vimeothumb====","https://vimeo.com/"+vimeoUrl+".xml");
        VimeoExtractor.getInstance().fetchVideoWithURL("https://vimeo.com/api/v2/video/" + vimeoUrl, null, new OnVimeoExtractionListener() {
            @Override
            public void onSuccess(VimeoVideo video) {
                String hdStream = video.getThumbs().get("640");
                thumbanilVimeo=hdStream;
                Log.e("vimeothumb====", hdStream);
                try {
                    ContextCompat.getMainExecutor(AudioRecordPreviews.this).execute(new Runnable() {
                        @Override
                        public void run() {
                            // Utils.loadImageUsingGlidePlaceHolder(context, hdStream, holder.img_videoThumb, R.mipmap.ic_launcher_foreground);
                            new ImageLoadTask(hdStream, img_thumbnail).execute();
                        }
                    });
                } catch (Exception e) {

                }


            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("error", throwable.getMessage());
            }
        });
    }
}