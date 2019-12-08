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
    private String items[] = new String[] {"Account", "Info"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#232f3e")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setTitle("Impostazioni");

        ListView listView = findViewById(R.id.lista);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items[position].equals("Account")){

                    Intent i = new Intent(Impostazioni.this, EditUser.class);
                    startActivity(i);
                }
                if (items[position].equals("Info")){

                    Intent i = new Intent(Impostazioni.this, Info.class);
                    startActivity(i);
                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        this.finish();
        return true;
    }
}
