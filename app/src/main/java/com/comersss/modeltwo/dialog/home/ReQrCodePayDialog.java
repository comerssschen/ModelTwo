package com.comersss.modeltwo.dialog.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.comersss.modeltwo.Constant;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.QrCodePayResult;
import com.comersss.modeltwo.bean.ResultLogin;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

/**
 * 作者：create by comersss on 2019/5/21 11:43
 * 邮箱：904359289@qq.com
 */
public class ReQrCodePayDialog extends Dialog {

    private Context context;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvButton;

    private String contentStr;
    private String titleStr;
    private String btTextStr;
    private String imgeUrl;
    private String money;
    private ImageView ivImage;
    private EditText etScan;
    private CountDownTimer timer;
    private FrameLayout frameLayout;

    public interface OnOkClickListener {
        void onsucess();

        void onfaile();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public ReQrCodePayDialog(Context context, String title, String content, String money, String bttext) {
        super(context);
        this.context = context;
        this.titleStr = title;
        this.contentStr = content;
        this.btTextStr = bttext;
        this.money = money;
    }

    public void setContent(String content) {
        this.contentStr = content;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.qrcode_pay_dialog);
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogWindow.setGravity(Gravity.CENTER);
        frameLayout = findViewById(R.id.framelayout);
        etScan = findViewById(R.id.et_scan);
        etScan.requestFocus();
        etScan.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null) {
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                if (!TextUtils.isEmpty(etScan.getText())) {
                    handler.postDelayed(delayRun, 100);
                }
            }
        });
        TextView right_close = findViewById(R.id.right_close);
        right_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvButton = findViewById(R.id.tv_button);
        ivImage = findViewById(R.id.iv_image);

        tvTitle.setText(titleStr);
        tvContent.setText(contentStr);
        tvButton.setText(btTextStr);
        if (!ObjectUtils.isEmpty(imgeUrl)) {
            Glide.with(context).load(imgeUrl).into(ivImage);
        }

        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            if (onOkClickListener != null) {
                pay(etScan.getText().toString().replace(" ", "").replace("\n", ""));
            }
            etScan.setText("");
        }
    };

    private void dissmissdialog() {
        frameLayout.setVisibility(View.GONE);
        if (!ObjectUtils.isEmpty(timer)) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (frameLayout.getVisibility() == View.VISIBLE) {
            frameLayout.setVisibility(View.GONE);
        } else {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (!ObjectUtils.isEmpty(timer)) {
            timer.cancel();
            timer = null;
        }
    }

    private void pay(String result) {
        frameLayout.setVisibility(View.VISIBLE);
        HashMap<Object, Object> localHashMap = new HashMap<>();
        localHashMap.put("data1", money);
        localHashMap.put("data2", result);
        OkGo.<String>post(Constant.URL + Constant.posPrePay)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            final QrCodePayResult resultGetOrder = new Gson().fromJson(body, QrCodePayResult.class);
                            if (resultGetOrder.isSuccess()) {
                                if (onOkClickListener != null)
                                    dissmissdialog();
                                onOkClickListener.onsucess();
                            } else {
                                if (resultGetOrder.getCode() == 0 && resultGetOrder.getData().getTrade_status() == 6) {
                                    timer = new CountDownTimer(60000, 2000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            //在计时器中轮询支付结果：每秒查询一次支付结果
                                            OkGo.<String>get(Constant.URL + Constant.OrderQuery + "?orderNum=" + resultGetOrder.getData().getOut_trade_no()).execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {
                                                    try {
                                                        ResultLogin resultLogin = new Gson().fromJson(response.body(), ResultLogin.class);
                                                        if (ObjectUtils.equals(resultLogin.getData(), "3")) {
                                                            dissmissdialog();
                                                            onOkClickListener.onsucess();
                                                        } else if (ObjectUtils.equals(resultLogin.getData(), "5") || ObjectUtils.equals(resultLogin.getData(), "1")) {
                                                            dissmissdialog();
                                                            onOkClickListener.onfaile();
                                                        }
                                                        Log.i("test", " response.body() = " + response.body());
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFinish() {
                                            dissmissdialog();
                                            //倒数到0时的操作，一般认为倒数到0仍未收到支付结果，则认为支付失败，页面跳转
                                            ToastUtils.showShort("支付失败，请重试");
                                        }
                                    };
                                    timer.start();
                                } else {
                                    dissmissdialog();
                                    ToastUtils.showShort(resultGetOrder.getMessage());
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            dissmissdialog();
                            ToastUtils.showShort("支付失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        dissmissdialog();
                        ToastUtils.showShort("支付失败，请重试");
                    }
                });

    }

}
