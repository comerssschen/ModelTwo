package com.comersss.modeltwo;

import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.bean.ArgGetAuthInfo;
import com.comersss.modeltwo.fragments.BossFragment;
import com.comersss.modeltwo.fragments.CashierPlatformFragment;
import com.comersss.modeltwo.fragments.MessageFragment;
import com.comersss.modeltwo.fragments.StatementFragment;
import com.comersss.modeltwo.dialog.home.BackPressDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * 主界面
 * Created by cc on 2016/8/2.
 * 邮箱：904359289@QQ.com.
 *
 * */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public AppContext appContext;
    RadioGroup radiogroup;
    private ArrayList<Fragment> fragments;
    private CashierPlatformFragment cashierPlatformFragment;
    private StatementFragment statementFragment;
    private MessageFragment messageFragment;
    private BossFragment bossFragment;
    private BackPressDialog backPressDialog;
    private HashMap<Object, Object> localHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = (AppContext) getApplication();
        radiogroup = findViewById(R.id.radiogroup);
        fragments = new ArrayList<>();
        cashierPlatformFragment = new CashierPlatformFragment();
        statementFragment = new StatementFragment();
        bossFragment = new BossFragment();
        messageFragment = new MessageFragment();
        fragments.add(cashierPlatformFragment);
        fragments.add(statementFragment);
        fragments.add(bossFragment);
        fragments.add(messageFragment);

        setDefaultFragment(fragments.get(0));
        radiogroup.setOnCheckedChangeListener(this);
        radiogroup.check(R.id.rb_home);

        ImageView ivLoginOut = findViewById(R.id.iv_login_out);
        ivLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.getInstance().clear();
                finish();
            }
        });

        try {
            WxPayFace.getInstance().initWxpayface(MainActivity.this, new IWxPayfaceCallback() {
                public void response(Map paramMap) throws RemoteException {
                    Log.i("test", "response | initWxpayface " + paramMap);
                    getAuthInfo();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("test", ex + "");
        }

    }

    @Override
    public void onBackPressed() {
        backPressDialog = new BackPressDialog(MainActivity.this);
        backPressDialog.setOnOkClickListener(new BackPressDialog.OnOkClickListener() {
            @Override
            public void onOkClick() {
                finish();
            }
        });
        backPressDialog.show();
    }

    //当页面重新进入主界面读取用户信息
    @Override
    protected void onResume() {
        super.onResume();
    }

    private Fragment preFragment;

    //底部导航栏切换
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        int index = group.indexOfChild(group.findViewById(checkedId));
        Fragment fragment = fragments.get(index);

        if (preFragment != fragment) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!fragment.isAdded()) { // 先判断是否被add过
                transaction.hide(preFragment).add(R.id.framelayout, fragment).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(preFragment).show(fragment).commit(); // 隐藏当前的fragment，显示下一个
            }

//            transaction.replace(R.id.framelayout, fragment).commit(); // 替换fragment
            preFragment = fragment;
        }
    }

    private void setDefaultFragment(Fragment fm) {
        FragmentTransaction mFragmentTrans = getSupportFragmentManager().beginTransaction();

        mFragmentTrans.add(R.id.framelayout, fm).commit();

        preFragment = fm;
    }


    public void getAuthInfo() {
        WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            public void response(Map paramMap) throws RemoteException {
                Log.i("test", "paramMap = " + paramMap);
                localHashMap = new HashMap<>();
                localHashMap.put("data1", "10");
                localHashMap.put("data2", paramMap.get("rawdata").toString());
                Log.i("test", "map = " + localHashMap);
                OkGo.<String>post(Constant.URL + Constant.WechatFaceAuth)
                        .upJson(new Gson().toJson(localHashMap))
                        .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    String body = response.body();
                                    Log.i("test", "getAuthInfo :" + body);
                                    ArgGetAuthInfo result = new Gson().fromJson(body, ArgGetAuthInfo.class);
                                    if (result.isSuccess()) {
                                        Constant.authInfo.setAuthinfo(result.getData().getAuthinfo());
                                        Constant.authInfo.setExpire_time(System.currentTimeMillis());
                                        //开启一个子线程，定时刷新
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Looper.prepare();
                                                try {
//                                                    int millis = Integer.parseInt(result.getData().getExpires_in());
//                                                    Log.i("test", millis + "");
                                                    Thread.sleep(24 * 60 * 60 * 1000);
                                                    getAuthInfo();
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                Looper.loop();
                                            }
                                        }).start();
                                    } else {
                                        ToastUtils.showShort(" Msg = " + result.getMessage());
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    ToastUtils.showShort("初始化失败,请退出重进");

                                }
                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);
                                ToastUtils.showShort("初始化失败,请退出重进");
                            }
                        });
            }
        });

    }


}
