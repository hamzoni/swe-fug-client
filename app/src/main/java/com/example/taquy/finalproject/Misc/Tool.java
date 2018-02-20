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

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(humanfmt);
        return sdf.format(date);
    }

    // turn from date to sql format
    public static String dateToString2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(sqlfmt);
        return sdf.format(date);
    }

    // turn from human readable date string to date
    public static Date stringToDate2(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(humanfmt);
        return formatter.parse(date);
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(sqlfmt);
        return formatter.parse(date);
    }
}
