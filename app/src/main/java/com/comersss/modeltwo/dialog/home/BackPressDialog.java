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

import com.comersss.modeltwo.R;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class BackPressDialog extends Dialog {

    private Context mContext;
    private String titleStr;
    private String contentStr;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public interface OnCancleClickListener {
        void onOkClick();
    }

    private OnCancleClickListener onCancleClickListener;

    public void setOnCancleClickListener(OnCancleClickListener onOkClickListener) {
        this.onCancleClickListener = onOkClickListener;
    }

    public BackPressDialog(Context context, String titleStr, String contentStr) {
        super(context);
        this.mContext = context;
        this.titleStr = titleStr;
        this.contentStr = contentStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.backpress_dialog);
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
        TextView tv_title = findViewById(R.id.tv_title);
        tv_content.setText(contentStr);
        tv_title.setText(titleStr);
        TextView tvConfirm = findViewById(R.id.tv_confirm);
        TextView tvCancle = findViewById(R.id.tv_cancle);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkClickListener != null)
                    onOkClickListener.onOkClick();
            }
        });
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancleClickListener != null)
                    onCancleClickListener.onOkClick();
            }
        });
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }


}
