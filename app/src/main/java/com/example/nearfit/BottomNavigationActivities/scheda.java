package com.example.nearfit.BottomNavigationActivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class scheda extends Fragment {
    BottomNavigationView bottomNavigationView;
    private Spinner spinner;
    protected SessionManager sessionManager;
    protected HashMap<String,String> u;
    private String userAct, pswAct, id;
    private List<String> spinnerArray;
    private String v;
    private static String URL_SCHEDA = "https://nearfit.altervista.org/fitness2/infoscheda.php";
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.scheda, container, false);
        ((PostLoginActivity) getActivity()).setTextActionBar("Scheda");
        spinner=root.findViewById(R.id.spinner);

        sessionManager = new SessionManager(getContext());
        u = sessionManager.getUserDetail();
        userAct = u.get(sessionManager.USERNAME);
        pswAct = u.get(sessionManager.PASSWORD);
        id = u.get(sessionManager.ID);
        v = ((PostLoginActivity) getActivity()).ImpostaGiorni();
        spinnerArray =  new ArrayList<String>();
        int i = Integer.parseInt(v);
        for (int n=1;n<=i;n++){
            spinnerArray.add("Giorno "+n);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return root;
    }
}