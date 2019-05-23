package com.comersss.modeltwo;

import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Listener.PayResultLitener;
import com.comersss.modeltwo.bean.ResultBase;
import com.comersss.modeltwo.bean.ResultFacePay;
import com.comersss.modeltwo.bean.ResultGetOrder;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.util.HashMap;
import java.util.Map;


/**
 * 作者：create by comersss on 2019/5/23 15:06
 * 邮箱：904359289@qq.com
 * 网络请求
 */
public class NetUtil {

    private static NetUtil netUtil;

    public static NetUtil getInstance() {
        if (netUtil == null)
            netUtil = new NetUtil();
        return netUtil;
    }

    private String Result = "";
    private String TAG = "test";
    private HashMap<Object, Object> localHashMap;


    //获取微信openid
    public String getOpenId() {
        localHashMap = new HashMap();
        localHashMap.put("face_authtype", "FACEID-ONCE");
        localHashMap.put("appid", Constant.appid);
        localHashMap.put("mch_id", Constant.mch_id);
        localHashMap.put("sub_mch_id", Constant.sub_mch_id);
        if (TextUtils.isEmpty(Constant.authInfo.getAuthinfo())) {
            ToastUtils.showShort("请先获取rawdata");
            return Result;
        }
        localHashMap.put("authinfo", Constant.authInfo.getAuthinfo());
        Log.i("test", "localHashMap" + localHashMap.toString());
        WxPayFace.getInstance().getWxpayfaceUserInfo(localHashMap, new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                Log.i("test", "map = " + map.toString());
                Result = map.get("openid").toString();
            }
        });
        return Result;

    }

    //获取订单号
    public void getOrder(final String money, final PayResultLitener payResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("data", "10");
        OkGo.<String>post(Constant.URL + Constant.GenerateOrderNum)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            ResultGetOrder resultGetOrder = new Gson().fromJson(body, ResultGetOrder.class);
                            if (resultGetOrder.isSuccess()) {
                                wxPay(resultGetOrder.getData(), money, payResultLitener);
                            } else {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            }
                            Log.i(TAG, "getAuthInfo :" + body);

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

    //微信扫脸支付
    private void wxPay(final String outTratNum, final String money, final PayResultLitener payResultLitener) {
        localHashMap = new HashMap();
        localHashMap.put("face_authtype", "FACEPAY");
        localHashMap.put("appid", Constant.appid);
        localHashMap.put("mch_id", Constant.mch_id);
//        localHashMap.put("store_id", store_id);
        localHashMap.put("out_trade_no", outTratNum);
        localHashMap.put("total_fee", money);
        localHashMap.put("ask_face_permit", "1");
        localHashMap.put("ask_ret_page", "1");
//        localHashMap.put("sub_appid", sub_appid);
        localHashMap.put("sub_mch_id", Constant.sub_mch_id);
        if (TextUtils.isEmpty(Constant.authInfo.getAuthinfo())) {
            ToastUtils.showShort("请先获取rawdata");
            return;
        }
        localHashMap.put("authinfo", Constant.authInfo.getAuthinfo());
        Log.i(TAG, "localHashMap" + localHashMap.toString());
        WxPayFace.getInstance().getWxpayfaceCode(localHashMap, new IWxPayfaceCallback() {
            public void response(final Map paramMap) throws RemoteException {
                Log.i("test", "response | getWxpayfaceCode " + paramMap);
                final String code = paramMap.get("return_code").toString();
                if (TextUtils.equals(code, "SUCCESS")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("data1", "10");
                    map.put("data2", money);
                    map.put("data3", outTratNum);
                    map.put("data4", paramMap.get("openid").toString().trim());
                    map.put("data5", paramMap.get("face_code").toString().trim());
                    OkGo.<String>post(Constant.URL + Constant.WechatFacePay)
                            .upJson(new Gson().toJson(map))
                            .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Log.i("test", "response.body() = " + response.body());
                                    try {
                                        ResultFacePay resultFacePay = new Gson().fromJson(response.body(), ResultFacePay.class);
                                        if (resultFacePay.isSuccess()) {
                                            WxPayFace.getInstance().updateWxpayfacePayResult(localHashMap, new IWxPayfaceCallback() {
                                                public void response(Map paramMap) throws RemoteException {
                                                    Log.i(TAG, "paramMap" + paramMap);
                                                    payResultLitener.sucess("");
                                                }
                                            });
                                        } else {
                                            payResultLitener.fail(resultFacePay.getMessage());
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        payResultLitener.fail("网络异常");
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                    payResultLitener.fail("网络异常：" + response.body());
                                }
                            });


                } else if (TextUtils.equals(code, "USER_CANCEL")) {
                    payResultLitener.fail("用户取消");
                } else if (TextUtils.equals(code, "SCAN_PAYMENT")) {
                    payResultLitener.fail("扫码支付");
                }
            }
        });

    }

    //条码支付
    public void getQrCodePay(String qrcode, String money, final PayResultLitener payResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("data1", money);
        localHashMap.put("data2", qrcode);
        OkGo.<String>post(Constant.URL + Constant.posPrePay)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            payResultLitener.sucess(body);
//                            ResultGetOrder resultGetOrder = new Gson().fromJson(body, ResultGetOrder.class);
//                            if (resultGetOrder.isSuccess()) {
//
//                            } else {
//                                ToastUtils.showShort(resultGetOrder.getMessage());
//                            }
                            ToastUtils.showShort(body);
                            Log.i("test", "getAuthInfo :" + body);

                        } catch (Exception e) {
                            e.printStackTrace();
                            payResultLitener.fail("预下单失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        payResultLitener.fail("预下单失败，请重试");
                    }
                });
    }

    //单条会员信息的查询
    public void QueryMember(String codeStr, final PayResultLitener payResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("data", codeStr);
        OkGo.<String>post(Constant.URL + Constant.QueryMember)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            payResultLitener.sucess(resultGetOrder.getMessage());
                            Log.i("test", "getAuthInfo :" + body);
                        } catch (Exception e) {
                            e.printStackTrace();
                            payResultLitener.fail(e.toString());
                            ToastUtils.showShort("查询失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        payResultLitener.fail(response.body());
                    }
                });
    }

    //会员充值
    public void Recharge(String memberid, String money, String auth_code, String face_token, String WechatUnqueId, final PayResultLitener payResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("Id", memberid);
        localHashMap.put("RechargePrice", money);
        localHashMap.put("auth_code", auth_code);
        localHashMap.put("face_token", face_token);
        localHashMap.put("WechatUnqueId", WechatUnqueId);
        OkGo.<String>post(Constant.URL + Constant.Recharge)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            Log.i("test", "getAuthInfo :" + body);
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            payResultLitener.sucess(resultGetOrder.getMessage());

                        } catch (Exception e) {
                            e.printStackTrace();
                            payResultLitener.fail(e.toString());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        payResultLitener.fail(response.body());
                    }
                });

    }

    //解冻0、冻结1
    public void LockOrUnlockMember(String memberid, int IsLocked) {

        localHashMap = new HashMap<>();
        localHashMap.put("Id", memberid);
        localHashMap.put("IsLocked", IsLocked);
        OkGo.<String>post(Constant.URL + Constant.LockOrUnlockMember)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            Log.i("test", "getAuthInfo :" + body);
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            if (resultGetOrder.isSuccess()) {
                                ToastUtils.showShort("操作成功");
                            } else {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort("操作失败");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort("操作失败");
                    }
                });

    }


}
