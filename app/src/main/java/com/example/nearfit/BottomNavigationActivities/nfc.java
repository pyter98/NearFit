package com.example.nearfit.BottomNavigationActivities;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.fragment.app.Fragment;

import com.example.nearfit.Biometric.BiometricCallback;
import com.example.nearfit.Biometric.BiometricManager;

import com.example.nearfit.BottomNavigationActivities.PostLoginActivity;


import com.example.nearfit.OnSwipeTouchListener;
import com.example.nearfit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.content.Intent;
import android.widget.Toast;

import java.util.concurrent.Executor;


public class nfc extends Fragment implements BiometricCallback {
    public String username;
    public String password;
    Button button;
    BiometricManager mBiometricManager;

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
            public void onClick(View view) {

                /*
                 *
                 * */
                mBiometricManager = new BiometricManager.BiometricBuilder(getContext())
                        .setTitle(getString(R.string.biometric_title))
                        .setSubtitle(getString(R.string.biometric_subtitle))
                        .setDescription(getString(R.string.biometric_description))
                        .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                        .build();

                //start authentication
                mBiometricManager.authenticate(nfc.this);
            }
        });




//        gesture(root);

/*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), nfcTransfer.class);
                i.putExtra("username", username);
                i.putExtra("password", password);
                startActivity(i);
                //getActivity().finish();
            }
        });*/

        ((PostLoginActivity) getActivity()).setTextActionBar("Home Page");


        return root;
    }
    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        nfcTransfer();
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        nfcTransfer();
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        nfcTransfer();
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getActivity().getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
//        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
        mBiometricManager.cancelAuthentication();
    }

    @Override
    public void onAuthenticationSuccessful() {

        nfcTransfer();
        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
//        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }

    protected void nfcTransfer(){
        Intent i = new Intent(getActivity(), nfcTransfer.class);
        i.putExtra("username", username);
        i.putExtra("password", password);
        startActivity(i);
    }

}




