package com.example.nearfit.BottomNavigationActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nearfit.BottomNavigationActivities.Home_nfc.nfc;
import com.example.nearfit.Settings.Impostazioni;
import com.example.nearfit.R;
import com.example.nearfit.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostLoginActivity extends AppCompatActivity {
    private ActionBar actionBar;
    protected SessionManager sessionManager;
    private BottomNavigationView bottomNav;
    protected String mUser, mPassword, id, mName;
    protected HashMap<String,String> user;
    private long backPressedTime;
    private String days;
    private static String URL_GIORNO = "https://nearfit.altervista.org/fitness2/infoscheda.php";
    private static String URL_SCHEDA = "https://nearfit.altervista.org/fitness2/seleziona_giorno.php";
    private String[] esercizi, ripetizioni, recupero, metodologia, serie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        //Verifica utente già loggato
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();


        //Acquisisco dalla classe SeessionManager le credenziali
        user = sessionManager.getUserDetail();
        mUser = user.get(sessionManager.USERNAME);
        mPassword = user.get(sessionManager.PASSWORD);
        id = user.get(sessionManager.ID);
        mName = user.get(sessionManager.NAME);

        setColorActionBar("#232f3e");

        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);



        //Lancio il primo fragment(nfc)
        if (savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, new nfc(mUser,mPassword,mName));
            fragmentTransaction.commit();

        }
        days = ImpostaGiorni();
        //setTable();
    }

    //Gestione del Bottom Navigation menu
    protected BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            selectedFragment = new nfc(mUser,mPassword, mName);

                            break;
                        case R.id.scheda:
                            selectedFragment = new scheda(/*esercizi,ripetizioni,recupero,metodologia,serie*/);
                            break;
                        case R.id.cronometro:
                            selectedFragment = new cronometro();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

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
                showPopup();
                return true;
            case R.id.impostazioni:
                Intent i = new Intent(PostLoginActivity.this, Impostazioni.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    //Imposto il testo della action bar
    public void setTextActionBar (String text){
        actionBar = getSupportActionBar();
        actionBar.setTitle(text);
    }

    //Imposto colore della action bar
    public void setColorActionBar (String color){
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else {
            Toast.makeText(PostLoginActivity.this, "Premi ancora per uscire",Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    protected String ImpostaGiorni() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GIORNO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            String g = jsonObject.getString("giorni");
                            //Log.e("PARAMETRI", jsonObject.toString());
                            if (success.equals("1")) {
                                setDays(g);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PostLoginActivity.this, "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostLoginActivity.this, "Error "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_name", mUser);
                params.put("password", mPassword);
                params.put("ID",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PostLoginActivity.this);
        requestQueue.add(stringRequest);
        return days;
    }

    /*private void setTable() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SCHEDA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("giorno");
                            //Log.e("PARAMETRI", jsonObject.toString());
                            if (success.equals("1")) {
                                String ese, rip, recup, met, ser;
                                esercizi = new String[jsonArray.length()];
                                ripetizioni = new String[jsonArray.length()];
                                recupero = new String[jsonArray.length()];
                                metodologia = new String[jsonArray.length()];
                                serie = new String[jsonArray.length()];


                                for (int i = 0;i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    ese = object.getString("esercizio").trim();
                                    rip = object.getString("ripetizioni").trim();
                                    recup = object.getString("recupero").trim();
                                    met = object.getString("metodologia").trim();
                                    ser = object.getString("serie").trim();
                                    esercizi[i] = ese;
                                    ripetizioni[i] = rip;
                                    recupero[i]=recup;
                                    metodologia[i]=met;
                                    serie[i]=ser;


                                }
                                //setRowsTable(esercizi,ripetizioni,recupero,metodologia,serie);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PostLoginActivity.this, "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostLoginActivity.this, "Error "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_name", mUser);
                params.put("password", mPassword);
                params.put("ID",id);
                params.put("giorno","Giorno "+"1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PostLoginActivity.this);
        requestQueue.add(stringRequest);

    }*/

    public void setDays(String days) {
        this.days = days;
    }

    public String getDays() {
        nGiorni(days);
        return days;
    }

    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(PostLoginActivity.this);
        alert.setIcon(R.drawable.ic_logout).setTitle("Logout").setMessage("Sei sicuro di voler uscire?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        logout(); // Last step. Logout function

                    }
                }).setNegativeButton("No", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void logout() {
        sessionManager.logout();
        Toast.makeText(PostLoginActivity.this, "Logout...",Toast.LENGTH_SHORT).show();

    }

    public String nGiorni(String n){
        return ("Giorni "+ n);
    }
}

