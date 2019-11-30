package com.example.nearfit.BottomNavigationActivities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
}