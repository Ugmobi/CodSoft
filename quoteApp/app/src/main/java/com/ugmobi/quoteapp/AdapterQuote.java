package com.ugmobi.quoteapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AdapterQuote extends RecyclerView.Adapter<AdapterQuote.Myviewholder> {
    ArrayList<model> list = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;
    boolean favshow, bookmarkshow;
    Activity activity;
    ArrayList<model> fullloadedlist = new ArrayList<>();

    public AdapterQuote(ArrayList<model> list, Context context, boolean favedit, boolean bookedit,
                      RecyclerView recyclerView, Activity activity) {
        this.list = list;
        this.context = context;
        this.favshow = favedit;
        this.bookmarkshow = bookedit;
        this.recyclerView = recyclerView;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, @SuppressLint("RecyclerView") int position) {

        loadDataInstaBio();
        holder.bio.setText(list.get(position).getBio());
        int nightModeFlags =
                context.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(position).isFav()) {
                        holder.favbtn.setBackgroundResource(R.drawable.ic_favfill);
                    }
                    if (!list.get(position).isFav()) {
                        holder.favbtn.setBackgroundResource(R.drawable.ic_fav);
                    }
                    if (!list.get(position).isBookmark()) {
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#233159"));
                        holder.bookmarkbtn.setBackgroundResource(R.drawable.ic_class);
                    }
                    if (list.get(position).isBookmark()) {
                        holder.cardView.setCardBackgroundColor(Color.parseColor("#032218"));
                        holder.bookmarkbtn.setBackgroundResource(R.drawable.removebook);
                    }

                }

                break;

            case Configuration.UI_MODE_NIGHT_NO:
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(position).isFav()) {
                        holder.favbtn.setBackgroundResource(R.drawable.ic_favfill);
                    }
                    if (!list.get(position).isFav()) {
                        holder.favbtn.setBackgroundResource(R.drawable.ic_fav);
                    }
                    if (!list.get(position).isBookmark()) {
                        holder.cardView.setCardBackgroundColor(Color.WHITE);
                        holder.bookmarkbtn.setBackgroundResource(R.drawable.ic_class);
                    }
                    if (list.get(position).isBookmark()) {
                        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.bottom_bar_indicator_color));
                        holder.bookmarkbtn.setBackgroundResource(R.drawable.removebook);
                    }
                }

                break;
        }

        if (!bookmarkshow) {
            holder.bookmarkbtn.setOnClickListener(view -> {
                if (list.get(position).isBookmark()) {
                    switch (nightModeFlags) {
                        case Configuration.UI_MODE_NIGHT_YES:
                            list.get(position).setBookmark(false);
                            holder.bookmarkbtn.setBackgroundResource(R.drawable.ic_class);
                            holder.cardView.setCardBackgroundColor(Color.parseColor("#233159"));
                            SaveDataInstaBio();
                            break;

                        case Configuration.UI_MODE_NIGHT_NO:
                            list.get(position).setBookmark(false);
                            holder.bookmarkbtn.setBackgroundResource(R.drawable.ic_class);
                            holder.cardView.setCardBackgroundColor(Color.WHITE);
                            SaveDataInstaBio();
                            break;
                    }

                } else {
                    list.get(position).setBookmark(true);
                    holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.bottom_bar_indicator_color));
                    holder.bookmarkbtn.setBackgroundResource(R.drawable.removebook);
                    notifyItemChanged(position, list.size());
                    SaveDataInstaBio();
                }
            });
        }


        if (!favshow) {
            holder.favbtn.setOnClickListener(view -> {
                if (list.get(position).isFav()) {
                    list.get(position).setFav(false);
                    holder.favbtn.setBackgroundResource(R.drawable.ic_fav);
                    notifyItemChanged(position, list.size());
                    SaveDataInstaBio();
                    SaveDataInstaBioFav();

                } else {
                    list.get(position).setFav(true);
                    holder.favbtn.setBackgroundResource(R.drawable.ic_favfill);
                    notifyItemChanged(position, list.size());
                    SaveDataInstaBio();
                    SaveDataInstaBioFav();
                }

            });
        }


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
        ImageView copybtn, bookmarkbtn, sharebtn, favbtn;
        CardView cardView;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            bio = itemView.findViewById(R.id.id);
            favbtn = itemView.findViewById(R.id.favbtn);
            bookmarkbtn = itemView.findViewById(R.id.bookmarkbtn);
            copybtn = itemView.findViewById(R.id.copybtn);
            sharebtn = itemView.findViewById(R.id.sharebtn);
            cardView = itemView.findViewById(R.id.cardview);

        }
    }

    public void SaveDataInstaBio() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savedatainstabio", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("instabiolist", json);
        editor.apply();
    }

    public void SaveDataInstaBioFav() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savedatainstabiofav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("instabiolistfav", json);
        editor.apply();
    }

    private void loadDataInstaBio() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("savedatainstabio", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("instabiolist", null);
        Type type = new TypeToken<ArrayList<model>>() {
        }.getType();
        fullloadedlist = gson.fromJson(json, type);
        if (fullloadedlist == null) {
            fullloadedlist = new ArrayList<>();
        }
    }
}
