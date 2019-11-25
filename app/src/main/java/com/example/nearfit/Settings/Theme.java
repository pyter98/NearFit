package com.example.nearfit.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.nearfit.R;

public class Theme extends AppCompatActivity {
    private ActionBar actionBar;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = new SharedPref(this);

                sharedPref.setTheme(true);  if(sharedPref.loadGabrieleState() == false){
            setTheme(R.style.Gabriele);
        }
        else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_theme);

        actionBar=getSupportActionBar();
        actionBar.hide();

        Button piero = findViewById(R.id.piero);
        Button gabriele = findViewById(R.id.gabriele);
        Button ernesto = findViewById(R.id.ernesto);
        Button riccardo = findViewById(R.id.riccardo);
        gabriele.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref.setTheme(true);
                restartApp();
            }
        });
    }

    /*public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {

            case R.id.gabriele:
                restartApp();
                break;
            case R.id.piero:
                ThemeUtils.changeToTheme(this,  ThemeUtils.Piero);
                break;
            case R.id.ernesto:
                ThemeUtils.changeToTheme(this,  ThemeUtils.Ernesto);
                break;
            case R.id.riccardo:
                ThemeUtils.changeToTheme(this,  ThemeUtils.Riccardo);
                break;
        }
    }*/

    public void restartApp(){
        Intent i =new Intent(getApplicationContext(), Theme.class);
        startActivity(i);
        finish();
    }
}