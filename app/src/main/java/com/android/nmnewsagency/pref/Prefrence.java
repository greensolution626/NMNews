package com.android.nmnewsagency.pref;

import android.content.Context;
import android.content.SharedPreferences;


public class Prefrence {
    public static final String CONFIG_FILE = "NMNEWS";
    public static final String INVALID_STRING = "";
    public static final int INVALID_INT = 0;
    public static final boolean INVALID_BOOLEN = false;
    public static final String FLD_LOGIN = "login";
    public static final String FLD_TAHSIL = "tahsil";
    private static SharedPreferences preference = null;
    private static SharedPreferences.Editor editor;
    private static Context mContext;

    public static void init(Context mContext) {
        Prefrence.mContext = mContext;
        Prefrence.preference = mContext.getSharedPreferences(CONFIG_FILE, Context.MODE_PRIVATE);
        Prefrence.editor = preference.edit();
    }

    public static void removeOrClearPerferance(String key) {
        if (key != null && key.trim().length() != 0)
            editor.remove(key);
        else {
            editor.clear();
        }
        editor.commit();
    }

    public static void removeOrClearAllPerferanceData() {
        editor.clear();
        editor.apply();
    }

    public static String getlogin() {
        return preference.getString(FLD_LOGIN, INVALID_STRING);
    }

    public static void setlogin(String login) {
        editor.putString(FLD_LOGIN, login);
        editor.commit();
    }

    public static String gettahsil() {
        return preference.getString(FLD_TAHSIL, INVALID_STRING);
    }

    public static void settahsil(String login) {
        editor.putString(FLD_TAHSIL, login);
        editor.commit();
    }

}

