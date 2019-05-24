package com.comersss.modeltwo.dialog.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.comersss.modeltwo.R;

/**
 * 作者：create by comersss on 2019/5/21 11:43
 * 邮箱：904359289@qq.com
 */
public class QrCodePayDialog extends Dialog {

    private Context context;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvButton;

    private String contentStr;
    private String titleStr;
    private String btTextStr;
    private String imgeUrl;
    private ImageView ivImage;
    private EditText etScan;

    public interface OnOkClickListener {
        void onResult(String result);
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public QrCodePayDialog(Context context, String title, String content, String bttext) {
        super(context);
        this.context = context;
        this.titleStr = title;
        this.contentStr = content;
        this.btTextStr = bttext;
    }

    public void setImage(String url) {
        this.imgeUrl = url;
    }

    public QrCodePayDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setbtText(String bttext) {
        this.btTextStr = bttext;
    }

    public void setTitle(String title) {
        this.titleStr = title;
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
        dialogWindow.setGravity(Gravity.CENTER);
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
            if (onOkClickListener != null)
                onOkClickListener.onResult(etScan.getText().toString().replace(" ", "").replace("\n", ""));
            etScan.setText("");
        }
    };

}
