package com.jcc.smartcar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    OverviewFragment overviewFragment = new OverviewFragment();
    DrivingAndControlFragment drivingAndControlFragment = new DrivingAndControlFragment();
    MoreFragment moreFragment = new MoreFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id. container, overviewFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Overview:
                        getSupportFragmentManager().beginTransaction().replace(R.id. container, overviewFragment).commit();
                        return true;

                    case R.id.DrivingControl:
                        getSupportFragmentManager().beginTransaction().replace(R.id. container, drivingAndControlFragment).commit();
                        return true;

                    case R.id.More:
                        getSupportFragmentManager().beginTransaction().replace(R.id. container, moreFragment).commit();
                        return true;

                }

                return false;
            }
        });

    }
}