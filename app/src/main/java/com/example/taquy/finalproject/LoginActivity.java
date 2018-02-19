package com.example.taquy.finalproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taquy.finalproject.API.UserDAL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private EditText ipt_login;
    private EditText ipt_pwd;
    private Button btn_submit;
    private Button btn_register;

    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ipt_login = findViewById(R.id.ipt_login);
        ipt_pwd = findViewById(R.id.ipt_pwd);
        btn_submit = findViewById(R.id.btn_submit);
        btn_register = findViewById(R.id.btn_register);

        btn_submit.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        root = getWindow().getDecorView().getRootView();

//        development();
    }

    private void development() {
        // testing
        ipt_login.setText("doyle.leola@example.net");
        ipt_pwd.setText("secret");
        this.actionSubmit();
    }

    private void actionSubmit() {
        JSONObject json = new JSONObject();
        try {
            json.put("login", ipt_login.getText().toString());
            json.put("pwd", ipt_pwd.getText().toString());
            new UserDAL(root, UserDAL.CMD_LOGIN).makeRequest(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actionRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                actionSubmit();
                break;
            case R.id.btn_register:
                actionRegister();
                break;
        }
    }
}

