package com.example.nearfit.Settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    SharedPreferences mySP;
    public SharedPref (Context context) {
        mySP = context.getSharedPreferences("filename", Context.MODE_PRIVATE);
    }

    public void setTheme (boolean state){
        SharedPreferences.Editor editor = mySP.edit();
        editor.putBoolean("Gabriele",state);
        editor.commit();
    }

    public boolean loadGabrieleState (){
        Boolean state = mySP.getBoolean("Gabriele", false);
        return state;
    }
}
