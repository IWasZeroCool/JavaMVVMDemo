package com.justincamp.demo.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

public class SharedPrefs {

    private static final String SHARED_PREFS_NAME = "HomeSpotterDemoPreferences";
    private static final String INITIAL_MESSAGE_KEY = "InitialMessage";

    private WeakReference<Context> contextWR;

    public SharedPrefs(Context context) {
        if (context != null) {
            contextWR = new WeakReference<>(context);
        }
    }

    public void setShouldShowInitialMessage(boolean showInitialMessage) {
        if (contextWR == null || contextWR.get() == null) return;
        SharedPreferences prefs = contextWR.get().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(INITIAL_MESSAGE_KEY, showInitialMessage);
        editor.apply();
    }

    public boolean getShouldShowInitialMessage() {
        if (contextWR == null || contextWR.get() == null) return false;
        SharedPreferences prefs = contextWR.get().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(INITIAL_MESSAGE_KEY, true);
    }

}
