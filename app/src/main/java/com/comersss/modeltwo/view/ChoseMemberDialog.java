package com.comersss.modeltwo.view;


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
public class ChoseMemberDialog extends Dialog {

    private Context mContext;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public ChoseMemberDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chose_member_dialog);

        TextView tv_content = findViewById(R.id.tv_content);
        TextView tv_parm1 = findViewById(R.id.tv_parm1);
        TextView tv_parm2 = findViewById(R.id.tv_parm2);
        TextView tv_ok = findViewById(R.id.tv_ok);

        EditText et_code = findViewById(R.id.et_code);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }


}
