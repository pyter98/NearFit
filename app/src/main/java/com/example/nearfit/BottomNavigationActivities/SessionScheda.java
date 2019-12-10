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

    public SessionScheda(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }



    public void setScheda(String giorno){

        editor.putBoolean(LOGIN, true);
        editor.putString(GIORNO, giorno);
        editor.apply();

    }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }


    public HashMap<String, String> getGiorno(){

        HashMap<String, String> user = new HashMap<>();
        user.put(GIORNO, sharedPreferences.getString(GIORNO, null));

        return user;
    }

}
