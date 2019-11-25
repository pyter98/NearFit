package com.example.nearfit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class Theme extends AppCompatActivity implements OnClickListener {
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_theme);

        Button piero;
        findViewById(R.id.piero).setOnClickListener(this);
        Button gabriele;
        findViewById(R.id.gabriele).setOnClickListener(this);
        Button ernesto;
        findViewById(R.id.ernesto).setOnClickListener(this);
        Button riccardo;
        findViewById(R.id.riccardo).setOnClickListener(this);
    }
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
        switch (v.getId())
        {

            case R.id.gabriele:
                ThemeUtils.changeToTheme(this, ThemeUtils.Gabriele);
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
    }
}