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

    public static String dateToString(Date date, String format) {
        if (format == null) format= "HH:mm:ss dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String dateToString(Date date) {
        String format = "HH:mm:ss dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(date);
    }
}
