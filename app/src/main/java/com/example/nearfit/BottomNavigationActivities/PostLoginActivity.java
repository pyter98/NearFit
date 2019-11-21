package com.example.nearfit.BottomNavigationActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nearfit.HomeActivity;
import com.example.nearfit.R;
import com.example.nearfit.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.nearfit.SessionManager;

import java.lang.reflect.Array;
import java.util.HashMap;

public class PostLoginActivity extends AppCompatActivity {
    ActionBar actionBar;
    SessionManager sessionManager;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        ActionBar actionBar = getSupportActionBar();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String,String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);

        actionBar.setTitle("Bentornato, "+mName);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF3F51B5")));

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    HashMap<String,String> user = sessionManager.getUserDetail();
                    String mUser = user.get(sessionManager.USERNAME);
                    String mPassword = user.get(sessionManager.PASSWORD);
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            selectedFragment = new nfc(mUser,mPassword);
                            break;
                        case R.id.scheda:
                            selectedFragment = new scheda();
                            break;
                        case R.id.cronometro:
                            selectedFragment = new cronometro();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                Toast.makeText(PostLoginActivity.this, "Logout...",Toast.LENGTH_SHORT).show();
                sessionManager.logout();
                return true;
            case R.id.impostazioni:
                Toast.makeText(PostLoginActivity.this, "Impostazioni",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
            //return true;

        }
    }

    protected String getUsername() {
        HashMap<String,String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.USERNAME);
        return mName;
    }

    protected String getPassword() {
        HashMap<String,String> user = sessionManager.getUserDetail();
        //String mPasw = user.get(sessionManager.PASSWORD);
        return "1";
    }
}
