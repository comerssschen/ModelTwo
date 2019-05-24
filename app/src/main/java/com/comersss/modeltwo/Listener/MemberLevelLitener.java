package com.comersss.modeltwo.Listener;


import com.comersss.modeltwo.bean.MemberLevelResult;

import java.util.List;


/**
 * 作者：create by comersss on 2019/5/23 15:15
 * 邮箱：904359289@qq.com
 */
public abstract class MemberLevelLitener {
    public abstract void sucess( List<MemberLevelResult.DataBean> mberBeanList);

    public abstract void fail(String errMsg);
}
