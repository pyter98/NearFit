package com.example.nearfit.BottomNavigationActivities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nearfit.OnSwipeTouchListener;
import com.example.nearfit.Settings.Impostazioni;
import com.example.nearfit.R;
import com.example.nearfit.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashMap;
import java.util.concurrent.Executor;

//TODO 160799; 089820; 980206; 9810FE
public class PostLoginActivity extends AppCompatActivity {
    ActionBar actionBar;
    SessionManager sessionManager;
    BottomNavigationView bottomNav;
    private final Executor executor = ContextCompat.getMainExecutor(this);
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        //ActionBar actionBar = getSupportActionBar();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String,String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        String mUser = user.get(sessionManager.USERNAME);
        String mPassword = user.get(sessionManager.PASSWORD);

        //actionBar.setTitle("Bentornato, "+mName);
        //setTextActionBar("Bentornato "+ mName);
        //actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary2)));
        setColorActionBar("#232f3e");

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        //Lancio il primo fragment(nfc)
        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new nfc(mUser,mPassword));
            fragmentTransaction.commit();

        }
    }

    protected BottomNavigationView.OnNavigationItemSelectedListener navListener =
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
                Intent i = new Intent(PostLoginActivity.this, Impostazioni.class);
                startActivity(i);
                //Toast.makeText(PostLoginActivity.this, "Impostazioni",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void setTextActionBar (String text){
        actionBar = getSupportActionBar();
        actionBar.setTitle(text);
    }

    public void setColorActionBar (String color){
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }

    public void updateNavigationBarState(int actionId){
        Menu menu = bottomNav.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    protected String[] getUserDetail() {

        String[] userdetail = new String[2];
        HashMap<String, String> user = sessionManager.getUserDetail();
        String mUser = user.get(sessionManager.USERNAME);
        String mPassword = user.get(sessionManager.PASSWORD);

        userdetail[0] = mUser;
        userdetail[1] = mPassword;

        return userdetail;

    }

}
