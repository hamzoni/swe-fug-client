package com.example.taquy.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ipt_email;
    private EditText ipt_name;
    private EditText ipt_phone;
    private EditText ipt_password;
    private EditText ipt_retypepwd;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ipt_email = findViewById(R.id.ipt_email);
        ipt_name = findViewById(R.id.ipt_name);
        ipt_phone = findViewById(R.id.ipt_phone);
        ipt_password = findViewById(R.id.ipt_password);
        ipt_retypepwd = findViewById(R.id.ipt_retypepwd);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);

        development();
    }

    private void development() {
        ipt_password.setText("secret");
        ipt_retypepwd.setText("secret");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                action_registration_submit();
                break;
        }
    }

    private void action_registration_submit() {
        String email = ipt_email.getText().toString().trim();
        String name = ipt_name.getText().toString().trim();
        String phone = ipt_phone.getText().toString().trim();
        String pwd = ipt_password.getText().toString().trim();
        String pwd2 = ipt_retypepwd.getText().toString().trim();

        // no blank input allowed
        if (email.length() == 0 || name.length() == 0 || phone.length() == 0 || pwd.length() == 0) {
            Toast.makeText(this, "No blank input allowed", Toast.LENGTH_LONG).show();
            ipt_password.setText("");
            ipt_retypepwd.setText("");
            return;
        }

        // password and retype password must matched
        if (!pwd.equals(pwd2)) {
            Toast.makeText(this, "Password and retype pasword must matched", Toast.LENGTH_LONG).show();
            ipt_password.setText("");
            ipt_retypepwd.setText("");
            return;
        }

        // submit registration form to server
    }
}
