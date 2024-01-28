package com.ugmobi.quoteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavActivity extends AppCompatActivity {
    ArrayList<model> tempdata = new ArrayList<>();
    AdapterQuote adapter;
    RecyclerView recyclerView;
    ArrayList<model> list = new ArrayList<>();
    String whichone;
    int choice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        recyclerView = findViewById(R.id.recyclerview);
        Intent intent = getIntent();
        if (intent !=null){
            whichone = intent.getStringExtra("which");
            if (whichone.equals("fav")){
                choice = 1;
            }else if (whichone.equals("book")){
                choice = 2;
            }
        }

        loadDataInstaBio();
        switch (choice){
            case 1:
                tempdata.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isFav()) {
                        model model = new model(list.get(i).getBio(), list.get(i).isFav(), list.get(i).isBookmark(),list.get(i).isShowad(), list.get(i).getPosition());
                        tempdata.add(model);
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                adapter = new AdapterQuote(tempdata, this, true, true, recyclerView, FavActivity.this);
                recyclerView.setAdapter(adapter);

                break;

            case 2:
                tempdata.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isBookmark()) {
                        model biomodule = new model(list.get(i).getBio(), list.get(i).isFav(), list.get(i).isBookmark(),list.get(i).isShowad(), list.get(i).getPosition());
                        tempdata.add(biomodule);
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                adapter = new AdapterQuote(tempdata, this, true, true, recyclerView, FavActivity.this);
                recyclerView.setAdapter(adapter);

                break;
            default:
                Toast.makeText(this, "Opps Something went wrong.", Toast.LENGTH_SHORT).show();
                break;
        }

    }
    public void loadDataInstaBio() {
        list.clear();
        SharedPreferences sharedPreferences = getSharedPreferences("savedatainstabio", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("instabiolist", null);
        Type type = new TypeToken<ArrayList<model>>() {
        }.getType();
        list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<>();
        }
    }
}