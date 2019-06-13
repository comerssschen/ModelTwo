package com.comersss.modeltwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Listener.MemberLevelLitener;
import com.comersss.modeltwo.Listener.TopCountLitener;
import com.comersss.modeltwo.bean.ArgGetAuthInfo;
import com.comersss.modeltwo.bean.ArgTopCountResult;
import com.comersss.modeltwo.bean.CashResult;
import com.comersss.modeltwo.bean.MemberLevelResult;
import com.comersss.modeltwo.bean.ResultBase;
import com.comersss.modeltwo.bean.StoreResult;
import com.comersss.modeltwo.fragments.DataFragment;
import com.comersss.modeltwo.fragments.HomeFragment;
import com.comersss.modeltwo.fragments.SettingFragment;
import com.comersss.modeltwo.fragments.MemberFragment;
import com.comersss.modeltwo.dialog.home.BackPressDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：create by comersss on 2019/5/17 17:42
 * 邮箱：904359289@qq.com
 * 主页面
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    public AppContext appContext;
    RadioGroup radiogroup;
    private ArrayList<Fragment> fragments;
    private HomeFragment homeFragment;
    private MemberFragment memberFragment;
    private SettingFragment settingFragment;
    private DataFragment dataFragment;
    private BackPressDialog backPressDialog;
    private HashMap<Object, Object> localHashMap;
    private TextView tv_cash;
    private TextView tv_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = (AppContext) getApplication();
        tv_store = findViewById(R.id.tv_store);
        tv_cash = findViewById(R.id.tv_cash);
        radiogroup = findViewById(R.id.radiogroup);
        fragments = new ArrayList<>();
        homeFragment = new HomeFragment();
        memberFragment = new MemberFragment();
        dataFragment = new DataFragment();
        settingFragment = new SettingFragment();
        fragments.add(homeFragment);
        fragments.add(memberFragment);
        fragments.add(dataFragment);
        fragments.add(settingFragment);

        setDefaultFragment(fragments.get(0));
        radiogroup.setOnCheckedChangeListener(this);
        radiogroup.check(R.id.rb_home);

        ImageView ivLoginOut = findViewById(R.id.iv_login_out);
        ivLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backPressDialog = new BackPressDialog(MainActivity.this, "退出", "确认退出吗？");
                backPressDialog.setOnOkClickListener(new BackPressDialog.OnOkClickListener() {
                    @Override
                    public void onOkClick() {
                        backPressDialog.dismiss();
                        SPUtils.getInstance().clear();
                        finish();
                    }
                });
                backPressDialog.show();
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

        NetUtil.getInstance().QueryMemberLevels(new MemberLevelLitener() {
            @Override
            public void sucess(List<MemberLevelResult.DataBean> mberLevelList) {
                Constant.mberLevelList = mberLevelList;
            }

            @Override
            public void fail(String errMsg) {

            }
        });
        QueryCasherInfo();
        QueryStoreInfo();
    }

    //当页面重新进入主界面读取用户信息
    @Override
    protected void onResume() {
        super.onResume();
    }

    //返回按钮监听,回到桌面
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
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
                localHashMap.put("data", paramMap.get("rawdata").toString());
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

    public void QueryCasherInfo() {
        OkGo.<String>post(Constant.URL + Constant.QueryCasherInfo)
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            CashResult cashResult = new Gson().fromJson(body, CashResult.class);
                            if (cashResult.isSuccess()) {
                                tv_cash.setText(cashResult.getData().getUserName());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    public void QueryStoreInfo() {
        OkGo.<String>post(Constant.URL + Constant.QueryStoreInfo)
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            StoreResult storeResult = new Gson().fromJson(body, StoreResult.class);
                            if (storeResult.isSuccess()) {
                                tv_store.setText(storeResult.getData().getStoreName());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }


}
