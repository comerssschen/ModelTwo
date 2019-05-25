package com.comersss.modeltwo;

import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Listener.GetOpenIdLitener;
import com.comersss.modeltwo.Listener.MemberInfoLitener;
import com.comersss.modeltwo.Listener.MemberLevelLitener;
import com.comersss.modeltwo.Listener.MemberResultLitener;
import com.comersss.modeltwo.Listener.BaseResultLitener;
import com.comersss.modeltwo.Listener.RefreshLitener;
import com.comersss.modeltwo.Listener.TopCountLitener;
import com.comersss.modeltwo.bean.ArgTopCountResult;
import com.comersss.modeltwo.bean.MemberLevelResult;
import com.comersss.modeltwo.bean.MemberListResult;
import com.comersss.modeltwo.bean.MemberResult;
import com.comersss.modeltwo.bean.QrCodePayResult;
import com.comersss.modeltwo.bean.ResultBase;
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

    private String TAG = "test";
    private HashMap<Object, Object> localHashMap;

    //获取微信openid
    public void getOpenId(final GetOpenIdLitener litener) {
        localHashMap = new HashMap();
        localHashMap.put("face_authtype", "FACEID-ONCE");
        localHashMap.put("appid", Constant.appid);
        localHashMap.put("mch_id", Constant.mch_id);
        localHashMap.put("sub_mch_id", Constant.sub_mch_id);
        if (TextUtils.isEmpty(Constant.authInfo.getAuthinfo())) {
            ToastUtils.showShort("请先获取rawdata");
        }
        localHashMap.put("authinfo", Constant.authInfo.getAuthinfo());
        Log.i("test", "localHashMap" + localHashMap.toString());
        WxPayFace.getInstance().getWxpayfaceUserInfo(localHashMap, new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                Log.i("test", "map = " + map.toString());
                litener.sucess(map.get("openid").toString());
            }
        });
    }

    //获取订单号 type 0消费  1充值
    public void getOrder(final int type, final String money, final int memberId, final BaseResultLitener baseResultLitener) {
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
                            Log.i(TAG, "getOrder :" + body);
                            ResultGetOrder resultGetOrder = new Gson().fromJson(body, ResultGetOrder.class);
                            if (resultGetOrder.isSuccess()) {
                                ScanPayType(type, resultGetOrder.getData(), money, memberId, baseResultLitener);
                            } else {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            }


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

    //微信扫脸支付/充值 type0消费1 充值
    private void ScanPayType(final int type, final String outTratNum, final String money, final int memberId, final BaseResultLitener baseResultLitener) {
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
                    String openid = paramMap.get("openid").toString().trim();
                    String face_code = paramMap.get("face_code").toString().trim();
                    if (type == 0) {
                        //直接消费
                        pay(money, outTratNum, openid, face_code, baseResultLitener);
                    } else if (type == 2) {
                        BigDecimal minMoney = new BigDecimal(money);
                        Consume(memberId, minMoney.divide(new BigDecimal("100")).toString(), "", outTratNum, openid, face_code, baseResultLitener);
                    } else {
                        //充值
                        BigDecimal minMoney = new BigDecimal(money);
                        Recharge(memberId, minMoney.divide(new BigDecimal("100")).toString(), "", face_code, openid, outTratNum, baseResultLitener);
                    }

                } else if (TextUtils.equals(code, "USER_CANCEL")) {
                    baseResultLitener.fail("用户取消");
                } else if (TextUtils.equals(code, "SCAN_PAYMENT")) {
                    baseResultLitener.fail("扫码支付");
                }
            }
        });

    }

    private void pay(String money, String outTratNum, String openid, String face_code, final BaseResultLitener baseResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("data1", "10");
        localHashMap.put("data2", money);
        localHashMap.put("data3", outTratNum);
        localHashMap.put("data4", openid);
        localHashMap.put("data5", face_code);
        OkGo.<String>post(Constant.URL + Constant.WechatFacePay)
                .upJson(new Gson().toJson(localHashMap))
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
                                        baseResultLitener.sucess("");
                                    }
                                });
                            } else {
                                baseResultLitener.fail(resultFacePay.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            baseResultLitener.fail("网络异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        baseResultLitener.fail("网络异常：" + response.body());
                    }
                });
    }

    //会员消费
    public void Consume(int memberid, String money, String auth_code, String outTratNum, String openid, String face_code, final BaseResultLitener baseResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("Id", memberid);
        localHashMap.put("RealConsumePrice", money);
        localHashMap.put("face_token", face_code);
        localHashMap.put("out_trade_code", outTratNum);
        localHashMap.put("WechatUnqueId", openid);
        localHashMap.put("auth_code", auth_code);
        OkGo.<String>post(Constant.URL + Constant.Consume)
                .upJson(new Gson().toJson(localHashMap))
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
                                        baseResultLitener.sucess("");
                                    }
                                });
                            } else {
                                baseResultLitener.fail(resultFacePay.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            baseResultLitener.fail("网络异常");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        baseResultLitener.fail("网络异常：" + response.body());
                    }
                });
    }

    //条码支付
    public void getQrCodePay(String qrcode, String money, int memberId, final BaseResultLitener baseResultLitener) {
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
                            QrCodePayResult resultGetOrder = new Gson().fromJson(body, QrCodePayResult.class);
                            if (resultGetOrder.isSuccess()) {
                                baseResultLitener.sucess(body);
                            } else {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            baseResultLitener.fail("支付失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        baseResultLitener.fail("支付失败，请重试");
                    }
                });
    }

    //获取会员列表
    public void getMemberList(int pigeindex, final MemberResultLitener memberResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("pageIndex", pigeindex);
        localHashMap.put("pageSize", 10);
        OkGo.<String>post(Constant.URL + Constant.QueryMembers)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            MemberListResult memberListResult = new Gson().fromJson(body, MemberListResult.class);
                            if (memberListResult.isSuccess()) {
                                memberResultLitener.sucess(memberListResult.getData().getData());
                            } else {
                                memberResultLitener.fail(memberListResult.getMessage());
                            }
                            Log.i("test", "getMemberList :" + body);

                        } catch (Exception e) {
                            e.printStackTrace();
                            memberResultLitener.fail("查询失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        memberResultLitener.fail("查询失败，请重试");
                    }
                });
    }

    //单条会员信息的查询
    public void QueryMember(String codeStr, final MemberInfoLitener litener) {
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
                            Log.i("test", "QueryMember :" + body);
                            MemberResult resultGetOrder = new Gson().fromJson(body, MemberResult.class);
                            if (resultGetOrder.isSuccess()) {
                                litener.sucess(resultGetOrder.getData());
                            } else {
                                litener.fail(resultGetOrder.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            litener.fail(e.toString());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        litener.fail(response.body());
                    }
                });
    }

    //单条会员信息的查询通过微信openid
    public void QueryMemberByOpenId(final MemberInfoLitener baseResultLitener) {
        localHashMap = new HashMap();
        localHashMap.put("face_authtype", "FACEID-ONCE");
        localHashMap.put("appid", Constant.appid);
        localHashMap.put("mch_id", Constant.mch_id);
        localHashMap.put("sub_mch_id", Constant.sub_mch_id);
        if (TextUtils.isEmpty(Constant.authInfo.getAuthinfo())) {
            ToastUtils.showShort("请先获取rawdata");
            return;
        }
        localHashMap.put("authinfo", Constant.authInfo.getAuthinfo());
        Log.i("test", "localHashMap" + localHashMap.toString());
        WxPayFace.getInstance().getWxpayfaceUserInfo(localHashMap, new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                Log.i("test", "map = " + map.toString());
                QueryMember(map.get("openid").toString(), baseResultLitener);
            }
        });


    }

    //会员充值
    public void Recharge(int memberid, String money, String qrcode, final String face_token, final String openId, String outTratNum, final BaseResultLitener baseResultLitener) {
        localHashMap = new HashMap<>();
        localHashMap.put("Id", memberid);
        localHashMap.put("RechargePrice", money);
        localHashMap.put("auth_code", qrcode);
        localHashMap.put("face_token", face_token);
        localHashMap.put("WechatUnqueId", openId);
        localHashMap.put("out_trade_code", outTratNum);
        OkGo.<String>post(Constant.URL + Constant.Recharge)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            Log.i("test", "Recharge :" + body);
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            if (resultGetOrder.isSuccess()) {
                                if (!ObjectUtils.isEmpty(face_token) && !ObjectUtils.isEmpty(openId)) {
                                    WxPayFace.getInstance().updateWxpayfacePayResult(localHashMap, new IWxPayfaceCallback() {
                                        public void response(Map paramMap) throws RemoteException {
                                            Log.i(TAG, "paramMap" + paramMap);
                                            baseResultLitener.sucess("");
                                        }
                                    });
                                } else {
                                    baseResultLitener.sucess(resultGetOrder.getMessage());
                                }
                            } else {
                                baseResultLitener.fail(resultGetOrder.getMessage());
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            baseResultLitener.fail(e.toString());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        baseResultLitener.fail(response.body());
                    }
                });

    }

    //解冻0、冻结1
    public void LockOrUnlockMember(int memberid, int IsLocked, final RefreshLitener litener, final int position) {

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
                            Log.i("test", "LockOrUnlockMember :" + body);
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            if (resultGetOrder.isSuccess()) {
                                if (litener != null) {
                                    litener.refresh(position);
                                }
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

    //获取会员统计
    public void queryMemberStatistics(final TopCountLitener litener) {
        OkGo.<String>post(Constant.URL + Constant.QueryMemberStatistics)
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            ArgTopCountResult resultGetOrder = new Gson().fromJson(body, ArgTopCountResult.class);
                            if (resultGetOrder.isSuccess()) {
                                litener.sucess(resultGetOrder.getData());
                            } else {
                                litener.fail(resultGetOrder.getMessage());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            litener.fail("查询失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        litener.fail("查询失败，请重试");
                    }
                });
    }

    //获取会员统计
    public void QueryMemberLevels(final MemberLevelLitener litener) {
        OkGo.<String>post(Constant.URL + Constant.QueryMemberLevels)
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            MemberLevelResult resultGetOrder = new Gson().fromJson(body, MemberLevelResult.class);
                            if (resultGetOrder.isSuccess()) {
                                litener.sucess(resultGetOrder.getData());
                            } else {
                                litener.fail("查询失败，请重试");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            litener.fail("查询失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        litener.fail("查询失败，请重试");
                    }
                });
    }

}
