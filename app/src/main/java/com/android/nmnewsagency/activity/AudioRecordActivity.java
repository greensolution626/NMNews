package com.android.nmnewsagency.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.modelclass.UploadNewsModel;
import com.android.nmnewsagency.pref.Prefrence;

import com.github.piasy.rxandroidaudio.AudioRecorder;

import org.mp4parser.Container;
import org.mp4parser.muxer.FileDataSourceImpl;
import org.mp4parser.muxer.Movie;
import org.mp4parser.muxer.Track;
import org.mp4parser.muxer.builder.DefaultMp4Builder;
import org.mp4parser.muxer.container.mp4.MovieCreator;
import org.mp4parser.muxer.tracks.AACTrackImpl;
import org.mp4parser.muxer.tracks.h264.H264TrackImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.concurrent.TimeUnit;

import VideoHandle.EpEditor;
import VideoHandle.OnEditorListener;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class AudioRecordActivity extends AppCompatActivity {
    ImageView button_audiocapture, audio_stop, img_thumbnail;
    AudioRecorder mAudioRecorder;
    File mAudioFile;
    CircularSeekBar circularSeekBar;
    VideoView vidview_audiorecord;
    boolean startRecord = true;
    String outputPaTH;
    CountDownTimer countDownTimer;
    int duration;
    TextView txt_timeer;
    private UploadNewsModel.DataBean dataBean = null;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_record);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dataBean = (UploadNewsModel.DataBean) bundle.get("video");
        }
        iniIt();
    }

    private void iniIt() {
        circularSeekBar = (CircularSeekBar) findViewById(R.id.seek_bar);
        mAudioRecorder = AudioRecorder.getInstance();
        outputPaTH = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + System.nanoTime() + "edit.mp4";
        button_audiocapture = (ImageView) findViewById(R.id.button_audiocapture);
        txt_timeer = (TextView) findViewById(R.id.txt_timeer);
        audio_stop = (ImageView) findViewById(R.id.audio_stop);
        img_thumbnail = (ImageView) findViewById(R.id.img_thumbnail);
        vidview_audiorecord = (VideoView) findViewById(R.id.vidview_audiorecord);
        vidview_audiorecord.setVideoPath(Prefrence.getVideoFile());
        vidview_audiorecord.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVolume(0f, 0f);
                mp.setLooping(true);
            }
        });
        vidview_audiorecord.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                // vidview_audiorecord.start();

            }
        });

        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                String message = String.format("Progress changed to %.2f, fromUser %s", progress, fromUser);
                Log.d("Main", message);
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
        getDuration();
        Log.e("videoduration", getDuration());
        new ImageLoadTask(dataBean.getImageFullPath(),
                img_thumbnail).execute();

        audio_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                txt_timeer.setVisibility(View.GONE);
                button_audiocapture.setVisibility(View.VISIBLE);
                audio_stop.setVisibility(View.GONE);
                img_thumbnail.setVisibility(View.VISIBLE);
                // stop recording and release
                Toast.makeText(AudioRecordActivity.this, "audioend", Toast.LENGTH_SHORT).show();
                vidview_audiorecord.stopPlayback();
                vidview_audiorecord.setVisibility(View.GONE);
                mAudioRecorder.stopRecord();
                startRecord = true;
                mergingAUdioVideo();
            }
        });
        button_audiocapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startRecord) {
                    Toast.makeText(AudioRecordActivity.this, "audiostart", Toast.LENGTH_SHORT).show();
                    vidview_audiorecord.setVisibility(View.VISIBLE);
                    txt_timeer.setVisibility(View.VISIBLE);
                    img_thumbnail.setVisibility(View.GONE);
                    showtimerAudioRecording();
                    vidview_audiorecord.start();
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
            }
        });
    }

    public void mergingAUdioVideo() {
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


        AACTrackImpl aacTrack = null;
        try {
            // aacTrack = new AACTrackImpl(new FileDataSourceImpl(mAudioFile.toString()));
            // Movie countVideo = MovieCreator.build(Prefrence.getVideoFile());
            Movie video = MovieCreator.build(Prefrence.getVideoFile());
            Track videoTrack = video.getTracks().get(0);


            Movie audio = MovieCreator.build(mAudioFile.getAbsolutePath()); // here
            Track audioTrack = audio.getTracks().get(0);


            Movie movie = new Movie();
            movie.addTrack(videoTrack);
            movie.addTrack(audioTrack);

            Container mp4file = new DefaultMp4Builder().build(movie);

            FileChannel fc = new FileOutputStream(
                    new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                            File.separator + System.nanoTime() + "editaac.mp4")).getChannel();


            mp4file.writeContainer(fc);
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean mux(String videoFile, String audioFile, String outputFile) {
        Movie video;
        try {
            video = new MovieCreator().build(videoFile);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Movie audio;
        try {
            audio = new MovieCreator().build(audioFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        Track audioTrack = audio.getTracks().get(0);
        video.addTrack(audioTrack);

        Container out = new DefaultMp4Builder().build(video);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        BufferedWritableFileByteChannel byteBufferByteChannel = new BufferedWritableFileByteChannel(fos);
        try {
            out.writeContainer(byteBufferByteChannel);
            byteBufferByteChannel.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
        /*return String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))

        );*/
        return String.valueOf(duration);
    }

    public void showtimerAudioRecording() {
        button_audiocapture.setVisibility(View.GONE);
        audio_stop.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                txt_timeer.setText(f.format(min) + ":" + f.format(sec));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                // txt_timer_done.setVisibility(View.VISIBLE);
                txt_timeer.setVisibility(View.GONE);
                button_audiocapture.setVisibility(View.VISIBLE);
                audio_stop.setVisibility(View.GONE);

                // stop recording and release
                Toast.makeText(AudioRecordActivity.this, "audioend", Toast.LENGTH_SHORT).show();
                vidview_audiorecord.stopPlayback();
                vidview_audiorecord.setVisibility(View.GONE);
                img_thumbnail.setVisibility(View.VISIBLE);
                mAudioRecorder.stopRecord();
                startRecord = true;
                //  boolean statusEdigt=mux(Prefrence.getVideoFile(), mAudioFile.getAbsolutePath(), outputPaTH);
                //  Toast.makeText(AudioRecordActivity.this, String.valueOf(statusEdigt), Toast.LENGTH_SHORT).show();
                 mergingAUdioVideo();
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
}