package com.example.project.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.project.Adapter.ViewPagerAdapter;
import com.example.project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UI extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private TextView tvHistory;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_bar);
        viewPager2 = findViewById(R.id.viewPager2);
        tvHistory = findViewById(R.id.tv_history);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.nav_library).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.nav_profile).setChecked(true);
                        break;
                }

            }
        });
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                viewPager2.setCurrentItem(0);
                return true;
            } else if (itemId == R.id.nav_library) {
                viewPager2.setCurrentItem(1);
                return true;
            } else if (itemId == R.id.nav_profile) {
                viewPager2.setCurrentItem(2);
                return true;
            }
            return false;
        });




    }
}