package com.aigentech.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.aigentech.in.fragment.CreateAdFragment;
import com.aigentech.in.fragment.ViewAdFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Ad");


        //loading the default fragment
        loadFragment(new ViewAdFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


        loadFragment(new ViewAdFragment());
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.menuViewAd:
                toolbar.setTitle("View Ad");
                fragment = new ViewAdFragment();
                loadFragment(fragment);
                return true;
            case R.id.menuCreateAd:
                toolbar.setTitle("Create Ad");
                fragment = new CreateAdFragment();
                loadFragment(fragment);
                return true;
            case R.id.menuLogout:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
        }


        return false;
    }
}
