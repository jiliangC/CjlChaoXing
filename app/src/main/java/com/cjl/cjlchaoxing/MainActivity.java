package com.cjl.cjlchaoxing;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjl.cjlchaoxing.cookies.GetCookies;
import com.cjl.cjlchaoxing.databinding.ActivityMain2Binding;
import com.cjl.cjlchaoxing.get_url_img.ImageService;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Informationn {

    private AppBarConfiguration mAppBarConfiguration;
    private static String cookies;
    private final OkHttpClient okHttpClient = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cookies = new GetCookies().getCookies();

        new Thread() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("https://sso.chaoxing.com/apis/login/userLogin4Uname.do")
                        .addHeader("Cookie", cookies)
                        .addHeader("User-Agent", user_agent)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    JSONObject jsonObject = JSON.parseObject(response.body().string()).getJSONObject("msg");

                    String img_url = jsonObject.getString("pic");
                    String stu_name = jsonObject.getString("name");
                    String school_name = jsonObject.getString("schoolname");
                    String stu_number = jsonObject.getString("uname");

                    Request request1 = new Request.Builder()
                            .url(img_url)
                            .build();
                    Call call1 = okHttpClient.newCall(request1);
                    Response response1 = call1.execute();
                    String t_img_url = response1.request().url().toString();

                    byte[] data = ImageService.getImage(t_img_url);
                    Bitmap bitmap_t = BitmapFactory.decodeByteArray(data, 0, data.length);  //生成位图
                    Bitmap bitmap = ImageService.createCircleImage(bitmap_t);

                    runOnUiThread(() -> {
                        NavigationView view = findViewById(R.id.nav_view);
                        View v = view.getHeaderView(0);
                        ImageView imageView = v.findViewById(R.id.imageView);

                        TextView s_name = v.findViewById(R.id.stu_name);
                        s_name.setText(stu_name);
                        TextView s_school = v.findViewById(R.id.stu_school);
                        s_school.setText(school_name);
                        TextView s_number = v.findViewById(R.id.stu_number);
                        s_number.setText("学号 " + stu_number);
                        String welcome = "欢迎你，" + stu_name;
                        imageView.setImageBitmap(bitmap);

                        Toast.makeText(MainActivity.this, welcome, Toast.LENGTH_SHORT).show();
                    });

                    System.out.println(school_name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        com.cjl.cjlchaoxing.databinding.ActivityMain2Binding binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain2.toolbar);


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_course,
                R.id.nav_sign,
                R.id.nav_task,
                R.id.nav_exam,
                R.id.nav_setting
        )
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this,"点击了开启签到",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_sign_back:
                Toast.makeText(this,"点击了退出签到服务",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_back_login:
                Toast.makeText(this,"点击了退出登录",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}