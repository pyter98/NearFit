package com.example.nearfit;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ThemeUtils
{
    private static int cTheme;
    public final static int Gabriele = 0;
    public final static int Piero = 1;
    public final static int Ernesto = 2;
    public final static int Riccardo = 3;

    public static void changeToTheme(Activity activity, int theme)
    {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));

    }

    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (cTheme)
        {
            default:
            case Gabriele:
                activity.setTheme(R.style.Gabriele);
                break;
            case Piero:
                activity.setTheme(R.style.Piero);
                break;
            case Ernesto:
                activity.setTheme(R.style.Ernesto);
                break;
            case Riccardo:
                activity.setTheme(R.style.Riccardo);
                break;
        }
    }
}