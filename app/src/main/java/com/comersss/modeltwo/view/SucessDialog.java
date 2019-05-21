package com.comersss.modeltwo.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.comersss.modeltwo.R;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class SucessDialog extends Dialog {

    private Context context;
    public CountDownConfrimHelper helper;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvClose;
    private String content;


    public SucessDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void show() {
        super.show();
        if (!ObjectUtils.isEmpty(helper)) {
            helper.stop();
            helper = null;
        }
        helper = new CountDownConfrimHelper(tvClose, 10, 1);
        helper.setOnFinishListener(new CountDownConfrimHelper.OnFinishListener() {
            @Override
            public void fin() {
                close();
            }
        });
        helper.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sucess_dialog);

        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvClose = findViewById(R.id.tv_close);
        tvContent.setText(content);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void close() {
        if (!ObjectUtils.isEmpty(helper)) {
            helper.stop();
        }
        helper = null;
        dismiss();
    }


}
