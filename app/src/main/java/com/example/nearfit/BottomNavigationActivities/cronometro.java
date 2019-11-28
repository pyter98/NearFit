package com.example.nearfit.BottomNavigationActivities;

import android.app.ProgressDialog;
import android.graphics.drawable.RotateDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.example.nearfit.OnSwipeTouchListener;
import com.example.nearfit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class cronometro extends Fragment{
//TODO Notifica
    Chronometer chronometer;
    ChronometerHelper chronometerHelper = new ChronometerHelper();
    private boolean running;
    private long pauseOffset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cronometro, container, false);
        chronometer = view.findViewById(R.id.simpleChronometer);

        ((PostLoginActivity) getActivity()).setTextActionBar("Cronometro");

        final FloatingActionButton start = (FloatingActionButton) view.findViewById(R.id.start_btn);
        final FloatingActionButton clear = view.findViewById(R.id.clear_btn);
        final FloatingActionButton riprendi = (FloatingActionButton) view.findViewById(R.id.resume_btn);
        final FloatingActionButton stop = (FloatingActionButton) view.findViewById(R.id.stop_btn);
        //final ProgressBar progressBar = view.findViewById(R.id.pr_crono);
        //final RotateDrawable rotateDrawable = (RotateDrawable) progressBar.getIndeterminateDrawable();
//        gesture(view);
        stop.hide();
        //progressBar.stopNestedScroll();
        //rotateDrawable.setToDegrees(270);

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
                //rotateDrawable.setToDegrees(rotateDrawable.getFromDegrees());

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
                //rotateDrawable.setToDegrees(270);

            }
        });
        return view;
    }

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
        chronometerHelper.setStartTime(startTime);
        chronometer.setBase(startTime);
        pauseOffset = 0;

    }
/*
    protected void gesture(final View root){

        root.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            String[] userdetail = ((PostLoginActivity) getActivity()).getUserDetail();
            String username = userdetail[0];
            String password = userdetail[1];

            @Override
            public void onSwipeLeft(){
                nfc nfc = new nfc(username,password);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nfc, "cronometro").addToBackStack(null)
                        .commit();

            }
            @Override
            public void onSwipeRight(){
                scheda scheda = new scheda();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, scheda, "scheda").addToBackStack(null)
                        .commit();
            }

        });
    }*/


}