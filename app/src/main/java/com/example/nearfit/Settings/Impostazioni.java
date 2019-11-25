package com.example.nearfit.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nearfit.R;

public class Impostazioni extends AppCompatActivity {

    String items[] = new String[] {"Account", "Tema", "Info"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ListView listView = findViewById(R.id.lista);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Impostazioni.this, items[position], Toast.LENGTH_SHORT).show();
                if (items[position].equals("Account")){
                    Intent i = new Intent(Impostazioni.this, EditUser.class);
                    startActivity(i);
                    //Toast.makeText(Impostazioni.this, "Account", Toast.LENGTH_SHORT).show();
                }
                if (items[position].equals("Tema")){
                    Intent i = new Intent(Impostazioni.this, Theme.class);
                    startActivity(i);
                    //Toast.makeText(Impostazioni.this, "Tema", Toast.LENGTH_SHORT).show();
                }
                if (items[position].equals("Info")){
                    Toast.makeText(Impostazioni.this, "Info", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
