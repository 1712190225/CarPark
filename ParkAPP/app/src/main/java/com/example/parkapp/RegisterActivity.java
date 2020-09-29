package com.example.parkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private EditText edit03,edit04;
    private Button button03;
//    private SharedPreferences sp;
    private static final String PREFERENCE_NAME = "temp_data";
    String r1,r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edit03=(EditText)findViewById(R.id.edit03);
        edit04=(EditText)findViewById(R.id.edit04);
        button03=(Button) findViewById(R.id.button03);
//        sp = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences.Editor editor = sp.edit();
//                String account = edit01.getText().toString();
//                String password = edit02.getText().toString();
//                editor.putString("Account",account);
//                editor.putString("Password",password);
//                editor.commit();
                r1=edit03.getText().toString();
                r2=edit04.getText().toString();
                Intent intent = new Intent(RegisterActivity.this,logActivity.class);
                if(!r1.isEmpty() && !r2.isEmpty()) {
                    intent.putExtra("account", r1);
                    intent.putExtra("password", r2);
                }
                startActivity(intent);
            }
        });

    }
}
