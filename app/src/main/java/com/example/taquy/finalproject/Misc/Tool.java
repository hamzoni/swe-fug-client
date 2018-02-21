package com.example.taquy.finalproject.Misc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tool {

    public static Bitmap loadBitmap(String url) throws IOException {
        URL iurl = new URL(url);
        InputStream is = iurl.openConnection().getInputStream();
        Bitmap image = BitmapFactory.decodeStream(is);
        is.close();
        return image;
    }

    public static String humanfmt = "HH:mm:ss dd-MM-yyyy";
    public static String sqlfmt = "yyyy-MM-dd HH:mm:ss";

    public static double parseDouble(String n) {
        try {
            return Double.parseDouble(n);
        } catch (Exception e) {
            Debugger.log("Unable to convert \" " + n + "\" to double");
        }
        return -1;
    }

    public static int indexOf(int[] array, int el) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == el) return i;
        }
        return -1;
    }

    public static String dateToString(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(humanfmt);
            return sdf.format(date);
        } catch (Exception e) {
            Debugger.log("Unable to parse date");
            return null;
        }
    }

    // turn from date to sql format
    public static String dateToString2(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(sqlfmt);
            return sdf.format(date);
        } catch (Exception e) {
            Debugger.log("Unable to parse date");
            return null;
        }
    }

    // turn from human readable date string to date
    public static Date stringToDate2(String date) throws ParseException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(humanfmt);
            return formatter.parse(date);
        } catch (Exception e) {
            Debugger.log("Unable to parse date");
            return null;
        }
    }

    public static Date stringToDate(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(sqlfmt);
            return formatter.parse(date);
        } catch (Exception e) {
            Debugger.log("Unable to parse date");
            return null;
        }
    }
}
