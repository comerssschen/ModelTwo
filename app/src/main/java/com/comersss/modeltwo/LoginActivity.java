package com.comersss.modeltwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.bean.ResultLogin;
import com.comersss.modeltwo.dialog.home.LoadingDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {

    private EditText etLoinname;
    private EditText etPassword;
    private ImageView ivEye;
    private String loginName;
    private String pwd;
    private SPUtils spInsance;
    private LoadingDialog loadingDialog;
    private boolean isHidden;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spInsance = SPUtils.getInstance();
        etLoinname.setText(spInsance.getString("LoginName"));
        etPassword.setText(spInsance.getString("LoginPassword"));
        if (!ObjectUtils.isEmpty(spInsance.getString("LoginPassword"))) {
            ivEye.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        etLoinname = findViewById(R.id.et_loinname);
        etPassword = findViewById(R.id.et_password);
        ivEye = findViewById(R.id.iv_eye);
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(etPassword.getText().toString().trim())) {
                    ivEye.setVisibility(View.VISIBLE);
                } else {
                    ivEye.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loadingDialog = new LoadingDialog(LoginActivity.this, "登录中。。。");
        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden) {
                    //设置EditText文本为可见的
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                etPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = etPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

            }
        });
    }

    public void Login(View view) {
        loadingDialog.show();
        loginName = etLoinname.getText().toString().trim();
        pwd = etPassword.getText().toString().trim();
        if (ObjectUtils.isEmpty(loginName) || ObjectUtils.isEmpty(loginName)) {
            ToastUtils.showShort("请输入账号密码");
        }
        Map<String, String> logMap = new HashMap<>();
        logMap.put("LoginName", loginName);
        logMap.put("LoginPassword", pwd);
        OkGo.<String>post(Constant.URL + Constant.Login)
                .upJson(new Gson().toJson(logMap))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        loadingDialog.dismiss();
                        try {
                            String body = response.body();
                            Log.i("test", "getAuthInfo :" + body);
                            ResultLogin result = new Gson().fromJson(body, ResultLogin.class);
                            if (result.isSuccess()) {
                                spInsance.put("LoginName", loginName);
                                spInsance.put("LoginPassword", pwd);
                                spInsance.put("token", EncodeUtils.urlEncode(result.getData()));
                                startActivity(new Intent(LoginActivity.this,
                                        MainActivity.class));
                                finish();
                            } else {
                                ToastUtils.showShort(result.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort("登陆失败");

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        loadingDialog.dismiss();
                        ToastUtils.showShort("登陆失败");
                    }
                });

    }
}
