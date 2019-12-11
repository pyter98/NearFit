package com.example.nearfit.BottomNavigationActivities;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.nearfit.BottomNavigationActivities.PostLoginActivity;
import com.example.nearfit.MainActivity;

import java.util.HashMap;

/*
Questa classe si occupa del mantenimento della sessione di un utente loggato.
 */
public class SessionScheda {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String GIORNO = "GIORNO";
    public static final String ESERCIZI = "ESERCIZI";
    public static final String RIPETIZIONI = "RIPETIZIONI";
    public static final String RECUPERO = "RECUPERO";
    public static final String METODOLOGIA = "METODOLOGIA";
    public static final String SERIE = "SERIE";
    public static final String SELEZIONE = "SELEZIONE";
    public static final String CONNESSIONE = "CONNESSIONE";
    StringBuilder esercizi, ripetizioni, recupero, serie, metodologia,sel, conn;

    public SessionScheda(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }



    public void setScheda(String giorno, String[] ese, String[] rip, String[] recup, String[] met, String[] ser){

        editor.putBoolean(LOGIN, true);
        editor.putString(GIORNO, giorno);
        esercizi = new StringBuilder();
        ripetizioni = new StringBuilder();
        recupero = new StringBuilder();
        metodologia = new StringBuilder();
        serie = new StringBuilder();
        if (ese!=null) {
            for (int i = 0; i < ese.length; i++) {
                esercizi.append(ese[i]).append(",");
                ripetizioni.append(rip[i]).append(",");
                recupero.append(recup[i]).append(",");
                metodologia.append(met[i]).append(",");
                serie.append(ser[i]).append(",");
            }
        }
        editor.putString(ESERCIZI, esercizi.toString());
        editor.putString(RIPETIZIONI, ripetizioni.toString());
        editor.putString(RECUPERO, recupero.toString());
        editor.putString(METODOLOGIA, metodologia.toString());
        editor.putString(SERIE, serie.toString());
        editor.apply();

    }

    public boolean isSet(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }


    public HashMap<String, String> getScheda(){

        HashMap<String, String> scheda = new HashMap<>();
        scheda.put(GIORNO, sharedPreferences.getString(GIORNO, null));
        scheda.put(ESERCIZI, sharedPreferences.getString(ESERCIZI, null));
        scheda.put(RECUPERO, sharedPreferences.getString(RECUPERO, null));
        scheda.put(METODOLOGIA, sharedPreferences.getString(METODOLOGIA, null));
        scheda.put(SERIE, sharedPreferences.getString(SERIE, null));
        scheda.put(RIPETIZIONI, sharedPreferences.getString(RIPETIZIONI, null));
        scheda.put(SELEZIONE, sharedPreferences.getString(SELEZIONE, null));
        scheda.put(CONNESSIONE, sharedPreferences.getString(CONNESSIONE, null));

        return scheda;
    }

    public void setSelezione (int[] selezione){
        sel = new StringBuilder();
        for (int i = 0; i < selezione.length; i++) {
            sel.append(selezione[i]).append(",");
        }
        editor.putString(SELEZIONE,sel.toString());
        editor.apply();
    }

    public void setConnessione (int[] connessione){
        conn = new StringBuilder();
        for (int i = 0; i < connessione.length; i++) {
            conn.append(connessione[i]).append(",");
        }
        editor.putString(CONNESSIONE,conn.toString());
        editor.apply();
    }
}