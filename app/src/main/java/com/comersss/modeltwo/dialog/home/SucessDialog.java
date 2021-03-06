package com.comersss.modeltwo.dialog.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.view.CountDownConfrimHelper;

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
    private String title;
    private String stateStr;
    private TextView tvState;

    public SucessDialog(Context context, String title, String content, String stateStr) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        this.stateStr = stateStr;
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
        helper = new CountDownConfrimHelper(tvClose, 5, 1);
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
        tvState = findViewById(R.id.tv_state);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvClose = findViewById(R.id.tv_close);
        tvContent.setText(content);
        tvTitle.setText(title);
        tvState.setText(stateStr);
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
