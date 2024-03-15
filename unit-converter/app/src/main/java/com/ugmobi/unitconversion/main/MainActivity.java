package com.ugmobi.unitconversion.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.hardware.Camera;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ugmobi.unitconversion.R;

public class MainActivity extends AppCompatActivity {
    public static final Fragment fragment1 = new LengthFragment();
    public static final Fragment fragment3 = new WeightFragmnet();
    public static FragmentManager fm;
    public static BottomNavigationView smoothBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smoothBottomBar = findViewById(R.id.bottom_navigation);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.framelayout, fragment1, "1").show(fragment1).commitNow();
        // fm.beginTransaction().add(R.id.framelayout, fragment2, "2").hide(fragment2).commitNow();
        fm.beginTransaction().add(R.id.framelayout, fragment3, "3").hide(fragment3).commitNow();
        smoothBottomBar.setOnItemSelectedListener(item -> {
            int meniid = item.getItemId();
            if (meniid == R.id.length) {
                fm.beginTransaction().hide(fragment1).hide(fragment3).show(fragment1).commitNow();
                return true;

            } else if (meniid == R.id.weight) {
                fm.beginTransaction().hide(fragment1).hide(fragment3).show(fragment3).commitNow();
                return true;
            }
            return false;
        });

    }
}