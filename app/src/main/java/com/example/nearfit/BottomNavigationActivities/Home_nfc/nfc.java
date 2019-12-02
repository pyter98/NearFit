package com.example.nearfit.BottomNavigationActivities.Home_nfc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.nearfit.Biometric.BiometricCallback;
import com.example.nearfit.Biometric.BiometricManager;


import com.example.nearfit.BottomNavigationActivities.PostLoginActivity;
import com.example.nearfit.R;

import android.content.Intent;
import android.widget.Toast;


public class nfc extends Fragment implements BiometricCallback {
    public String username;
    public String password;
    Button button;
    BiometricManager mBiometricManager;


    //Costruttore
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

        //Click sul tasto 'ACCEDI IN PALESTRA'
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        Toast.makeText(getActivity().getApplicationContext(), "Accedi in palestra "+ username, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
//        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }

    //Passaggio credenziali alla classe nfcTransfer
    protected void nfcTransfer(){

        Intent i = new Intent(getActivity(), NfcTransfer.class);
        i.putExtra("username", username);
        i.putExtra("password", password);
        startActivity(i);


    }


}




