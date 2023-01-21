package com.example.todoapp.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SaveSharedPreference {
    static final String PREF_USER_NAME = "username";
    static final String PREF_TOKEN = "token";

    static SharedPreferences getSharedPreference(Context ctx){
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPrefUserName(Context ctx, String username){
        Editor editor = getSharedPreference(ctx).edit();
        editor.putString(PREF_USER_NAME, username);
        editor.apply();
    }

    public static void setPrefToken(Context ctx, String token){
        Editor editor = getSharedPreference(ctx).edit();
        editor.putString(PREF_TOKEN, token);
        editor.apply();
    }

    public static String getPrefUserName(Context ctx) {
        return getSharedPreference(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getPrefToken(Context ctx) {
        return getSharedPreference(ctx).getString(PREF_TOKEN, "");
    }

    public static void clearData(Context ctx) {
        Editor editor = getSharedPreference(ctx).edit();
        editor.clear();
        editor.apply();
    }


}
