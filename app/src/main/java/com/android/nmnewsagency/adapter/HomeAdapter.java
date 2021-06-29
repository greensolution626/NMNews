package com.android.nmnewsagency.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.CommentsActivity;
import com.android.nmnewsagency.activity.UserProfileActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.zip.Inflater;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    // private List<LocationModel> moviesList;
    private List<String> moviesList;
    private List<Integer> imgList;
    Context context;

    /*public LocationAdapter(List<LocationModel> moviesList) {
        this.moviesList = moviesList;
    }*/

    public HomeAdapter(Context context, List<String> moviesList, List<Integer> imgList) {
        this.moviesList = moviesList;
        this.imgList = imgList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lin_report,lin_comments,lin_save,lin_share;
        RelativeLayout rel_userprofile;
        public ImageView img_homeprofile;

        public MyViewHolder(View view) {
            super(view);
            rel_userprofile = (RelativeLayout) view.findViewById(R.id.rel_userprofile);
            lin_report = (LinearLayout) view.findViewById(R.id.lin_report);
            lin_share = (LinearLayout) view.findViewById(R.id.lin_share);
            lin_save = (LinearLayout) view.findViewById(R.id.lin_save);
            lin_comments = (LinearLayout) view.findViewById(R.id.lin_comments);
            img_homeprofile = (ImageView) view.findViewById(R.id.img_homeprofile);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*LocationModel movie = moviesList.get(position);
        holder.title.setText(movie.getmName());*/
        // holder.title.setText(moviesList.get(position));
        holder.lin_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBox();
            }
        });
        Glide.with(context)
                .load(imgList.get(position))
                .placeholder(R.color.adbag)

                /*.transition(DrawableTransitionOptions.withCrossFade(500))*/
                .into(holder.img_homeprofile);
        holder.rel_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, UserProfileActivity.class);
                context.startActivity(intent);
            }
        });
        holder.lin_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CommentsActivity.class);
                context.startActivity(intent);
            }
        });
        holder.lin_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Item successfully save",Toast.LENGTH_SHORT).show();
            }
        });
        holder.lin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFeed();
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    private void openDialogBox() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_report);
        ImageView img_close_dialog = dialog.findViewById(R.id.img_close_dialog);
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
}

