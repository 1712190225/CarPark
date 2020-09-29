package com.example.parkapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class logActivity extends AppCompatActivity {
    private EditText edit01, edit02;
    private Button button01, button02;
    private SharedPreferences sp;
    private static final String PREFERENCE_NAME = "temp_data";
    private String r1, r2, q1, q2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        edit01 = findViewById(R.id.edit03);
        edit02 = findViewById(R.id.edit04);
        button01 = findViewById(R.id.button01);
        button02 = findViewById(R.id.button02);
        sp = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            r1 = bundle.getString("account");
            r2 = bundle.getString("password");
        } else {
            r1 = sp.getString("account", null);
            r2 = sp.getString("password", null);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("account", r1);
        editor.putString("password", r2);
        editor.commit();

        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q1 = edit01.getText().toString();
                q2 = edit02.getText().toString();
                if (r1 == null) {
                    new AlertDialog.Builder(logActivity.this).setTitle("提示").setMessage("还未注册").show();
                } else if (!r1.equals(q1)) {
                    new AlertDialog.Builder(logActivity.this).setTitle("提示").setMessage("该用户不存在").show();
                } else if (!r2.equals(q2)) {
                    new AlertDialog.Builder(logActivity.this).setTitle("提示").setMessage("密码错误").show();
                } else {
                    Intent intentlogin = new Intent(logActivity.this, MainActivity.class);
                    startActivity(intentlogin);
                }
            }

        });
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentregister = new Intent(logActivity.this, RegisterActivity.class);
                startActivity(intentregister);
            }
        });
    }
}