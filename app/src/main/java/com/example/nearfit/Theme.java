package com.example.nearfit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Theme extends AppCompatActivity {
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        Button piero = findViewById(R.id.piero);
        Button gabriele = findViewById(R.id.gabriele);
        Button ernesto = findViewById(R.id.ernesto);
        Button riccardo = findViewById(R.id.riccardo);

        actionBar = getSupportActionBar();

        gabriele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getApplication().setTheme(R.style.Gabriele);

            }
        });
    }
}
