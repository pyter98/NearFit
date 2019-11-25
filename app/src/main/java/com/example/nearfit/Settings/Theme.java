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
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        actionBar=getSupportActionBar();
        actionBar.hide();

        Button piero = findViewById(R.id.piero);
        Button gabriele = findViewById(R.id.gabriele);
        Button ernesto = findViewById(R.id.ernesto);
        Button riccardo = findViewById(R.id.riccardo);
    }

    public void restartApp(){
        Intent i =new Intent(getApplicationContext(), Theme.class);
        startActivity(i);
        finish();
    }
}