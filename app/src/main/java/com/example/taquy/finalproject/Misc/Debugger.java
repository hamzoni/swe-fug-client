package com.example.taquy.finalproject.Misc;

import android.util.Log;

/**
 * Created by taquy on 2/20/2018.
 */

public class Debugger {
    public static void log(String msg) {
        StackTraceElement trace = Thread.currentThread().getStackTrace()[3]; // parent trace
        String fn = trace.getFileName();
        int line = trace.getLineNumber();
        Log.e("0==|======> (" + fn + ":" + line + ")", msg);
    }
}
