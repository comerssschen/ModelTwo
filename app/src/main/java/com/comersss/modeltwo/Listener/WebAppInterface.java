package com.comersss.modeltwo.Listener;

import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.SPUtils;

/**
 * 作者：create by comersss on 2019/5/24 14:02
 * 邮箱：904359289@qq.com
 */
public class WebAppInterface {
    @JavascriptInterface
    public String GetToken() {
        return SPUtils.getInstance().getString("token", "");
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
