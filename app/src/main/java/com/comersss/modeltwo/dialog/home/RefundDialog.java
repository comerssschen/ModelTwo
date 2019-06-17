package com.comersss.modeltwo.dialog.home;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Constant;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.ResultBase;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class RefundDialog extends Dialog {

    private Context mContext;
    private HashMap<Object, Object> localHashMap;
    private EditText et_code;
    private EditText et_pwd;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public RefundDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.refund_dialog);
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);
        TextView right_close = findViewById(R.id.right_close);
        right_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView tv_content = findViewById(R.id.tv_content);
        TextView tv_ok = findViewById(R.id.tv_ok);

        et_code = findViewById(R.id.et_code);
        et_pwd = findViewById(R.id.et_pwd);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refund();
            }
        });
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void refund() {
        String et_codeStr = et_code.getText().toString().trim();
        String et_pwdStr = et_pwd.getText().toString().trim();
        if (ObjectUtils.isEmpty(et_codeStr) || ObjectUtils.isEmpty(et_pwdStr)) {
            ToastUtils.showShort("请输入必填项");
            return;
        }
        localHashMap = new HashMap<>();
        localHashMap.put("data1", et_codeStr);
        localHashMap.put("data2", et_pwdStr);
        OkGo.<String>post(Constant.URL + Constant.Refund)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        dismiss();
                        try {
                            String body = response.body();
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            if (resultGetOrder.isSuccess()) {
                                showSucessDialog(resultGetOrder.getData().toString());
                            } else {
                                showfailDialog();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showfailDialog();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dismiss();
                        showfailDialog();
                    }
                });

    }

    private void showSucessDialog(String money) {
        BigDecimal minMoney = new BigDecimal(money);
        money = minMoney.divide(new BigDecimal("100")).toString();
        SucessDialog dialog = new SucessDialog(mContext, "退款", "退款金额：" + money + "元", "退款成功");
        dialog.show();
    }

    private void showfailDialog() {
        SucessDialog dialog = new SucessDialog(mContext, "退款", "", "退款失败");
        dialog.show();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
