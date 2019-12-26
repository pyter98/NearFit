package com.example.nearfit.BottomNavigationActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.example.nearfit.Widget.MaterialProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class scheda extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    protected SessionManager sessionManager;
    protected HashMap<String, String> u;
    private String userAct, pswAct, id, days, text, gio_sel;
    private List<String> spinnerArray;
    private TableLayout table;
    private TableRow tr;
    private TextView r1, r2, r3, r4, r5, messaggio;
    private static String URL_SCHEDA = "https://nearfit.altervista.org/fitness2/seleziona_giorno.php";
    private MaterialProgressBar materialProgressBar;
    protected SessionScheda sessionScheda;
    private int[] giorno_selezionato;
    protected HashMap<String, String> giorno;
    private String[] ese, rip, recup, met, ser, sel;
    private int size;
    private boolean check;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.scheda, container, false);
        ((PostLoginActivity) getActivity()).setTextActionBar("Scheda");

        messaggio = root.findViewById(R.id.text_giorno);
        spinner = root.findViewById(R.id.spinner);
        materialProgressBar = root.findViewById(R.id.progress);
        sessionManager = new SessionManager(getContext());
        u = sessionManager.getUserDetail();
        userAct = u.get(sessionManager.USERNAME);
        pswAct = u.get(sessionManager.PASSWORD);
        id = u.get(sessionManager.ID);

        days = ((PostLoginActivity) getActivity()).getDays();


        spinnerArray = new ArrayList<String>();
        int i = Integer.parseInt(days);
        spinnerArray.add("Seleziona Giorno");

        for (int n = 1; n <= i; n++) {
            spinnerArray.add("Giorno " + n);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), R.layout.color_spinner_first, spinnerArray) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) return false;
                else return true;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        sessionScheda = new SessionScheda(getContext());
        giorno = sessionScheda.getScheda();
        gio_sel = giorno.get(sessionScheda.GIORNO);
        if (gio_sel == null) {
            messaggio.setVisibility(View.VISIBLE);
            giorno_selezionato = new int[i];
            for (int x = 0; x < i; x++) {
                giorno_selezionato[x] = x + 1;
            }
            sessionScheda.setSelezione(giorno_selezionato);
            Log.i("sessione", "null");
            sessionScheda.setScheda("0", ese, rip, recup, met, ser);
        } else {

            if (!(gio_sel.equals("0"))) {
                if (sessionScheda.isSet()) {
                    String s;
                    s = giorno.get(sessionScheda.SELEZIONE);
                    sel = s.split(",");
                    creaArrayGiorni(sel);

                    /*s = giorno.get(sessionScheda.CONNESSIONE);
                    conn = s.split(",");
                    creaArrayConnessione(conn);*/
                    spinner.setSelection(Integer.parseInt(gio_sel));

                    Rimuovi(Integer.parseInt(gio_sel));
                    Log.i("sessione", gio_sel);
                }
            }
            //messaggio.setVisibility(View.VISIBLE);
        }


        table = root.findViewById(R.id.schedaTab);
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);
        table.setColumnStretchable(3, true);
        table.setColumnStretchable(4, true);

        //clearTable();
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        text = parent.getItemAtPosition(position).toString();
        String pos = Integer.toString(position);
        clearTable();
        if (position > 0) {
            messaggio.setVisibility(View.GONE);
            table.setVisibility(View.VISIBLE);
            materialProgressBar.setVisibility(View.VISIBLE);

            if (giorno_selezionato == null) {
                int i = Integer.parseInt(days);
                giorno_selezionato = new int[i];
                for (int x = 0; x < i; x++) {
                    giorno_selezionato[x] = x + 1;
                }
                sessionScheda.setSelezione(giorno_selezionato);
                sessionScheda.setScheda("0", ese, rip, recup, met, ser);
                setTable(pos);
            }
            else {

                for (int c = 0; c < giorno_selezionato.length; c++) {
                    if (giorno_selezionato[c] == position)
                        check = true;
                }
                if (check) {
                    setTable(pos);
                    Toast.makeText(getContext(), "Connetto...", Toast.LENGTH_SHORT).show();
                } else {

                    String e, r, ri, m, se;
                    e = giorno.get(sessionScheda.ESERCIZI);
                    r = giorno.get(sessionScheda.RECUPERO);
                    ri = giorno.get(sessionScheda.RIPETIZIONI);
                    m = giorno.get(sessionScheda.METODOLOGIA);
                    se = giorno.get(sessionScheda.SERIE);
                    ese = e.split(",");
                    recup = r.split(",");
                    rip = ri.split(",");
                    met = m.split(",");
                    ser = se.split(",");

                    setRowsTable(ese, rip, recup, met, ser);
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setTable(final String giorno) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SCHEDA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("giorno");
                            if (success.equals("1")) {
                                String ese, rip, recup, met, ser;
                                String[] esercizi = new String[jsonArray.length()];
                                String[] ripetizioni = new String[jsonArray.length()];
                                String[] recupero = new String[jsonArray.length()];
                                String[] metodologia = new String[jsonArray.length()];
                                String[] serie = new String[jsonArray.length()];


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    ese = object.getString("esercizio").trim();
                                    rip = object.getString("ripetizioni").trim();
                                    recup = object.getString("recupero").trim();
                                    met = object.getString("metodologia").trim();
                                    ser = object.getString("serie").trim();
                                    esercizi[i] = ese;
                                    ripetizioni[i] = rip;
                                    recupero[i] = recup;
                                    metodologia[i] = met;
                                    serie[i] = ser;


                                }
                                sessionScheda.setScheda(giorno, esercizi, ripetizioni, recupero, metodologia, serie);
                                setRowsTable(esercizi, ripetizioni, recupero, metodologia, serie);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", userAct);
                params.put("password", pswAct);
                params.put("ID", id);
                params.put("giorno", getText());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public String getText() {
        return text;
    }

    private void setRowsTable(String[] ese, String[] rip, String[] recup, String[] met, String[] ser) {

        for (int i = 0; i < ese.length; i++) {
            tr = new TableRow(getContext());

            r1 = new TextView(getContext());
            r2 = new TextView(getContext());
            r3 = new TextView(getContext());
            r4 = new TextView(getContext());
            r5 = new TextView(getContext());

            r1.setText(ese[i] + "\n");
            r1.setWidth(200);
            r1.setGravity(Gravity.CENTER);

            r2.setText(ser[i]);
            r2.setGravity(Gravity.CENTER);

            r3.setText(rip[i]);
            r3.setGravity(Gravity.CENTER);

            r4.setText(recup[i]);
            r4.setGravity(Gravity.CENTER);

            r5.setText(met[i]);
            r5.setGravity(Gravity.CENTER);

            tr.addView(r1);
            tr.addView(r2);
            tr.addView(r3);
            tr.addView(r4);
            tr.addView(r5);

            tr.setBackgroundResource(R.color.colorAccent);
            table.addView(tr);
        }
        materialProgressBar.setVisibility(View.GONE);

    }

    private void clearTable() {

        int count = table.getChildCount();
        if (count > 1) {
            table.removeViews(1, count - 1);
        }
    }

    public void Rimuovi(int x) {
        size = giorno_selezionato.length;
        for (int c = x - 1; c < size - 1; c++) {
            giorno_selezionato[c] = giorno_selezionato[c + 1];
        }
        giorno_selezionato[size - 1] = 0;
        size--;
    }

    public void creaArrayGiorni(String[] sel) {
        giorno_selezionato = new int[sel.length];
        for (int i = 0; i < sel.length; i++)
            giorno_selezionato[i] = Integer.parseInt(sel[i]);
    }
}
