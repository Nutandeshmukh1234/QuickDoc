package com.example.quickdoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNevigationView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNevigationView = findViewById(R.id.homeBottomNev);
        bottomNevigationView.setOnNavigationItemSelectedListener(HomeActivity.this);
        bottomNevigationView.setSelectedItemId(R.id.itemHome);

        preferences= PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor=preferences.edit();

        boolean firsttime = preferences.getBoolean("isFirstTime",true);
        if(firsttime)
        {
            welcome();
        }


    }

    private void welcome() {
            AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
            ab.setTitle("QuickDoc");
            ab.setMessage("Welcome a QuickDoc");
            ab.setPositiveButton("Thank you", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create().show();

            editor.putBoolean("isFirstTime",false).commit();

        }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuAboutus) {
            Intent i = new Intent(HomeActivity.this, AboutUsActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.menuSetting) {
            Intent i = new Intent(HomeActivity.this,SettingActivity.class);
            startActivity(i);

        } else if (item.getItemId()==R.id.menulogout) {
            logout();


        }
        return true;
    }

    private void logout() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Become A QuickDoc Member");
        ad.setMessage("Are You Sure You Want To Logout");
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                editor.putBoolean("isLogin",false).commit();
                startActivity(i);
            }
        }).create().show();

    }


    HomeFragment homeFragment = new HomeFragment();
    DoctorFragment doctorkFragment = new DoctorFragment();
    MyProfileFragment myProfileFragment = new MyProfileFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.itemHome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, homeFragment).commit();

        } else if (item.getItemId() == R.id.itemDoctor) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, doctorkFragment).commit();

        } else if (item.getItemId() == R.id.itemMyProfile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, myProfileFragment).commit();
        }
        return true;
    }
}


