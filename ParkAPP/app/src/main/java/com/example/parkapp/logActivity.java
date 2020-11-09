package com.example.parkapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class logActivity extends AppCompatActivity {
    private EditText edit01, edit02;
    private Button button01, button02;
    private SharedPreferences sp;
    private static final String PREFERENCE_NAME = "temp_data";
    private String r1, r2, a, b;

    private class MyThread1 extends Thread {

        private String a;
        private String b;


        public MyThread1(String a,String b) {
            this.a = a;
            this.b = b;

        }
        @Override
        public void run() {

            OkHttpClient mOkHttpClient = new OkHttpClient();
            String url = "http://192.168.1.128:9099/user/login";
            FormBody formBody = new FormBody.Builder()
                    .add("loginname", ""+a)
                    .add("password", ""+b)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)//参数放在body体里
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                //失败的实例方法
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String resp = response.body().string();
                    String code = null;
                    String mes = null;
                    try{
                        JSONObject jsonObject = new JSONObject(resp);
                        code = jsonObject.getString("code");
                        mes=jsonObject.getString("message");
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    Looper.prepare();
                    
                    if(code.equals("200")) {

                    Toast.makeText(logActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intentlogin = new Intent(logActivity.this, MainActivity.class);
                    startActivity(intentlogin);
                    }
                    else{
                        Toast.makeText(logActivity.this, mes, Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
            });
        }
    }
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
                a = edit01.getText().toString();
                b = edit02.getText().toString();

                int index1 = a.indexOf(":");
                int index2 = b.indexOf(":");
                String suba=a.substring(index1+1);
                String subb=b.substring(index2+1);
                Log.d("a=",a);
                Log.d("b=",b);
                MyThread1 mt1=new MyThread1(suba,subb);
                mt1.start();
                /*if (r1 == null) {
                    new AlertDialog.Builder(logActivity.this).setTitle("提示").setMessage("还未注册").show();
                } else if (!r1.equals(q1)) {
                    new AlertDialog.Builder(logActivity.this).setTitle("提示").setMessage("该用户不存在").show();
                } else if (!r2.equals(q2)) {
                    new AlertDialog.Builder(logActivity.this).setTitle("提示").setMessage("密码错误").show();
                } else {

                }*/
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