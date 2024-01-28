package com.ugmobi.todolist.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ugmobi.todolist.R;
import com.ugmobi.todolist.adapter.adaptertasks;
import com.ugmobi.todolist.module.taskmodel;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addbtn, deletebtn;
    dbhelper helper;
    RecyclerView recyclerViewmain;
    ArrayList<taskmodel> list = new ArrayList<>();
    adaptertasks adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deletebtn = findViewById(R.id.deletebtn);
        helper = new dbhelper(MainActivity.this);
        recyclerViewmain = findViewById(R.id.recyclerviewmain);
        recyclerViewmain.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        addbtn = findViewById(R.id.addbtn);
        loadDataTASK();
        adapter = new adaptertasks(list, this);
        recyclerViewmain.setAdapter(adapter);

        addbtn.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this, R.style.BottomSheetStyle);
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.addtagdialogue, null);
            Objects.requireNonNull(bottomSheetDialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            bottomSheetDialog.setContentView(view);
            CardView addbtn = view.findViewById(R.id.addbtn);
            AppCompatImageView closebtn = view.findViewById(R.id.closebtn);
            AppCompatEditText tagtitle = view.findViewById(R.id.tagtitle);
            closebtn.setOnClickListener(v1 -> bottomSheetDialog.dismiss());

            addbtn.setOnClickListener(v13 -> {
                String tasklabel = Objects.requireNonNull(tagtitle.getText()).toString();
                if (!tasklabel.isEmpty()) {
                    long id = helper.addTASK(tasklabel, "false");
                    if (id != -1) {
                        runOnUiThread(() -> {
                            taskmodel model = new taskmodel(id, tasklabel, "false");
                            list.add(model);
                            adapter.notifyDataSetChanged();
                            tagtitle.setText("");
                        });
                    }
                } else {
                    Toast.makeText(this, "Please Enter Task Name..", Toast.LENGTH_SHORT).show();
                }
            });

            bottomSheetDialog.show();
        });

        deletebtn.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.deletedialogue);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            AppCompatButton cancelbtn = dialog.findViewById(R.id.cancelbtn);
            AppCompatButton deletebtn = dialog.findViewById(R.id.deletebtn);
            deletebtn.setOnClickListener(v14 -> {
                if (!list.isEmpty()) {
                    for (int i = 0; i < list.size(); i++) {
                        helper.deleteTask(list.get(i).getId());
                    }
                    list.clear();
                    recyclerViewmain.setAdapter(null);
                }
                dialog.dismiss();
                Dialog dialog1 = new Dialog(MainActivity.this);
                dialog1.setContentView(R.layout.successdialogue);
                Objects.requireNonNull(dialog1.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog1.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog1.setCancelable(true);
                dialog1.setCanceledOnTouchOutside(true);
                runOnUiThread(() -> {
                    Handler handler = new Handler();
                    handler.postDelayed(dialog1::dismiss, 2000);
                });
                dialog1.show();
            });

            cancelbtn.setOnClickListener(v12 -> dialog.dismiss());

            dialog.show();
        });

    }

    @SuppressLint("Range")
    private void loadDataTASK() {
        Cursor cursor = helper.getTaskTable();
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(dbhelper.TASK_ID));
            String taskname = cursor.getString(cursor.getColumnIndex(dbhelper.TASK));
            String taskstatus = cursor.getString(cursor.getColumnIndex(dbhelper.TASK_STATUS));
            list.add(new taskmodel(Long.parseLong(String.valueOf(id)), taskname, taskstatus));
        }
    }
}