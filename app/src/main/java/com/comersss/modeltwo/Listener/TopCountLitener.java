package com.comersss.modeltwo.Listener;


import com.comersss.modeltwo.bean.ArgTopCountResult;


/**
 * 作者：create by comersss on 2019/5/23 15:15
 * 邮箱：904359289@qq.com
 */
public abstract class TopCountLitener {
    public abstract void sucess(ArgTopCountResult.DataBean memberBeanList);

    public abstract void fail(String errMsg);
}
