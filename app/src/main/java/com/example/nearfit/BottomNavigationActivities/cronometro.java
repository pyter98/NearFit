package com.example.nearfit.BottomNavigationActivities;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.fragment.app.Fragment;
import android.widget.Button;


import com.example.nearfit.MainActivity;
import com.example.nearfit.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class cronometro extends Fragment{

    Chronometer chronometer;
    ChronometerHelper chronometerHelper = new ChronometerHelper();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cronometro, container, false);
        chronometer = view.findViewById(R.id.simpleChronometer);

        final FloatingActionButton start = (FloatingActionButton) view.findViewById(R.id.start_btn);
        final FloatingActionButton clear = view.findViewById(R.id.clear_btn);
        final FloatingActionButton riprendi = (FloatingActionButton) view.findViewById(R.id.resume_btn);
        final FloatingActionButton stop = (FloatingActionButton) view.findViewById(R.id.stop_btn);
        stop.hide();
        //start.show();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopWatch();

                stop.show();
                start.hide();
                clear.show();
            }
        });

        riprendi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopWatch();
                //stop.setVisibility(view.VISIBLE);
                //riprendi.setVisibility(view.GONE);
                stop.show();
                riprendi.hide();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopWatch();
                //stop.setVisibility(view.GONE);
                //riprendi.setVisibility(view.VISIBLE);
                stop.hide();
                riprendi.show();
            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWatch();
                //start.setVisibility(view.VISIBLE);
                //clear.setVisibility(view.GONE);
                //stop.setVisibility(view.GONE);
                //riprendi.setVisibility(view.GONE);
                stop.hide();
                start.show();
                clear.hide();
                riprendi.hide();

            }
        });
        return view;
    }

    private void startStopWatch() {
        if (chronometerHelper.getStartTime() == null) {
            // If the start date is not defined, set it.
            long startTime = SystemClock.elapsedRealtime();
            chronometerHelper.setStartTime(startTime);
            chronometer.setBase(startTime);
        } else {
            // Otherwise set the chronometer's base to the original
            // starting time.
            chronometer.setBase(chronometerHelper.getStartTime());

        }

        chronometer.start();

    }

    private void stopStopWatch() {

        chronometer.stop();

    }

    private void clearWatch() {
        long startTime = SystemClock.elapsedRealtime();
        chronometerHelper.setStartTime(startTime);
        chronometer.setBase(startTime);
        chronometer.stop();
    }

}