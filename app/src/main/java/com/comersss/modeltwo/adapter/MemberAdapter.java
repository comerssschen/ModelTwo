package com.comersss.modeltwo.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.MemberBean;


/**
 * 作者：create by comersss on 2019/5/22 10:44
 * 邮箱：904359289@qq.com
 */
public class MemberAdapter extends BaseQuickAdapter<MemberBean, BaseViewHolder> {

    public MemberAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberBean item) {
        helper.setText(R.id.tv_memberid, item.getId());
        helper.setText(R.id.tv_name, item.getId());
        helper.setText(R.id.tv_grade, item.getId());
        helper.setText(R.id.tv_phone, item.getId());
        helper.setText(R.id.tv_intergral, item.getId());
        helper.setText(R.id.tv_cost_money, item.getId());
        helper.setText(R.id.tv_remain_money, item.getId());
        helper.setText(R.id.tv_time, item.getId());
        helper.setText(R.id.tv_state, item.getId());

        helper.addOnClickListener(R.id.tv_member_charge);
        helper.addOnClickListener(R.id.tv_member_screen);

//        helper.setBackgroundColor(R.id.rl, mContext.getResources().getColor(R.color.white));
//        helper.setImageResource(R.id.iv_group, item.getBgImgUnCheck());


    }
}
