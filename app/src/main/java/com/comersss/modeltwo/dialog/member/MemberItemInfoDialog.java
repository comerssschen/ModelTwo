package com.comersss.modeltwo.dialog.member;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.comersss.modeltwo.R;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 会员信息对话框
 */
public class MemberItemInfoDialog extends Dialog {

    private Context mContext;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public MemberItemInfoDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_info_item_dialog);

//        TextView tvConfirm = findViewById(R.id.tv_parm1);
//        TextView tvCancle = findViewById(R.id.tv_parm2);
//
//        tvConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onOkClickListener != null)
//                    onOkClickListener.onOkClick();
//            }
//        });
//        tvCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }


}
