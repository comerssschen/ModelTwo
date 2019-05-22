package com.comersss.modeltwo.dialog.home;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.bumptech.glide.Glide;
import com.comersss.modeltwo.R;

/**
 * 作者：create by comersss on 2019/5/21 11:43
 * 邮箱：904359289@qq.com
 */
public class PayMoneyDialog extends Dialog {

    private Context context;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvButton;

    private String contentStr;
    private String titleStr;
    private String btTextStr;
    private String imgeUrl;
    private ImageView ivImage;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public PayMoneyDialog(Context context, String title, String content, String bttext) {
        super(context);
        this.context = context;
        this.titleStr = title;
        this.contentStr = content;
        this.btTextStr = bttext;
    }

    public void setImage(String url) {
        this.imgeUrl = url;
    }

    public PayMoneyDialog(Context context) {
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
        setContentView(R.layout.paymony_dialog);

        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvButton = findViewById(R.id.tv_button);
        ivImage = findViewById(R.id.iv_image);

        tvTitle.setText(titleStr);
        tvContent.setText(contentStr);
        tvButton.setText(btTextStr);
        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkClickListener != null)
                    onOkClickListener.onOkClick();
            }
        });

        if (!ObjectUtils.isEmpty(imgeUrl)) {
            Glide.with(context).load(imgeUrl).into(ivImage);
        }

        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

}
