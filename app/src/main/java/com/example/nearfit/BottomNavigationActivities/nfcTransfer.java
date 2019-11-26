package com.example.nearfit.BottomNavigationActivities;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nearfit.R;

import java.nio.charset.Charset;

import static android.nfc.NdefRecord.createMime;


public class nfcTransfer extends AppCompatActivity implements CreateNdefMessageCallback {
    NfcAdapter nfcAdapter;
    TextView textView;
    String username;
    String password;
    Bundle bundle;
    Button back;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_transfer);

        back = findViewById(R.id.backbtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nfcTransfer.this,PostLoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        TextView textView = findViewById(R.id.user);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        bundle = getIntent().getExtras();
        username= bundle.getString("username");
        password=bundle.getString("password");

        textView.append(" "+username);
        // Register callback
        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String text = username;
        String text2 = password;
        String res = text + " "+ text2;
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMime(
                        "application/vnd.com.example.nearfit.BottomNavigationActivities.nfcTransfer", res.getBytes())

                });

        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}