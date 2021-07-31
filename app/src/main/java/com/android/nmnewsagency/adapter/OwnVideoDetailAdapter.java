package com.android.nmnewsagency.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.nmnewsagency.R;
import com.android.nmnewsagency.activity.CommentsActivity;
import com.android.nmnewsagency.activity.MessageDetailActivity;
import com.android.nmnewsagency.activity.UserOwnDegtailProfileActivity;
import com.android.nmnewsagency.activity.UserProfileActivity;
import com.android.nmnewsagency.modelclass.GetNewsByIdModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class OwnVideoDetailAdapter extends RecyclerView.Adapter<OwnVideoDetailAdapter.MyViewHolder> {
    private List<GetNewsByIdModel.DataBean> moviesList;
    Context context;

    public OwnVideoDetailAdapter(Context context, List<GetNewsByIdModel.DataBean> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public LinearLayout lin_chat_qwnvideo, lin_create, lin_own_share, lin_own_comnt;
        RelativeLayout rel_ownvideo_profile;
        ImageView img_delete;

        public MyViewHolder(View view) {
            super(view);
            lin_chat_qwnvideo = (LinearLayout) view.findViewById(R.id.lin_chat_qwnvideo);
            lin_create = (LinearLayout) view.findViewById(R.id.lin_create);
            lin_own_share = (LinearLayout) view.findViewById(R.id.lin_own_share);
            lin_own_comnt = (LinearLayout) view.findViewById(R.id.lin_own_comnt);
            rel_ownvideo_profile = (RelativeLayout) view.findViewById(R.id.rel_ownvideo_profile);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ownvideodetail, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*LocationModel movie = moviesList.get(position);
        holder.title.setText(movie.getmName());*/
        // holder.title.setText(moviesList.get(position));
        holder.lin_chat_qwnvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageDetailActivity.class);
                context.startActivity(intent);
            }
        });
        holder.rel_ownvideo_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserOwnDegtailProfileActivity.class);
                context.startActivity(intent);
            }
        });
        holder.lin_own_comnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                context.startActivity(intent);
            }
        });
        holder.lin_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBoxCreate();
            }
        });

        holder.lin_own_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareFeed();
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogBoxDelete();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private void openDialogBox() {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_report);
        dialog.show();
    }

    private void openDialogBoxCreate() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.dialog_createvideo);

        bottomSheetDialog.show();
    }

    private void openDialogBoxDelete() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.dialog_delete);

        bottomSheetDialog.show();
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

