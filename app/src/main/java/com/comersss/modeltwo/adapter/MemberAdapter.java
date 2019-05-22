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
        helper.setText(R.id.memberid, item.getMemberId());

//        helper.setBackgroundColor(R.id.rl, mContext.getResources().getColor(R.color.white));
//        helper.setImageResource(R.id.iv_group, item.getBgImgUnCheck());


    }
}
