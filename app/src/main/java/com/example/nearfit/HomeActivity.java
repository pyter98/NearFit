package com.example.nearfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private TextView name, username;
    private Button btn_logout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();


        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        btn_logout = findViewById(R.id.btn_logout);

        HashMap<String,String> user = sessionManager.getUserDetail();
        String mName = user.get(sessionManager.NAME);
        String mUser = user.get(sessionManager.USERNAME);

        name.setText(mName);
        username.setText(mUser);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
    }
        /** TEST MENU **/
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_actions, menu);
            return true;

        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            switch (id){
                case R.id.action_logout:
                    //Toast.makeText(HomeActivity.this, "Logout...",Toast.LENGTH_LONG);
                    //sessionManager.logout();
                    name.setText("Ciao");
                    return true;
                default:
                    Toast.makeText(HomeActivity.this, "Logout...",Toast.LENGTH_LONG);
                    return super.onOptionsItemSelected(item);
                //return true;

            }
        }
}
