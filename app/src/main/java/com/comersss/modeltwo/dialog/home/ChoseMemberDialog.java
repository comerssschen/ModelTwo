package com.comersss.modeltwo.dialog.home;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Listener.MemberInfoLitener;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.MemberResult;


/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 */
public class ChoseMemberDialog extends Dialog {

    private Context mContext;
    private MemberResult.DataBean mDataBean;
    private EditText et_code;
    private TextView tv_content;

    public interface OnOkClickListener {
        void onOkClick(MemberResult.DataBean bean);
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
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        TextView right_close = findViewById(R.id.right_close);
        right_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
                dismiss();
                if (onOkClickListener != null && !ObjectUtils.isEmpty(mDataBean)) {
                    onOkClickListener.onOkClick(mDataBean);
                }

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
                    NetUtil.getInstance().QueryMember(codeStr, new MemberInfoLitener() {
                        @Override
                        public void sucess(MemberResult.DataBean dataBean) {
                            mDataBean = dataBean;
                            tv_content.setText("姓名： " + dataBean.getName() + "等级：" + dataBean.getMemberLevelName() + "余额：" + dataBean.getBalance() + "元");
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
                NetUtil.getInstance().QueryMemberByOpenId(new MemberInfoLitener() {
                    @Override
                    public void sucess(MemberResult.DataBean dataBean) {
                        mDataBean = dataBean;
                        tv_content.setText("姓名： " + dataBean.getName() + "等级：" + dataBean.getMemberLevelName() + "余额：" + dataBean.getBalance() + "元");
                    }

                    @Override
                    public void fail(String errMsg) {

                    }
                });


            }
        });
    }


}
