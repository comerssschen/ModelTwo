package com.comersss.modeltwo.Listener;

import android.os.Message;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.bean.ResultBase;
import com.comersss.modeltwo.bean.ResultGetOrder;
import com.google.gson.Gson;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;

import java.util.HashMap;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * 作者：create by comersss on 2019/5/24 14:02
 * 邮箱：904359289@qq.com
 */
public class WebAppInterface {

    private final WebView mWebView;

    public WebAppInterface(com.tencent.smtt.sdk.WebView mWebView) {
        this.mWebView = mWebView;
    }

    @JavascriptInterface
    public String GetToken() {
        return SPUtils.getInstance().getString("token", "");
    }

    @JavascriptInterface
    public void getOpenId() {
        NetUtil.getInstance().getOpenId(new GetOpenIdLitener() {
            @Override
            public void sucess(final String openid) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.evaluateJavascript("javascript:openIdResult('" + new Gson().toJson(new ResultBase("认证成功", true, 0, openid)) + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.i("test", "onReceiveValue  + " + s);
                            }
                        });
                    }
                });

            }

            @Override
            public void fail(final String errMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.evaluateJavascript("javascript:openIdResult('" + new Gson().toJson(new ResultBase("认证失败", false, 0, errMsg)) + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.i("test", "onReceiveValue  + " + s);
                            }
                        });
                    }
                });
            }
        });


    }

    @JavascriptInterface
    public void FacePay(String money) {
        NetUtil.getInstance().getOrder(0, money, 0, new BaseResultLitener() {
            @Override
            public void sucess(final String serverRetData) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.evaluateJavascript("javascript:facepayResult('" + new Gson().toJson(new ResultBase("刷脸支付成功", true, 0, serverRetData)) + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.i("test", "onReceiveValue  + " + s);
                            }
                        });
                    }
                });
            }

            @Override
            public void fail(final String errMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.evaluateJavascript("javascript:facepayResult('" + new Gson().toJson(new ResultBase("刷脸支付失败", false, 0, errMsg)) + "')", new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String s) {
                                Log.i("test", "onReceiveValue  + " + s);
                            }
                        });
                    }
                });
            }
        });
    }

    @JavascriptInterface
    public String GetDevice() {
        return "APP";
    }

    @JavascriptInterface
    public String GetOption() {
        return "";
    }

    @JavascriptInterface
    public void SaveOption(String option) {
        return;
    }

    @JavascriptInterface
    public void Recv() {
        return;
    }

    @JavascriptInterface
    public void Member() {
        return;
    }

    @JavascriptInterface
    public void Statistics() {
        return;
    }

    @JavascriptInterface
    public void Setting() {
        return;
    }

    @JavascriptInterface
    public void ExitApp() {
        return;
    }
}
