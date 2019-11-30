package com.example.nearfit.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearfit.R;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

public class Info extends AppCompatActivity {
    GifImageView gifImageView;
    ImageView imageView;
    TextView piero, ernesto, gabriele, riccardo;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#232f3e")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        imageView = findViewById(R.id.logo);
        gifImageView = findViewById(R.id.egg);
        piero = findViewById(R.id.Piero);
        ernesto = findViewById(R.id.Ernesto);
        gabriele = findViewById(R.id.Gabriele);
        riccardo = findViewById(R.id.Riccardo);


        imageView.setOnClickListener(new View.OnClickListener() {
            int count = 8;
            int n = 0;
            @Override
            public void onClick(View v) {
                count = count-1;
                if (count == n)
                {
                    Toast.makeText(Info.this, "Ops..", Toast.LENGTH_SHORT).show();
                    gifImageView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.GONE);
                    piero.setTextColor(Color.parseColor("#980206"));
                    ernesto.setTextColor(Color.parseColor("#089820"));
                    gabriele.setTextColor(Color.parseColor("#160799"));
                    riccardo.setTextColor(Color.parseColor("#9810FE"));

                    count = 8;
                    refresh();

                }
                if (count == 1){
                    Toast.makeText(Info.this, "Ancora una volta", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }

    public void refresh()
    {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Info.this, Info.class);
                startActivity(intent);
                Info.this.finish();
            }
        },6000);
    }
}
