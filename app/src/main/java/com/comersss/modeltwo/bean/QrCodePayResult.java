package com.comersss.modeltwo.bean;

public class QrCodePayResult {

    /**
     * Data : {"out_trade_no":"WX00001201906130940178637161","trade_status":6}
     * Message :
     * Success : false
     * Code : 0
     */

    private DataBean Data;
    private String Message;
    private boolean Success;
    private int Code;

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
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

    public static class DataBean {
        /**
         * out_trade_no : WX00001201906130940178637161
         * trade_status : 6
         */

        private String out_trade_no;
        private int trade_status;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public int getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(int trade_status) {
            this.trade_status = trade_status;
        }
    }
}
