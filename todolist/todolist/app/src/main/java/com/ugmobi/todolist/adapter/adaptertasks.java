package com.ugmobi.todolist.adapter;

import android.app.Dialog;
import android.app.WallpaperInfo;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ugmobi.todolist.R;
import com.ugmobi.todolist.main.dbhelper;
import com.ugmobi.todolist.module.taskmodel;

import java.util.ArrayList;
import java.util.Objects;

public class adaptertasks extends RecyclerView.Adapter<adaptertasks.MyviewHolder> {
    ArrayList<taskmodel> list;
    Context context;
    dbhelper helper;

    public adaptertasks(ArrayList<taskmodel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public adaptertasks.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taglist_item, parent, false);
        return new MyviewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull adaptertasks.MyviewHolder holder, int position) {
        helper = new dbhelper(context);
        holder.taskname.setText(list.get(position).getName());
        if (list.get(position).getStatus().equals("true")) {
            holder.statusicon.setChecked(true);
            holder.cardview.setCardBackgroundColor(Color.parseColor("#90EE90"));
            holder.taskname.setPaintFlags(holder.taskname.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            holder.taskname.setPaintFlags(holder.taskname.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            holder.statusicon.setChecked(false);
            holder.cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.deletebtn.setOnClickListener(v -> {
            int pos = position;
            long id = helper.deleteTask(list.get(position).getId());
            if (id != -1) {
                list.remove(pos);
                notifyItemChanged(pos, list.size());
                notifyItemRemoved(pos);
            }
        });

        holder.editbrn.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.updatedialogue);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(true);
            AppCompatEditText tagtitle = dialog.findViewById(R.id.tagtitle);
            AppCompatButton canceltbn  = dialog.findViewById(R.id.cancelbtn);
            AppCompatButton updatebtn = dialog.findViewById(R.id.upadtebtn);
            tagtitle.setText(list.get(position).getName());
            canceltbn.setOnClickListener(v12 -> dialog.dismiss());

            updatebtn.setOnClickListener(v1 -> {
               long id =  helper.updateTask(list.get(position).getId(), Objects.requireNonNull(tagtitle.getText()).toString(),list.get(position).getStatus());
               if (id != -1){
                   list.get(position).setName(tagtitle.getText().toString());
                   holder.taskname.setText(list.get(position).getName());
                   notifyItemChanged(position,list.size());
                   notifyItemChanged(position);
                   notifyDataSetChanged();
                   dialog.dismiss();
                }
                dialog.dismiss();
            });
            dialog.show();

        });

        holder.itemView.setOnClickListener(v -> {
            if (list.get(position).getStatus().equals("true")) {
                holder.statusicon.setChecked(false);
                list.get(position).setStatus("false");
                helper.updateTask(list.get(position).getId(),list.get(position).getName(),"false");
                holder.cardview.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.taskname.setPaintFlags(holder.taskname.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            } else if (list.get(position).getStatus().equals("false")){
                holder.statusicon.setChecked(true);
                list.get(position).setStatus("true");
                helper.updateTask(list.get(position).getId(),list.get(position).getName(),"true");
                holder.cardview.setCardBackgroundColor(Color.parseColor("#90EE90"));
                holder.taskname.setPaintFlags(holder.taskname.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView deletebtn,editbrn;
        AppCompatCheckBox statusicon;
        AppCompatTextView taskname;
        CardView cardview;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            statusicon = itemView.findViewById(R.id.statusicon);
            taskname = itemView.findViewById(R.id.taskname);
            deletebtn = itemView.findViewById(R.id.deletebtn);
            cardview = itemView.findViewById(R.id.cardview);
            editbrn = itemView.findViewById(R.id.editbrn);

        }
    }
}
