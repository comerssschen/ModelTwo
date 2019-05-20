package com.comersss.modeltwo.fragments;

import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.AuthInfo;
import com.comersss.modeltwo.EditTextUtils;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.ArgGetAuthInfo;
import com.comersss.modeltwo.bean.ResultFacePay;
import com.comersss.modeltwo.bean.ResultGetOrder;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.util.Log.d;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * 收银主页
 * Created by cc on 2016/9/19.
 */
public class CashierPlatformFragment extends BaseFragment {

    private static final String TAG = "test";
    @BindView(R.id.paymoney_edit)
    EditText paymoneyEdit;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.ll_truemoney)
    LinearLayout llTruemoney;
    @BindView(R.id.ll_qrcode)
    LinearLayout llQrcode;
    @BindView(R.id.ll_bankcard)
    LinearLayout llBankcard;
    Unbinder unbinder;
    private String paymoney;
    private String localhostUrl = "http://api.test.360yunpay.com";
    private String money;
    private AuthInfo authInfo;
    private String appid = "wx2b975c0ca94a6154";
    private String mch_id = "1440771702";
    private String sub_mch_id = "1531015161";
    private HashMap localHashMap;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "createView");
        return inflater.inflate(R.layout.fragment_cashier_platform, container, false);
    }

    //初始化界面
    protected void initView(View mChildContentView, Bundle savedInstanceState) {
        Log.i(TAG, "initView");
        unbinder = ButterKnife.bind(this, mChildContentView);
        EditTextUtils.setPriceEditText(paymoneyEdit);
        authInfo = new AuthInfo();
//        WxPayFace.getInstance().initWxpayface(getActivity(), new IWxPayfaceCallback() {
//            public void response(Map paramMap) throws RemoteException {
//                d(TAG, "response | initWxpayface " + paramMap);
//                getAuthInfo();
//            }
//        });
    }

    public void getAuthInfo() {
        WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            public void response(Map paramMap) throws RemoteException {
                Log.i("test", "paramMap = " + paramMap);
                Map<String, Object> map = new HashMap<>();
                map.put("data1", "10");
                map.put("data2", paramMap.get("rawdata").toString());
                Log.i("test", "map = " + map);
                OkGo.<String>post(localhostUrl + "/api/FacePayApi/WechatFaceAuth")
                        .upJson(new Gson().toJson(map))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                try {
                                    String body = response.body();
                                    Log.i("test", "getAuthInfo :" + body);
                                    ArgGetAuthInfo result = new Gson().fromJson(body, ArgGetAuthInfo.class);
                                    if (result.isSuccess()) {

                                        authInfo.setAuthinfo(result.getData().getAuthinfo());
                                        authInfo.setExpire_time(System.currentTimeMillis());
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

    private void getOrder() {
        Map<String, Object> map = new HashMap<>();
        map.put("data", "10");
        OkGo.<String>post(localhostUrl + "/api/FacePayApi/GenerateOrderNum")
                .upJson(new Gson().toJson(map))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            ResultGetOrder resultGetOrder = new Gson().fromJson(body, ResultGetOrder.class);
                            if (resultGetOrder.isSuccess()) {
                                wxPay(resultGetOrder.getData());
                            }
                            Log.i("test", "getAuthInfo :" + body);

                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort("预下单失败，请重试");

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort("预下单失败，请重试");
                    }
                });
    }

    private void wxPay(final String outTratNum) {
        BigDecimal minMoney = new BigDecimal(paymoney);
        money = minMoney.multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        localHashMap = new HashMap();
        localHashMap.put("face_authtype", "FACEPAY");
        localHashMap.put("appid", appid);
        localHashMap.put("mch_id", mch_id);
//        localHashMap.put("store_id", store_id);
        localHashMap.put("out_trade_no", outTratNum);
        localHashMap.put("total_fee", money);
        localHashMap.put("ask_face_permit", "1");
        localHashMap.put("ask_ret_page", "1");
//        localHashMap.put("sub_appid", sub_appid);
        localHashMap.put("sub_mch_id", sub_mch_id);
        if (TextUtils.isEmpty(authInfo.getAuthinfo())) {
            ToastUtils.showShort("请先获取rawdata");
            return;
        }
        localHashMap.put("authinfo", authInfo.getAuthinfo());
        Log.i("test", "localHashMap" + localHashMap.toString());
        WxPayFace.getInstance().getWxpayfaceCode(localHashMap, new IWxPayfaceCallback() {
            public void response(final Map paramMap) throws RemoteException {
                d(TAG, "response | getWxpayfaceCode " + paramMap);
                final String code = paramMap.get("return_code").toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (TextUtils.equals(code, "SUCCESS")) {
                            Map<String, String> map = new HashMap<>();
                            map.put("data1", "10");
                            map.put("data2", money);
                            map.put("data3", outTratNum);
                            map.put("data4", paramMap.get("openid").toString().trim());
                            map.put("data5", paramMap.get("face_code").toString().trim());
                            OkGo.<String>post(localhostUrl + "/api/FacePayApi/WechatFacePay")
                                    .upJson(new Gson().toJson(map))
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            Log.i("test", "response.body() = " + response.body());
                                            try {
                                                ResultFacePay resultFacePay = new Gson().fromJson(response.body(), ResultFacePay.class);
                                                if (resultFacePay.isSuccess()) {

                                                    WxPayFace.getInstance().updateWxpayfacePayResult(localHashMap, new IWxPayfaceCallback() {
                                                        public void response(Map paramMap) throws RemoteException {
                                                            d(TAG, "paramMap" + paramMap);
                                                            ToastUtils.showShort("支付成功");
                                                        }
                                                    });
                                                } else {
                                                    ToastUtils.showShort(resultFacePay.getMessage());
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                ToastUtils.showShort("网络异常");
                                            }
                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);
                                            ToastUtils.showShort("网络异常：" + response.body());
                                        }
                                    });


                        } else if (TextUtils.equals(code, "USER_CANCEL")) {
                            ToastUtils.showShort("用户取消");
                        } else if (TextUtils.equals(code, "SCAN_PAYMENT")) {
                            ToastUtils.showShort("扫码支付");
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.i("test", "切换到了cashfragment");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        WxPayFace.getInstance().releaseWxpayface(getActivity());
    }

    @OnClick({R.id.tv_member, R.id.tv_refund, R.id.ll_scan, R.id.ll_truemoney, R.id.ll_qrcode, R.id.ll_bankcard, R.id.soft_keyboard_btn_1, R.id.soft_keyboard_btn_2, R.id.soft_keyboard_btn_3, R.id.soft_keyboard_btn_4, R.id.soft_keyboard_btn_5, R.id.soft_keyboard_btn_6, R.id.soft_keyboard_btn_7, R.id.soft_keyboard_btn_8, R.id.soft_keyboard_btn_9, R.id.tv_point, R.id.soft_keyboard_btn_0, R.id.rl_delete})
    public void onViewClicked(View view) {
        Editable editable = paymoneyEdit.getText();
        int start = paymoneyEdit.getSelectionStart();
        switch (view.getId()) {
            case R.id.tv_member:
                break;
            case R.id.tv_refund:
                break;
            case R.id.ll_scan:
                if (verifyMoney()) {
                    getOrder();
                }
                break;
            case R.id.ll_truemoney:
                break;
            case R.id.ll_qrcode:
                break;
            case R.id.ll_bankcard:
                break;
            case R.id.soft_keyboard_btn_0:
                editable.insert(start, Character.toString('0'));
                break;
            case R.id.soft_keyboard_btn_1:
                editable.insert(start, Character.toString('1'));
                break;
            case R.id.soft_keyboard_btn_2:
                editable.insert(start, Character.toString('2'));
                break;
            case R.id.soft_keyboard_btn_3:
                editable.insert(start, Character.toString('3'));
                break;
            case R.id.soft_keyboard_btn_4:
                editable.insert(start, Character.toString('4'));
                break;
            case R.id.soft_keyboard_btn_5:
                editable.insert(start, Character.toString('5'));
                break;
            case R.id.soft_keyboard_btn_6:
                editable.insert(start, Character.toString('6'));
                break;
            case R.id.soft_keyboard_btn_7:
                editable.insert(start, Character.toString('7'));
                break;
            case R.id.soft_keyboard_btn_8:
                editable.insert(start, Character.toString('8'));
                break;
            case R.id.soft_keyboard_btn_9:
                editable.insert(start, Character.toString('9'));
                break;
            case R.id.tv_point:
                if (!paymoneyEdit.getText().toString().contains(".")) {
                    editable.insert(start, Character.toString('.'));
                }
                break;
            case R.id.rl_delete:
                rollBack(editable, start);
                break;
        }
    }

    private boolean verifyMoney() {
        paymoney = paymoneyEdit.getText().toString().trim();
        if (ObjectUtils.isEmpty(paymoney) || TextUtils.equals(paymoney, "0") || TextUtils.equals(paymoney, "0.") || TextUtils.equals(paymoney, "0.0") || TextUtils.equals(paymoney, "0.00")) {
            ToastUtils.showShort("请输入有效金额");
            return false;
        } else if (Double.parseDouble(paymoney) > 100000) {
            ToastUtils.showShort("最大金额99999.99");
            return false;
        } else {
            return true;
        }
    }

    /**
     * 回退
     */
    private void rollBack(Editable editable, int start) {
        if (editable != null && editable.length() > 0) {
            if (start > 0) {
                editable.delete(start - 1, start);
            }
        }
    }

}



