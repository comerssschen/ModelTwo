package com.comersss.modeltwo.bean;

public class QrCodePayResult {

    /**
     * Data : WX00025201905242157552783432
     * Message :
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
