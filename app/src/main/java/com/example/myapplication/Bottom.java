package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bottom extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);

        loadFragment(new HomeFragment());
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.home_menu:
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.history_menu:
                Toast.makeText(this, "histori", Toast.LENGTH_SHORT).show();
                break;
            case R.id.support_menu:
                Toast.makeText(this, "Support", Toast.LENGTH_SHORT).show();
                break;
            case R.id.account_menu:
                Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
                break;
        }
        return loadFragment(fragment);
    }
}