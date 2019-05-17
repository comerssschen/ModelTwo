package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/5/15 15:03
 * 邮箱：904359289@qq.com
 */
public class ResultGetOrder {

    /**
     * Data : WX00025201905151455300567375
     * Message : 微信刷脸授权成功
     * Success : true
     * Code : 0
     */

    private String Data;
    private String Message;
    private boolean Success;
    private int Code;

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

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
