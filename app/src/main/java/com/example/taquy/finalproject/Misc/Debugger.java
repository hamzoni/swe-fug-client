package com.example.taquy.finalproject.Misc;

import android.util.Log;

/**
 * Created by taquy on 2/20/2018.
 */

public class Debugger {
    public static void log(Class c, int line, String msg) {
        Log.e("0==|======> " + c.getSimpleName() + "@" + line, msg);
    }

    public static void log(String msg) {
        Log.d("Debugger Trace", Log.getStackTraceString(new Throwable(msg)));
    }
}
