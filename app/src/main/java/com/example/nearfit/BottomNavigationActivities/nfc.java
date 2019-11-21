package com.example.nearfit.BottomNavigationActivities;

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

import com.example.nearfit.MainActivity;
import com.example.nearfit.R;
import com.example.nearfit.SessionManager;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.charset.Charset;

import static android.nfc.NdefRecord.createMime;

public class nfc extends Fragment {
    public String username;
    public String password;
    NfcAdapter nfcAdapter;
    TextView textView;
    Intent intent;
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
        final TextView textView = root.findViewById(R.id.textView);
        button = root.findViewById(R.id.transfer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), nfcTransfer.class);
                i.putExtra("username", username);
                i.putExtra("password", password);
                startActivity(i);


            }
        });



        return root;
    }


}




