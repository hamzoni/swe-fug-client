package com.example.taquy.finalproject.Misc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.taquy.finalproject.Entities.User;
import com.example.taquy.finalproject.LoginActivity;
import com.google.gson.Gson;

/**
 * Created by taquy on 2/19/2018.
 */

public class Authentication {
    private Context ctx;
    private String prefname;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public Authentication(Context ctx) {
        this.ctx = ctx;
        this.prefname = "AUTH_USER";
        this.pref = ctx.getSharedPreferences(prefname, 0);
        this.editor = pref.edit();
    }

    public void store(User user) {
        String json = new Gson().toJson(user);
        editor.putString(prefname, json);
        editor.commit();
    }

    public User retrieve() {
        String json = pref.getString(prefname, null);
        return new Gson().fromJson(json, User.class);
    }

    public boolean isAuth() {
        User user = retrieve();
        if (user == null) logout();
        return user != null;
    }

    public void logout() {
        // Clear data
        editor.clear();
        editor.commit();

        // Navigate view
        Intent intent = new Intent(ctx, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }
}
