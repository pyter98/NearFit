package com.example.nearfit.BottomNavigationActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import androidx.fragment.app.Fragment;


import com.example.nearfit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.droidsonroids.gif.GifImageView;

public class cronometro extends Fragment{
//TODO Notifica
    Chronometer chronometer;
    private boolean running;
    private long pauseOffset;
    private  FloatingActionButton start, clear, riprendi, stop;
    private GifImageView gif;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cronometro, container, false);
        chronometer = view.findViewById(R.id.simpleChronometer);

        ((PostLoginActivity) getActivity()).setTextActionBar("Cronometro");

        //Inizializzo i bottoni
        start = view.findViewById(R.id.start_btn);
        clear = view.findViewById(R.id.clear_btn);
        riprendi =  view.findViewById(R.id.resume_btn);
        stop = view.findViewById(R.id.stop_btn);

        stop.hide();

        gif = view.findViewById(R.id.gif);

        //Pulsante start
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setTextColor(Color.parseColor("#000000"));
                gif.setVisibility(View.VISIBLE);
                startStopWatch();
                stop.show();
                start.hide();
                clear.show();

            }
        });

        //Pulstante riprendi
        riprendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setTextColor(Color.parseColor("#000000"));
                startStopWatch();
                stop.show();
                riprendi.hide();
                gif.setVisibility(View.VISIBLE);

            }
        });
        //Pulsante stop
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setTextColor(Color.parseColor("#9e0808"));
                stopStopWatch();
                stop.hide();
                riprendi.show();
                gif.setVisibility(View.GONE);

                //rotateDrawable.setToDegrees(rotateDrawable.getFromDegrees());

            }
        });

        //Pulsante azzera
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setTextColor(Color.parseColor("#000000"));
                clearWatch();
                stop.hide();
                start.show();
                clear.hide();
                riprendi.hide();
                gif.setVisibility(View.GONE);
            }
        });
        return view;
    }
    //Metodi per la gestione del cronometro
    private void startStopWatch() {

        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }

    }

    private void stopStopWatch() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }

    }

    private void clearWatch() {
        long startTime = SystemClock.elapsedRealtime();
        chronometer.stop();
        chronometer.setBase(startTime);
        pauseOffset = 0;
        running = false;


    }

}