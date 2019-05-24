package com.comersss.modeltwo.Listener;


/**
 * 作者：create by comersss on 2019/5/23 15:15
 * 邮箱：904359289@qq.com
 */
public abstract class BaseResultLitener {
    public abstract void sucess(String serverRetData);

    public abstract void fail(String errMsg);
}
