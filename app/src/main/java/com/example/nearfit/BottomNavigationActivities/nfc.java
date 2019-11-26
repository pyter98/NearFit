package com.example.nearfit.BottomNavigationActivities;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.fragment.app.Fragment;
import com.example.nearfit.BottomNavigationActivities.PostLoginActivity;


import com.example.nearfit.R;

import android.content.Intent;


public class nfc extends Fragment {
    public String username;
    public String password;
    Button button;
    public nfc(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.nfc, container, false);
        button = root.findViewById(R.id.transfer);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), nfcTransfer.class);
                i.putExtra("username", username);
                i.putExtra("password", password);
                startActivity(i);
                //getActivity().finish();
            }
        });

        ((PostLoginActivity) getActivity()).setTextActionBar("Home Page");


        return root;
    }


}




