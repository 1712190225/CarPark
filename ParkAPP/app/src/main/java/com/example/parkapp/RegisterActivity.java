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

public class RegisterActivity extends AppCompatActivity {
    private EditText edit03,edit04;
    private Button button03;
    private SharedPreferences sp;
    private static final String PREFERENCE_NAME = "temp_data";
    private String r1, r2, a, b;

    private class MyThread2 extends Thread {

        private String a;
        private String b;


        public MyThread2(String a,String b) {
            this.a = a;
            this.b = b;

        }
        @Override
        public void run() {

            OkHttpClient mOkHttpClient = new OkHttpClient();
            String url = "http://192.168.43.223:9099/user/createuser";
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

                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        Intent intentlogin = new Intent(RegisterActivity.this, logActivity.class);
                        startActivity(intentlogin);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, mes, Toast.LENGTH_SHORT).show();
                    }
                    Looper.loop();
                }
            });
        }
    }

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
                a = edit03.getText().toString();
                b = edit04.getText().toString();

                int index1 = a.indexOf(":");
                int index2 = b.indexOf(":");
                String suba=a.substring(index1+1);
                String subb=b.substring(index2+1);
                Log.d("a=",a);
                Log.d("b=",b);
                RegisterActivity.MyThread2 mt1=new RegisterActivity.MyThread2(suba,subb);
                mt1.start();
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
