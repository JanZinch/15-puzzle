package com.ivan.puzzle;

import android.util.Log;

public class Debug {

    private static final String TAG ="MyLogs";

    public static void Log(String message){

        Log.d(TAG, message);
    }

}
