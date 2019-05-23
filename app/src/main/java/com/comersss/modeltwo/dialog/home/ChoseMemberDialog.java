package com.comersss.modeltwo.dialog.home;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Listener.PayResultLitener;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;

import java.util.HashMap;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class ChoseMemberDialog extends Dialog {

    private Context mContext;
    private HashMap localHashMap;
    private EditText et_code;
    private TextView tv_content;

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

        tv_content = findViewById(R.id.tv_content);
        TextView tv_parm1 = findViewById(R.id.tv_parm1);
        TextView tv_parm2 = findViewById(R.id.tv_parm2);
        TextView tv_ok = findViewById(R.id.tv_ok);

        et_code = findViewById(R.id.et_code);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkClickListener != null)
                    onOkClickListener.onOkClick();
            }
        });

        tv_parm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeStr = et_code.getText().toString().trim();
                if (ObjectUtils.isEmpty(codeStr)) {
                    ToastUtils.showShort("请输入会员唯一识别码");
                    return;
                } else {
                    NetUtil.getInstance().QueryMember(codeStr, new PayResultLitener() {
                        @Override
                        public void sucess(String serverRetData) {
                            tv_content.setText(serverRetData);
                        }

                        @Override
                        public void fail(String errMsg) {

                        }
                    });
                }
            }
        });
        //刷脸识别会员
        tv_parm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtil.getInstance().QueryMemberByOpenId(new PayResultLitener() {
                    @Override
                    public void sucess(String serverRetData) {
                        tv_content.setText(serverRetData);
                    }

                    @Override
                    public void fail(String errMsg) {

                    }
                });


            }
        });
    }


}
