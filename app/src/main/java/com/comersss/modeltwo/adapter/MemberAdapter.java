package com.comersss.modeltwo.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.MemberListResult;


/**
 * 作者：create by comersss on 2019/5/22 10:44
 * 邮箱：904359289@qq.com
 */
public class MemberAdapter extends BaseQuickAdapter<MemberListResult.DataBeanX.DataBean, BaseViewHolder> {

    public MemberAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberListResult.DataBeanX.DataBean item) {
        helper.setText(R.id.tv_memberid, item.getId() + "");
        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_grade, item.getMemberLevelName());
        helper.setText(R.id.tv_phone, item.getPhoneNum());
        helper.setText(R.id.tv_sex, item.isSex() ? "男" : "女");
        helper.setText(R.id.tv_cost_money, item.getBalance() + "");
        helper.setText(R.id.tv_state, item.isIsLocked() ? "冻结" : "正常");
        helper.addOnClickListener(R.id.tv_member_charge);
        helper.addOnClickListener(R.id.tv_member_screen);

//        helper.setBackgroundColor(R.id.rl, mContext.getResources().getColor(R.color.white));
//        helper.setImageResource(R.id.iv_group, item.getBgImgUnCheck());


    }
}
