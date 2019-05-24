package com.comersss.modeltwo.dialog.member;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.MemberListResult;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 会员信息对话框
 */
public class MemberItemInfoDialog extends Dialog {

    private Context mContext;
    private MemberListResult.DataBeanX.DataBean memberBean;

    public MemberItemInfoDialog(Context context, MemberListResult.DataBeanX.DataBean memberBean) {
        super(context);
        this.mContext = context;
        this.memberBean = memberBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_info_item_dialog);
        initView();
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void initView() {
        TextView tv_dongjie = findViewById(R.id.tv_dongjie);
        TextView tv_jiedong = findViewById(R.id.tv_jiedong);
        TextView tv_update = findViewById(R.id.tv_update);

        tv_dongjie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtil.getInstance().LockOrUnlockMember(memberBean.getId()+"", 1);
            }
        });
        tv_jiedong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtil.getInstance().LockOrUnlockMember(memberBean.getId()+"", 0);
            }
        });

        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemberAddDialog memberAddDialog = new MemberAddDialog(mContext, memberBean);
                memberAddDialog.show();
            }
        });

    }


}
