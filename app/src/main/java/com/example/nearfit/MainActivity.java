package com.example.nearfit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nearfit.BottomNavigationActivities.PostLoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button btn_login;
    private ProgressBar loading;
    private static String URL_LOGIN = "https://nearfit.altervista.org/fitness2/Prova_loginApp.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sessionManager = new SessionManager(this);


        //Verifico sessione attiva
        if (sessionManager.isLoggin()){
            Intent intent = new Intent(MainActivity.this, PostLoginActivity.class);
            intent.putExtra("nfc", R.id.home);
            startActivity(intent);
            MainActivity.this.finish();
        }

        loading = findViewById(R.id.loading);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mUsername.isEmpty() || !mPassword.isEmpty()){
                    Login(mUsername,mPassword);

                }
                else {
                    username.setError("Perfavore, inserisci username");
                    password.setError("Perfavore inserisci password");
                }
            }
        });


    }

    //Effettuo il login facendo il check delle credenziali sul DB
    private void Login(final String username, final String password){

        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String name = jsonObject.getString("name");
                            String user = jsonObject.getString("username");
                            String ID = jsonObject.getString("ID");
                            String password = jsonObject.getString("password");

                            if (success.equals("1")) {

                                sessionManager.createSession(name,user,ID,password);

                                Intent intent = new Intent(MainActivity.this, PostLoginActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("username", user);
                                startActivity(intent);
                                MainActivity.this.finish();
                                btn_login.setVisibility(View.VISIBLE);
                            }
                            else {
                                Toast.makeText(MainActivity.this,
                                        "Username o password errati ",Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Errore "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Errore "+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_name", username);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
