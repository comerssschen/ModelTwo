package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/5/23 10:21
 * 邮箱：904359289@qq.com
 */
public class ResultBase {

    /**
     * Message : 操作失败
     * Success : false
     * Code : 0
     */

    private String Message;
    private boolean Success;
    private int Code;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }
}
