package com.cjl.cjlchaoxing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cjl.cjlchaoxing.cookies.GetCookies;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;

public class LoginActivity extends AppCompatActivity implements Informationn {
    private static String TAG = "TAG";
    public static String cookies = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText phone = findViewById(R.id.edit_phone);
        EditText password = findViewById(R.id.password);
        Button bu_login = findViewById(R.id.login);

//        //允许网络活动在当前线程执行
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

        bu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = phone.getText().toString();
                String pass = password.getText().toString();
                if (number.length()==0 || pass.length()==0){
                    Snackbar.make(view, "账号密码不能为空", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    login("15677980928","chenjiliang123",LoginActivity.this);
                    Log.v(TAG, number + "\n" + pass);
                }
                //System.out.println(cookies);
            }
        });
    }


    private  void login(String user_account, String user_password, Context contexts) {
        Log.i("登录函数:", "开始尝试登录");

        OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                cookies = "";
                for (Cookie cookie : list) {
                    String cookies_chip = cookie.toString().split(";")[0] + ";";
                    cookies += cookies_chip;
                }
                new GetCookies().setCookies(cookies);
            }

            @NonNull
            @Override
            public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {

                return new ArrayList<>();
            }
        }).build();

        Request request = new Request.Builder()
                .url("https://passport2-api.chaoxing.com/v11/loginregister?code=" + user_password + "&cx_xxt_passport=json&uname=" + user_account + "&loginType=1&roleSelect=true")
                .addHeader("User-Agent", user_agent)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.v(TAG,"网络请求失败");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.v(TAG,"网络请求成功");
                Log.v(TAG,cookies);


                View view = LayoutInflater.from(contexts).inflate(R.layout.nav_header_main2,null);
                TextView textView = view.findViewById(R.id.stu_name);
                textView.setText("小明");
                System.out.println(textView.getText());

                Intent intent = new Intent(contexts,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
