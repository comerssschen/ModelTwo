package com.comersss.modeltwo.dialog.home;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.comersss.modeltwo.R;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class RefundDialog extends Dialog {

    private Context mContext;

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

        TextView tv_content = findViewById(R.id.tv_content);
        TextView tv_ok = findViewById(R.id.tv_ok);

        EditText et_code = findViewById(R.id.et_code);
        EditText et_pwd = findViewById(R.id.et_pwd);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }


}
