package com.ugmobi.quoteapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.Myviewholder> {
    ArrayList<model> list = new ArrayList<>();
    Context context;
    Activity activity;
    Dialog dialog;

    public FavAdapter(ArrayList<model> list, Context context, Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_items, parent, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.bio.setText(list.get(position).getBio());
        holder.sharebtn.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_TEXT, list.get(position).getBio());
                context.startActivity(Intent.createChooser(intent, "share"));

        });

        holder.copybtn.setOnClickListener(view -> {
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", list.get(position).getBio());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder {
        TextView bio;
        ImageView favbtn, copybtn, sharebtn;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            bio = itemView.findViewById(R.id.id);
            favbtn = itemView.findViewById(R.id.favbtn);
            sharebtn = itemView.findViewById(R.id.sharebtn);
            copybtn = itemView.findViewById(R.id.copybtn);
        }
    }

}
