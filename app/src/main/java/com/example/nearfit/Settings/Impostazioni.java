package com.example.nearfit.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.nearfit.R;

public class Impostazioni extends AppCompatActivity {
    ActionBar actionBar;
    String items[] = new String[] {"Account", "Info"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#232f3e")));
        actionBar.setTitle("Impostazioni");
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("232f3e")));


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
                if (items[position].equals("Info")){
                    Intent i = new Intent(Impostazioni.this, Info.class);
                    startActivity(i);
                    //Toast.makeText(Impostazioni.this, "Info", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
