package com.example.nearfit.BottomNavigationActivities.Home_nfc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearfit.BottomNavigationActivities.PostLoginActivity;
import com.example.nearfit.R;

import static android.nfc.NdefRecord.createMime;

public class NfcTransfer extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter nfcAdapter;
    String username, password;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfctransfer);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#232f3e")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        TextView textView = findViewById(R.id.user);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //Controllo se il dispositivo supporta tecnologia NFC
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }



        //Acquisizione delle credenziali dalla classe nfc()
        bundle = getIntent().getExtras();
        username= bundle.getString("username");
        password=bundle.getString("password");

        textView.append(" "+username);

        // Callback
        nfcAdapter.getDefaultAdapter(this);
        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    /*@Override
    public boolean onSupportNavigateUp(){
        this.finish();
        return true;
    }*/

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String text = username;
        String text2 = password;
        String res = text + " "+ text2;
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMime(
                        "application/vnd.com.example.nearfit.BottomNavigationActivities.Home_nfc.NfcTransfer", res.getBytes())

                });

        return msg;
    }
/*
    @Override
    public void onResume() {
        super.onResume();
    }*/
}
