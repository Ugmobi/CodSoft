package com.ugmobi.quoteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ArrayList<model> instabiolist = new ArrayList<>();
    public static AdapterQuote adapterBio;
    RecyclerView recyclerView;
    int position;
    LinearProgressIndicator progressIndicator;
    SwipeRefreshLayout refresh;
    AppCompatImageView favbtn,bookmarkbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressIndicator = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview);
        refresh = findViewById(R.id.shuffle);
        favbtn = findViewById(R.id.favbtn);
        bookmarkbtn= findViewById(R.id.bookmarkbtn);

        readTextFile(getResources().openRawResource(R.raw.quotes));
        progressIndicator.show();
        progressIndicator.postDelayed(() -> {
            adapterBio = new AdapterQuote(instabiolist, this, false, false, recyclerView, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(adapterBio);
            progressIndicator.setVisibility(View.GONE);
            progressIndicator.hide();
        }, 2000);
        refresh.setOnRefreshListener(() -> {
            shuffle();
            refresh.setRefreshing(false);
        });

        favbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavActivity.class);
            intent.putExtra("which","fav");
            startActivity(intent);
        });

        bookmarkbtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,FavActivity.class);
            intent.putExtra("which","book");
            startActivity(intent);
        });



    }

    private void shuffle() {
        Collections.shuffle(instabiolist);
        adapterBio = new AdapterQuote(instabiolist, this, false, false, recyclerView, this);
        recyclerView.swapAdapter(adapterBio, false);
        adapterBio.notifyDataSetChanged();
    }


    private void readTextFile(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
        int i = 0;
        while (true) {
            String str = "";
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (readLine.equals("*****")) {
                            position = position + 1;
                            break;
                        }
                        str = str + "\n" + readLine;
                    } else {
                        inputStream.close();
                        inputStreamReader.close();
                        bufferedReader.close();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
            i++;
            if (i % 5 == 0) {
                model model = new model(str, false, false, true, String.valueOf(position));
                instabiolist.add(model);
            } else {
                model model = new model(str, false, false, false, String.valueOf(position));
                instabiolist.add(model);
            }
        }
    }
}