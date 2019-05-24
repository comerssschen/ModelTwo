package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/5/24 17:56
 * 邮箱：904359289@qq.com
 */
public class ArgTopCountResult {

    /**
     * Data : {"all":4,"yesterdayDay":0,"lastWeek":1}
     * Message : 操作成功
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
         * all : 4
         * yesterdayDay : 0
         * lastWeek : 1
         */

        private int all;
        private int yesterdayDay;
        private int lastWeek;

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public int getYesterdayDay() {
            return yesterdayDay;
        }

        public void setYesterdayDay(int yesterdayDay) {
            this.yesterdayDay = yesterdayDay;
        }

        public int getLastWeek() {
            return lastWeek;
        }

        public void setLastWeek(int lastWeek) {
            this.lastWeek = lastWeek;
        }
    }
}
