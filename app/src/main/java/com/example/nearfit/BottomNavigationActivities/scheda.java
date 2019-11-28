package com.example.nearfit.BottomNavigationActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nearfit.OnSwipeTouchListener;
import com.example.nearfit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class scheda extends Fragment {
    BottomNavigationView bottomNavigationView;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.scheda, container, false);
        ((PostLoginActivity) getActivity()).setTextActionBar("Scheda");

        //gesture(root);

        return root;
    }

    /*protected void gesture(View root){
        root.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            String[] userdetail = ((PostLoginActivity) getActivity()).getUserDetail();
            String username = userdetail[0];
            String password = userdetail[1];

            @Override
            public void onSwipeLeft(){
                cronometro cronometro = new cronometro();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, cronometro, "cronometro").addToBackStack(null)
                        .commit();
            }
            @Override
            public void onSwipeRight(){
                nfc nfc = new nfc(username,password);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nfc, "cronometro").addToBackStack(null)
                        .commit();
            }

        });
    }*/

}