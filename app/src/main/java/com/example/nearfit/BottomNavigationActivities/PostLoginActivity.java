package com.example.nearfit.BottomNavigationActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nearfit.BottomNavigationActivities.Home_nfc.nfc;
import com.example.nearfit.Settings.Impostazioni;
import com.example.nearfit.R;
import com.example.nearfit.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashMap;

public class PostLoginActivity extends AppCompatActivity {
    ActionBar actionBar;
    SessionManager sessionManager;
    BottomNavigationView bottomNav;
    String mUser, mPassword;
    HashMap<String,String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        //Verifica utente già loggato
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        //Acquisisco dalla classe SeessionManager le credenziali
        user = sessionManager.getUserDetail();
        mUser = user.get(sessionManager.USERNAME);
        mPassword = user.get(sessionManager.PASSWORD);

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

    //Gestione del Bottom Navigation menu
    protected BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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

    //Imposto il testo della action bar
    public void setTextActionBar (String text){
        actionBar = getSupportActionBar();
        actionBar.setTitle(text);
    }

    //Imposto colore della action bar
    public void setColorActionBar (String color){
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }

}
