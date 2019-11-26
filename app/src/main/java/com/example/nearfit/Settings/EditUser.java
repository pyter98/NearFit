package com.example.nearfit.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nearfit.R;
import com.example.nearfit.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditUser extends AppCompatActivity {
    SessionManager sessionManager;
    private TextView username, password;
    private Button btnSave;
    private  boolean change = false;
    private static String URL_EDIT = "https://nearfit.altervista.org/fitness2/edit.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Account");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        username = findViewById(R.id.editUser);
        password = findViewById(R.id.editPsw);
        btnSave = findViewById(R.id.btn_edit);

        sessionManager = new SessionManager(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        String mUser = user.get(sessionManager.USERNAME);
        String mPassword = user.get(sessionManager.PASSWORD);

        username.setText(mUser);
        password.setText(mPassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDetail();
            }
        });

    }

    protected void SaveDetail() {

        HashMap<String,String> u = sessionManager.getUserDetail();

        final String user = this.username.getText().toString().trim();
        final String psw = this.password.getText().toString().trim();
        final String userAct = u.get(sessionManager.USERNAME);
        final String pswAct = u.get(sessionManager.PASSWORD);
        final String id = u.get(sessionManager.ID);
        final String name = u.get(sessionManager.NAME);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1") && !(user.equals(userAct)) || !(psw.equals(pswAct))) {

                                Toast.makeText(EditUser.this, "Username e Password modificati correttamente!", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(name, user, id, psw);
                                Intent i = new Intent(EditUser.this, Impostazioni.class);
                                startActivity(i);
                                EditUser.this.finish();
                                change = true;
                            }
                            if (user.equals(userAct) && psw.equals(pswAct) && !change){
                                Toast.makeText(EditUser.this, "Username e Password attualmente in uso", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditUser.this, "Error "+ e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditUser.this, "Error "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_name", user);
                params.put("password", psw);
                params.put("ID",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
