package com.comersss.modeltwo.bean;

public class ResultLogin {
    private String Data;
    private String Message;
    private boolean Success;
    private String Code;

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
