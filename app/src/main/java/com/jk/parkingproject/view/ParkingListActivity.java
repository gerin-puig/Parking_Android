package com.jk.parkingproject.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.jk.parkingproject.R;
import com.jk.parkingproject.shared.ParkingSharedPrefs;
import com.jk.parkingproject.ui.main.SectionsPagerAdapter;

/**
 * Gerin Puig - 101343659
 * Rajdeep Dodiya - 101320088
 */

public class ParkingListActivity extends AppCompatActivity {

    private String currentUserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: hide action bar
        setContentView(R.layout.activity_parking_list);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // set selected tabs color
        tabs.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        tabs.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

        FloatingActionButton fab = findViewById(R.id.fab);

        ParkingSharedPrefs psp = new ParkingSharedPrefs(this);
        currentUserEmail = psp.getCurrentUser();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParkingListActivity.this, AddNewParking.class);
                startActivity(intent);
            }
        });
    }
}