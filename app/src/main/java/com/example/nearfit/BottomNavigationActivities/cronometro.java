package com.example.nearfit.BottomNavigationActivities;

import android.app.ProgressDialog;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
        final ProgressBar progressBar = view.findViewById(R.id.pr_crono);
        final RotateDrawable rotateDrawable = (RotateDrawable) progressBar.getIndeterminateDrawable();
        stop.hide();

       // if (progressBar != null) {
        //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                rotateDrawable.setToDegrees(270);
           // }
        //}

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
                stop.show();
                riprendi.hide();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopWatch();
                stop.hide();
                riprendi.show();
                rotateDrawable.setToDegrees(rotateDrawable.getFromDegrees());

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWatch();
                stop.hide();
                start.show();
                clear.hide();
                riprendi.hide();
                rotateDrawable.setToDegrees(270);

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