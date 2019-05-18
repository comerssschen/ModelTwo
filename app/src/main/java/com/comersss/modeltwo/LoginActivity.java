package com.comersss.modeltwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lzy.okgo.OkGo;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void Login(View view) {
        Map<String,String> logMap=new HashMap<>();
        logMap.put("LoginName","10000");
        logMap.put("LoginPassword","111");

        startActivity(new Intent(LoginActivity.this,
                MainActivity.class));
    }
}
