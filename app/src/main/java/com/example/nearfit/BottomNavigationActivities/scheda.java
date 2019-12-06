package com.example.nearfit.BottomNavigationActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nearfit.R;
import com.example.nearfit.SessionManager;
import com.example.nearfit.Settings.EditUser;
import com.example.nearfit.Settings.Impostazioni;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class scheda extends Fragment implements AdapterView.OnItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private Spinner spinner;
    protected SessionManager sessionManager;
    protected HashMap<String, String> u;
    private String userAct, pswAct, id;
    private List<String> spinnerArray;
    private String v;
    private String text;
    private static String URL_SCHEDA = "https://nearfit.altervista.org/fitness2/seleziona_giorno.php";


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.scheda, container, false);
        ((PostLoginActivity) getActivity()).setTextActionBar("Scheda");
        spinner = root.findViewById(R.id.spinner);

        sessionManager = new SessionManager(getContext());
        u = sessionManager.getUserDetail();
        userAct = u.get(sessionManager.USERNAME);
        pswAct = u.get(sessionManager.PASSWORD);
        id = u.get(sessionManager.ID);
        v = ((PostLoginActivity) getActivity()).getDays();
        spinnerArray = new ArrayList<String>();
        int i = Integer.parseInt(v);
        for (int n = 1; n <= i; n++) {
            spinnerArray.add("Giorno " + n);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);



        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        if (text.equals("Giorno 1")) {
            setText(text);
            setTable();
            Toast.makeText(parent.getContext(), "Porco", Toast.LENGTH_SHORT).show();


        }
        if (text.equals("Giorno 2")) {
            setTable();


        }
        if (text.equals("Giorno 3")) {
            //
        }
        if (text.equals("Giorno 4")) {
            //
        }
        if (text.equals("Giorno 5")) {
            //
        }
        if (text.equals("Giorno 6")) {
            //
        }
        if (text.equals("Giorno 7")) {
            //
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setTable() {
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
                                    String[] esercizi = new String[jsonArray.length()];
                                    String[] ripetizioni = new String[jsonArray.length()];
                                    String[] recupero = new String[jsonArray.length()];
                                    String[] metodologia = new String[jsonArray.length()];
                                    String[] serie = new String[jsonArray.length()];


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


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Error "+ error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("user_name", userAct);
                    params.put("password", pswAct);
                    params.put("ID",id);
                    params.put("giorno",getText());
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);

        }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}


