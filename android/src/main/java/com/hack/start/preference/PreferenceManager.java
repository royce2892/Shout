package com.hack.start.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created by Rraju on 7/25/2015.
 */
public class PreferenceManager {

    SharedPreferences preferences;

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
    }

    public void put(String key,String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public void put(String key,boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public String get(String key) {
        return preferences.getString(key,"default");
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, true);
    }
}
