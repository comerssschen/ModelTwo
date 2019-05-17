package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/4/11 11:29
 * 邮箱：904359289@qq.com
 */
public class ResultFacePay {


    /**
     * Data : {"Success":true,"msg":"SUCCESS","out_trade_no":"WX00025201905151611245065923","trade_no":null,"prepay_id":"4200000295201905157213360903","transaction_id":null,"appId":null,"refund_id":null,"total_fee":0,"trade_status":0}
     * Message : 微信刷脸支付成功
     * Success : true
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
         * Success : true
         * msg : SUCCESS
         * out_trade_no : WX00025201905151611245065923
         * trade_no : null
         * prepay_id : 4200000295201905157213360903
         * transaction_id : null
         * appId : null
         * refund_id : null
         * total_fee : 0
         * trade_status : 0
         */

        private boolean Success;
        private String msg;
        private String out_trade_no;
        private String trade_no;
        private String prepay_id;
        private String transaction_id;
        private String appId;
        private String refund_id;
        private int total_fee;
        private int trade_status;

        public boolean isSuccess() {
            return Success;
        }

        public void setSuccess(boolean Success) {
            this.Success = Success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getRefund_id() {
            return refund_id;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }

        public int getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(int total_fee) {
            this.total_fee = total_fee;
        }

        public int getTrade_status() {
            return trade_status;
        }

        public void setTrade_status(int trade_status) {
            this.trade_status = trade_status;
        }
    }
}
