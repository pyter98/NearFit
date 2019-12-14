package com.example.nearfit.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.example.nearfit.BottomNavigationActivities.PostLoginActivity;
import com.example.nearfit.BottomNavigationActivities.SessionScheda;
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
    HashMap<String,String> u;
    String userAct, pswAct, id, name;
    PostLoginActivity postLoginActivity;
    String days;
    protected HashMap<String, String> giorno;
    protected SessionScheda sessionScheda;
    private int[] giorno_selezionato;
    private int i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Set Action bar
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#232f3e")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        getSupportActionBar().setTitle("Modifica Credenziali");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        username = findViewById(R.id.editUser);
        password = findViewById(R.id.editPsw);
        btnSave = findViewById(R.id.btn_edit);



        //Acquisisco dati utente dalla classe SessionManager()
        sessionManager = new SessionManager(this);
        u = sessionManager.getUserDetail();
        userAct = u.get(sessionManager.USERNAME);
        pswAct = u.get(sessionManager.PASSWORD);
        id = u.get(sessionManager.ID);
        name = u.get(sessionManager.NAME);

        username.setText(userAct);
        password.setText(pswAct);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveDetail();

            }
        });

    }
    @Override
    public boolean onSupportNavigateUp(){
        this.finish();
        return true;
    }

    //Salvo le nuove credenziali dell'utente
    protected void SaveDetail() {

        final String new_user = this.username.getText().toString().trim();
        final String new_psw = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1") && !(new_user.equals(userAct)) || !(new_psw.equals(pswAct))) {

                                if (!(new_user.equals("")) && !(new_psw.equals(""))) {

                                    if (new_psw.length()>=8){
                                        //sessionScheda = new SessionScheda(getApplicationContext());

                                        //Toast.makeText(EditUser.this, "Username e/o Password modificati correttamente!", Toast.LENGTH_SHORT).show();
                                        //giorno = sessionScheda.getScheda();
                                        //days = giorno.get(sessionScheda.GIORNO);
                                        //i=Integer.parseInt(days);
                                        //giorno_selezionato = new int[i];
                                        /*for (int x = 0; x < i; x++) {
                                            giorno_selezionato[x] = x + 1;
                                        }
                                        sessionScheda.setSelezione(giorno_selezionato);*/
                                        sessionManager.createSession(name, new_user, id, new_psw);
                                        Intent i = new Intent(EditUser.this, Impostazioni.class);
                                        startActivity(i);
                                        EditUser.this.finish();
                                    }

                                    else Toast.makeText(EditUser.this, "La password deve essere almeno di 8 caratteri", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(EditUser.this, "Campi vuoti, impossibile aggiornare credenziali", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(EditUser.this, "Username già in uso", Toast.LENGTH_SHORT).show();

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
                params.put("user_name", new_user);
                params.put("password", new_psw);
                params.put("ID",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
