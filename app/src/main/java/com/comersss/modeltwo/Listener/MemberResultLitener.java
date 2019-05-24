package com.comersss.modeltwo.Listener;


import com.comersss.modeltwo.bean.MemberListResult;

import java.util.List;

/**
 * 作者：create by comersss on 2019/5/23 15:15
 * 邮箱：904359289@qq.com
 */
public abstract class MemberResultLitener {
    public abstract void sucess(List<MemberListResult.DataBeanX.DataBean> memberBeanList);

    public abstract void fail(String errMsg);
}
