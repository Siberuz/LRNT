package com.example.lrnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    Dashboard dashboardFragment =  new Dashboard();
    Course courseFragment = new Course();
    Profile profileFragment = new Profile();
    Ranks ranksFragment = new Ranks();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_bottom);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, dashboardFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.dashboard_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, dashboardFragment).commit();
                    return true;
                }
                if(id == R.id.course_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, courseFragment).commit();
                    return true;
                }
                if(id == R.id.profile_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, profileFragment).commit();
                    return true;
                }
                if(id == R.id.ranks_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ranksFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }

}