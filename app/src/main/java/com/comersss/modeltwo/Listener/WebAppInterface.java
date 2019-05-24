package com.comersss.modeltwo.Listener;

import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.SPUtils;

/**
 * 作者：create by comersss on 2019/5/24 14:02
 * 邮箱：904359289@qq.com
 */
public class WebAppInterface {
    @JavascriptInterface
    public String getToken() {
        return SPUtils.getInstance().getString("token", "");
    }

    @JavascriptInterface
    public void method2(String param1, String param2) {
        // do something
    }
}
