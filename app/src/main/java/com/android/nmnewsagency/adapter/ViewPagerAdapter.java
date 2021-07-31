package com.android.nmnewsagency.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.abedelazizshe.lightcompressorlibrary.CompressionListener;
import com.abedelazizshe.lightcompressorlibrary.VideoCompressor;
import com.abedelazizshe.lightcompressorlibrary.VideoQuality;
import com.abedelazizshe.lightcompressorlibrary.config.Configuration;
import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.CommentsActivity;
import com.android.nmnewsagency.activity.UserProfileActivity;
import com.android.nmnewsagency.fragment.HomeFragment;
import com.android.nmnewsagency.modelclass.AddNewsModel;
import com.android.nmnewsagency.modelclass.GetNewsListModel;
import com.android.nmnewsagency.modelclass.LikeModelClass;
import com.android.nmnewsagency.modelclass.ReportModelClass;
import com.android.nmnewsagency.modelclass.SaveModelClass;
import com.android.nmnewsagency.rest.Rest;
import com.android.nmnewsagency.utils.Utils;
import com.bumptech.glide.Glide;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> implements
        View.OnClickListener, Callback<Object> {

    private List<GetNewsListModel.DataBean.PagedRecordBean> moviesList;
    private LayoutInflater mInflater;
    private ViewPager2 viewPager2;
    Context context;
    SimpleExoPlayer exoPlayer;
    String url;
    Rest rest;
    int follow, report, coments, like, save;
    boolean show = true, isLike = false, isSave = false;
    int current_pos, total_duration;
    static ViewHolder viewHolder;
    ProgressDialog pBar;
    String outputPaTH = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + System.nanoTime() + "compress.mp4";
    String outputPaTHkk = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + System.nanoTime() + "edit.mp4";


    private int[] colorArray = new int[]{android.R.color.black, android.R.color.holo_blue_dark, android.R.color.holo_green_dark, android.R.color.holo_red_dark};

    public ViewPagerAdapter(Context context, List<GetNewsListModel.DataBean.PagedRecordBean> data, ViewPager2 viewPager) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.moviesList = data;
        this.viewPager2 = viewPager;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Toast.makeText(context, "onCreateViewHolder", Toast.LENGTH_SHORT).show();
        View view = mInflater.inflate(R.layout.item_home, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.setIsRecyclable(false);
        url = moviesList.get(position).getVideoUrl();
        holder.videoView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                    holder.initPlayer(url);
                    // Toast.makeText(context, "initPlayer", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                try {
                    // Toast.makeText(context, "relese", Toast.LENGTH_SHORT).show();
                    holder.videoView.getPlayer().stop();
                    holder.releasePlayer();
                } catch (NullPointerException ex) {
                }
            }
        });
        rest = new Rest(context, this);
        GetNewsListModel.DataBean.PagedRecordBean movie = moviesList.get(position);
        holder.txt_follow.setOnClickListener(this);
        holder.lin_report.setOnClickListener(this);
        holder.lin_comments.setOnClickListener(this);
        holder.lin_save.setOnClickListener(this);
        holder.lin_share.setOnClickListener(this);
        holder.rel_userprofile.setOnClickListener(this);
        holder.lin_like.setOnClickListener(this);
        holder.txt_noofcoment.setText(String.valueOf(movie.getCommentCount()));
        holder.txt_nooflike.setText(String.valueOf(movie.getLikesCount()));
        //   String date = movie.getAddedOn();
        //  String[] parts = date.split("T");
        holder.rel_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  holder.videoView.getPlayer().stop();
                int pos = (int) v.getTag();
                Intent intent1 = new Intent(context, UserProfileActivity.class);
                intent1.putExtra("userId", moviesList.get(pos).getUserId());
                context.startActivity(intent1);
            }
        });
        holder.txt_title.setText(movie.getTitle());
        holder.txt_datetime.setText(Utils.parseDateToddMMyyyy(movie.getAddedOn()));
        holder.txt_description.setText(movie.getDescription());
        holder.txt_location.setText(movie.getState_Name() + " " + movie.getCountry_Name());
        holder.txt_username.setText(movie.getUserName());
        holder.txt_views.setText(String.valueOf(movie.getViewCount()));
        if (movie.isIsFollowed()) {
            holder.txt_follow.setText("Following");
            holder.txt_follow.setTextColor(Color.parseColor("#DBDBDB"));
        } else {
            holder.txt_follow.setText("Follow");
            holder.txt_follow.setTextColor(Color.parseColor("#E5383E"));
        }
        if (movie.isIsLike()) {
            isLike = true;
            holder.img_like.setImageResource(R.drawable.ic_dislike);
        } else {
            isLike = false;
            holder.img_like.setImageResource(R.drawable.ic_like);
        }
        if (movie.isIsSaved()) {
            isSave = true;
            holder.img_save.setImageResource(R.drawable.ic_notsave);
            holder.txt_savetext.setTextColor(Color.parseColor("#FF0000"));
        } else {
            isSave = false;
            holder.img_save.setImageResource(R.drawable.ic_save);
            holder.txt_savetext.setTextColor(Color.parseColor("#333333"));
        }
        holder.exoPause.setTag(position);
        holder.rel_userprofile.setTag(position);
        holder.exoPlay.setTag(position);
        holder.txt_follow.setTag(position);
        holder.lin_report.setTag(position);
        holder.lin_save.setTag(position);
        holder.lin_comments.setTag(position);
        holder.lin_like.setTag(position);
        holder.txt_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow = (int) v.getTag();
                if (holder.txt_follow.getText().equals("Follow")) {
                    holder.txt_follow.setText("Following");
                    holder.txt_follow.setTextColor(Color.parseColor("#DBDBDB"));
                } else {
                    holder.txt_follow.setText("Follow");
                    holder.txt_follow.setTextColor(Color.parseColor("#E5383E"));
                }
                callServicegetFollows(String.valueOf(moviesList.get(follow).getUserId()),
                        moviesList.get(follow).isIsFollowed());

            }
        });
        holder.lin_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like = (int) v.getTag();
                if (isLike) {
                    int no = Integer.parseInt(holder.txt_nooflike.getText().toString());
                    if (no > 0) {
                        int set = no - 1;
                        holder.txt_nooflike.setText(String.valueOf(set));
                        moviesList.get(like).setLikesCount(Integer.parseInt(holder.txt_nooflike.getText().toString()));
                    }
                    holder.img_like.setImageResource(R.drawable.ic_like);
                    callServicegetDisLike(moviesList.get(like).getNewsId());
                } else {
                    int no = Integer.parseInt(holder.txt_nooflike.getText().toString());

                    holder.txt_nooflike.setText(String.valueOf(no + 1));
                    moviesList.get(like).setLikesCount(Integer.parseInt(holder.txt_nooflike.getText().toString()));
                    holder.img_like.setImageResource(R.drawable.ic_dislike);
                    callServicegetLike(moviesList.get(like).getNewsId());
                }
            }
        });

        holder.lin_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report = (int) v.getTag();
                openDialogBox(moviesList.get(report).getNewsId());

            }
        });
        holder.lin_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save = (int) v.getTag();
                if (isSave) {
                    holder.txt_savetext.setTextColor(Color.parseColor("#333333"));
                    holder.img_save.setImageResource(R.drawable.ic_save);
                    callServicegetDeleteSave(moviesList.get(save).getNewsId());
                } else {
                    holder.txt_savetext.setTextColor(Color.parseColor("#FF0000"));
                    holder.img_save.setImageResource(R.drawable.ic_notsave);
                    callServicegetSave(moviesList.get(save).getNewsId());
                }
            }
        });
        holder.lin_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   holder.videoView.getPlayer().stop();
                coments = (int) v.getTag();
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("newsId", moviesList.get(coments).getNewsId());
                context.startActivity(intent);
            }
        });

        new Handler().post(new Runnable() {
            public void run() {
                Glide.with(context)
                        .load(moviesList.get(position).getImageUrl())
                        .into(holder.video_thuimbnail);
            }
        });
        new Handler().post(new Runnable() {
            public void run() {
                Glide.with(context)
                        .load(moviesList.get(position).getAvatar())
                        .into(holder.image_profile);
            }
        });
         /* try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
            Uri videouri = Uri.parse(mData.get(position).getVideoUrl());
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);
            holder.exoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            holder.exoPlayerView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                @Override
                public void onViewAttachedToWindow(View v) {
                   // holder.exoPlayerView.getPlayer().setPlayWhenReady(true);
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                  // holder.exoPlayerView.getPlayer().setPlayWhenReady(false);
                }
            });
        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }*/
    }


    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_follow:
                //  callServicegetCityList();
                break;
            case R.id.lin_share:
                shareFeed();
                break;
            case R.id.lin_comments:

                break;
            case R.id.lin_save:
                break;
            case R.id.rel_userprofile:

                break;
            case R.id.lin_report:
                // openDialogBox();
                break;
        }
    }

    @Override
    public void onResponse(Call<Object> call, Response<Object> response) {
        rest.dismissProgressdialog();
        if (response.isSuccessful()) {
            Object obj = response.body();
            Log.e("nmnnn", String.valueOf(obj));
            if (obj instanceof AddNewsModel) {
                AddNewsModel loginModel = (AddNewsModel) obj;
                if (loginModel.isStatus()) {
                    if (moviesList.get(follow).isIsFollowed()) {
                        moviesList.get(follow).setIsFollowed(false);
                    } else {
                        moviesList.get(follow).setIsFollowed(true);
                    }
                    // notifyDataSetChanged();
                }
            }
            if (obj instanceof LikeModelClass) {
                LikeModelClass loginModel = (LikeModelClass) obj;
                if (loginModel.isStatus()) {
                    if (moviesList.get(like).isIsLike()) {
                        moviesList.get(like).setIsLike(false);
                        isLike = false;
                    } else {
                        moviesList.get(like).setIsLike(true);
                        isLike = true;
                    }
                    // notifyDataSetChanged();
                }
            }
            if (obj instanceof SaveModelClass) {
                SaveModelClass loginModel = (SaveModelClass) obj;
                if (loginModel.isStatus()) {
                    if (moviesList.get(save).isIsSaved()) {
                        moviesList.get(save).setIsSaved(false);
                        isSave = false;
                    } else {
                        moviesList.get(save).setIsSaved(true);
                        isSave = true;
                    }
                    // notifyDataSetChanged();
                }
            }
            if (obj instanceof ReportModelClass) {
                ReportModelClass loginModel = (ReportModelClass) obj;
                if (loginModel.isStatus()) {
                    Toast.makeText(context, "Report successfully added", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFailure(Call<Object> call, Throwable t) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        PlayerView videoView;
        MediaPlayer audioplayer;

        DataSpec dataSpec;
        public TextView txt_username, txt_description, txt_title, txt_views, txt_location, txt_datetime;
        public LinearLayout lin_report, lin_comments, lin_save, lin_share, lin_like;
        LinearLayout rel_userprofile;
        TextView txt_follow, current, total, txt_noofcoment, txt_nooflike, txt_savetext;
        ImageButton exoPlay, exoPause;
        HttpProxyCacheServer proxy;
        ImageView video_thuimbnail, img_playpause, img_like, img_save;
        CircleImageView image_profile;
        SeekBar seekbar;
        BandwidthMeter bandwidthMeter;
        TrackSelector trackSelector;
        DefaultHttpDataSourceFactory dataSourceFactory;
        ExtractorsFactory extractorsFactory;
        SimpleExoPlayer simpleExoPlayer;
        LoadControl loadControl;
        MediaSource mediaSource;
        Uri videouri;

        ViewHolder(View view) {
            super(view);
            //   Toast.makeText(context, "ViewHolder", Toast.LENGTH_SHORT).show();
           /* myTextView = itemView.findViewById(R.id.tvTitle);
            relativeLayout = itemView.findViewById(R.id.container);
            button = itemView.findViewById(R.id.btnToggle);*/
            rel_userprofile = (LinearLayout) view.findViewById(R.id.rel_userprofile);
            lin_report = (LinearLayout) view.findViewById(R.id.lin_report);
            lin_like = (LinearLayout) view.findViewById(R.id.lin_like);
            lin_share = (LinearLayout) view.findViewById(R.id.lin_share);
            lin_save = (LinearLayout) view.findViewById(R.id.lin_save);
            lin_comments = (LinearLayout) view.findViewById(R.id.lin_comments);
            //  videoView = (VideoView) view.findViewById(R.id.img_homeprofile);
            //  exoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.idExoPlayerVIew);
            exoPlay = (ImageButton) view.findViewById(R.id.exo_play);
            exoPause = (ImageButton) view.findViewById(R.id.exo_pause);
            txt_username = (TextView) view.findViewById(R.id.txt_username);
            txt_savetext = (TextView) view.findViewById(R.id.txt_savetext);
            txt_description = (TextView) view.findViewById(R.id.txt_description);
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            txt_views = (TextView) view.findViewById(R.id.txt_views);
            txt_location = (TextView) view.findViewById(R.id.txt_location);
            txt_datetime = (TextView) view.findViewById(R.id.txt_datetime);
            txt_follow = (TextView) view.findViewById(R.id.txt_follow);
            video_thuimbnail = (ImageView) view.findViewById(R.id.video_thuimbnail);
            img_playpause = (ImageView) view.findViewById(R.id.img_playpause);
            img_save = (ImageView) view.findViewById(R.id.img_save);
            img_like = (ImageView) view.findViewById(R.id.img_like);
            image_profile = (CircleImageView) view.findViewById(R.id.image_profile);
            total = (TextView) view.findViewById(R.id.total);
            current = (TextView) view.findViewById(R.id.current);
            txt_nooflike = (TextView) view.findViewById(R.id.txt_nooflike);
            txt_noofcoment = (TextView) view.findViewById(R.id.txt_noofcoment);
            seekbar = (SeekBar) view.findViewById(R.id.seekbar);
            videoView = (PlayerView) itemView.findViewById(R.id.idExoPlayerVIew);
            audioplayer = new MediaPlayer();
            //audioplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            //  simpleExoPlayer = new SimpleExoPlayer.Builder(context).build();
            bandwidthMeter = new DefaultBandwidthMeter();
            trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            loadControl = new DefaultLoadControl.Builder()
                    .setAllocator(new DefaultAllocator(true, 10))
                    .setBufferDurationsMs(VideoPlayerConfig.MIN_BUFFER_DURATION,
                            VideoPlayerConfig.MAX_BUFFER_DURATION,
                            VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                            VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER)
                    .setTargetBufferBytes(-1)
                    .setPrioritizeTimeOverSizeThresholds(true).createDefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            extractorsFactory = new DefaultExtractorsFactory();

        }

        public void initPlayer(String url) {
            videouri = Uri.parse(url);
            mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory,
                    null, null);
            simpleExoPlayer.prepare(mediaSource);
            videoView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.setPlayWhenReady(true);
            videoView.setKeepContentOnPlayerReset(true);
            //  videoView.setFocusable(true);
            simpleExoPlayer.addListener(new Player.EventListener() {

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                    if (playbackState == ExoPlayer.STATE_READY) {
                        // Toast.makeText(context, "STATE_READY", Toast.LENGTH_SHORT).show();
                        video_thuimbnail.setVisibility(View.GONE);
                        videoView.setControllerShowTimeoutMs(1500);

                    } else if (playbackState == ExoPlayer.STATE_BUFFERING) {
                        // Toast.makeText(context, "STATE_BUFFERING", Toast.LENGTH_SHORT).show();
                        //  video_thuimbnail.setVisibility(View.VISIBLE);
                        // exo_pause.setVisibility(View.GONE);
                        videoView.setControllerShowTimeoutMs(3000);

                    } else if (playbackState == ExoPlayer.STATE_ENDED) {
                        // Toast.makeText(context, "STATE_ENDED", Toast.LENGTH_SHORT).show();
                        simpleExoPlayer.seekTo(0);
                        videoView.setControllerShowTimeoutMs(1);
                    }
                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {
                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {
                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
                }
            });

        }

        public void releasePlayer() {
            if (simpleExoPlayer != null) {
                //  Toast.makeText(context, "releasePlayer", Toast.LENGTH_SHORT).show();
                simpleExoPlayer.setPlayWhenReady(true);
                simpleExoPlayer.stop();
                simpleExoPlayer.release();
                simpleExoPlayer = null;

                videoView.setPlayer(null);
                videoView = null;
                System.out.println("releasePlayer");
                Log.e("releasePlayer", "releasePlayer");
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    private void openDialogBox(int newsId) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_report);
        ImageView img_close_dialog = dialog.findViewById(R.id.img_close_dialog);
        TextView txt_report_subtitle = dialog.findViewById(R.id.txt_report_subtitle);
        RadioGroup radio_group = dialog.findViewById(R.id.radio_group);
        RadioButton radio_frst = dialog.findViewById(R.id.radio_frst);
        RadioButton radio_scond = dialog.findViewById(R.id.radio_scond);
        RadioButton radio_thrd = dialog.findViewById(R.id.radio_thrd);
        RadioButton radio_furth = dialog.findViewById(R.id.radio_furth);
        RadioButton radio_fifth = dialog.findViewById(R.id.radio_fifth);
        RadioButton radio_six = dialog.findViewById(R.id.radio_six);

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
                    Toast.makeText(context, "Please select report reason", Toast.LENGTH_SHORT).show();
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
                    callServicegetReport(txt_report_subtitle.getText().toString(),
                            selectedRadioButton.getText().toString(), newsId);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    public void shareFeed() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my news app.");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            context.startActivity(shareIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callServicegetFollows(String folow, boolean isfollow) {
        rest.ShowDialogue(context.getResources().getString(R.string.pleaseWait));
        if (isfollow) {
            rest.UNfollowUser(folow);
        } else {
            rest.likeUser(folow);
        }
    }

    private void callServicegetReport(String folow, String isfollow, int newid) {
        rest.ShowDialogue(context.getResources().getString(R.string.pleaseWait));
        rest.reportUser(folow, isfollow, newid);
    }

    private void callServicegetLike(int newid) {
        rest.ShowDialogue(context.getResources().getString(R.string.pleaseWait));
        rest.likeUser1(newid);
    }

    private void callServicegetDisLike(int newid) {
        rest.ShowDialogue(context.getResources().getString(R.string.pleaseWait));
        rest.DislikeUser(newid);
    }

    private void callServicegetSave(int newid) {
        rest.ShowDialogue(context.getResources().getString(R.string.pleaseWait));
        rest.SaveUser(newid);
    }

    private void callServicegetDeleteSave(int newid) {
        rest.ShowDialogue(context.getResources().getString(R.string.pleaseWait));
        rest.deleteSaveUser(newid);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull @NotNull ViewPagerAdapter.ViewHolder holder) {

        //   holder.videoView.getPlayer().stop();
        super.onViewDetachedFromWindow(holder);
        //  holder.videoView.getPlayer().stop();
        // Toast.makeText(context, "onViewDetachedFromWindow", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        //Toast.makeText(context, "onViewRecycled", Toast.LENGTH_SHORT).show();
        super.onViewRecycled(holder);
        try {
            holder.videoView.getPlayer().stop();
            holder.releasePlayer();
        } catch (NullPointerException ex) {
            Log.d("recerror", ex.toString());
        }
    }

    public HomeFragment.OnActivityStateChanged registerActivityState() {
        return new HomeFragment.OnActivityStateChanged() {
            public void onResumed() {
                Log.d("SimpleTextListAdapter", "onResumed: ");
                // Toast.makeText(context,"Resumed now",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                // Toast.makeText(context, String.valueOf(simpleExoPlayer.getPlaybackState()), Toast.LENGTH_SHORT).show();
                // viewHolder.releasePlayer();
            }

            public void onPaused() {
                // Toast.makeText(context,"Paused now",Toast.LENGTH_SHORT).show();
                // viewHolder.videoView.getPlayer().stop();
                // viewHolder.releasePlayer();
                //  View view = getCurrentFocus();
                //  simpleExoPlayer.stop();
                //  Toast.makeText(context, "onPaused", Toast.LENGTH_SHORT).show();
                //  viewHolder.videoView.getPlayer().stop();
                //viewHolder.releasePlayer();
                notifyDataSetChanged();
                Log.d("SimpleTextListAdapter", "onPaused: ");
            }

            @Override
            public void onStop() {
                // Toast.makeText(context,"Stop now",Toast.LENGTH_SHORT).show();

                viewHolder.releasePlayer();

            }
        };
    }

    public class VideoPlayerConfig {
        //Minimum Video you want to buffer while Playing
        public static final int MIN_BUFFER_DURATION = 1000;
        //Max Video you want to buffer during PlayBack
        public static final int MAX_BUFFER_DURATION = 2000;
        //Min Video you want to buffer before start Playing it
        public static final int MIN_PLAYBACK_START_BUFFER = 1000;
        //Min video You want to buffer when user resumes video
        public static final int MIN_PLAYBACK_RESUME_BUFFER = 1000;
    }


    public class CompressVideo extends AsyncTask<Uri, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pBar = new ProgressDialog(context);
            pBar.setMessage("Please wait...It is downloading");
            pBar.setIndeterminate(false);
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected String doInBackground(Uri... uris) {
            VideoCompressor.start(
                    context, // => This is required if srcUri is provided. If not, pass null.
                    uris[0], // => Source can be provided as content uri, it requires context.
                    null, // => This could be null if srcUri and context are provided.
                    outputPaTH,
                    new CompressionListener() {
                        @Override
                        public void onStart() {
                            // Compression start
                            Log.e("VideoCompreser", "onStart");
                        }

                        @Override
                        public void onSuccess() {
                            // On Compression success
                            Log.e("VideoCompreser", "onSuccess");
                            int lent = outputPaTH.length();
                            Log.e("VideoCompreser", String.valueOf(lent));
                        }

                        @Override
                        public void onFailure(String failureMessage) {
                            // On Failure
                            Log.e("VideoCompreser", "onFailure");
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

            return outputPaTH;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pBar.dismiss();
        }
    }
}
