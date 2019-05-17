package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/4/11 10:57
 * 邮箱：904359289@qq.com
 */
public class ArgGetAuthInfo {

    /**
     * Data : {"return_code":"SUCCESS","return_msg":"请求成功","nonce_str":"SgA8e8LuI7qiUWER221KnLfEQG8IJT2z","sign":"4C87E910BA0590D7CC4A727CE295417E","appid":"wx2b975c0ca94a6154","mch_id":"1440771702","sub_appid":null,"sub_mch_id":"1531015161","authinfo":"QJ357p4I2IAs6SSA5PeUh8Bl30W6cN6b8cRVXy4tzfb9iAkxSfS7zaAW6SvBX8oQ6K3krN38/eJjCeMHNeaJ9fdSsXtIdtXR57S8ozWK+Ymg33qCW3MckmuKHcY1lNHngQ0a4Kk9ZdXwqTayxIzBesJJ8MA6f/yGm5JOUF27mfH1jaMzkfpCAGsFgMWxD+wVfLU2hKvvWVh70JII64beXKJwZkkt/GUKUhdy+S5IdU0B2ya1yLZwELxXyHdskcu3HsjXOMp60pRBdbcsnCup0xo5J3kFXGgSvAYEtQUw6/Qv2h5AzkTzoWLYDLfB5RgoT4ImY8QIWXQShuMrZvMn6AI7Z5FPncXSvdTq30Zg/YWur0t774wvPkBTcoI/djRNf9W87cFKo2QK7UMcy7pj1h/UNyYHI2sC/nMGeZZx4iFjJvGUVIU/C/b/hFn30/98myeHIOTJy+MRHwVlDLuiUb06PNKKox7mDqFVIdk4bWJABAKmuqyAuLGD7doDvLMaSr3+NH8W1rIHpQevnWZ70uRGkQhcBB4MgcRCYwhzqHXQsIKwBit+CaC7YGJwedHr2opof4428Ypxi9SXNVzazNs0p844DRvWjJ90dhECHEctD64vS/HxcYn+GLu/Lk6t8bL7GFgN8J0gqw==","expires_in":"3600"}
     * Message : 微信刷脸授权成功
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
         * return_code : SUCCESS
         * return_msg : 请求成功
         * nonce_str : SgA8e8LuI7qiUWER221KnLfEQG8IJT2z
         * sign : 4C87E910BA0590D7CC4A727CE295417E
         * appid : wx2b975c0ca94a6154
         * mch_id : 1440771702
         * sub_appid : null
         * sub_mch_id : 1531015161
         * authinfo : QJ357p4I2IAs6SSA5PeUh8Bl30W6cN6b8cRVXy4tzfb9iAkxSfS7zaAW6SvBX8oQ6K3krN38/eJjCeMHNeaJ9fdSsXtIdtXR57S8ozWK+Ymg33qCW3MckmuKHcY1lNHngQ0a4Kk9ZdXwqTayxIzBesJJ8MA6f/yGm5JOUF27mfH1jaMzkfpCAGsFgMWxD+wVfLU2hKvvWVh70JII64beXKJwZkkt/GUKUhdy+S5IdU0B2ya1yLZwELxXyHdskcu3HsjXOMp60pRBdbcsnCup0xo5J3kFXGgSvAYEtQUw6/Qv2h5AzkTzoWLYDLfB5RgoT4ImY8QIWXQShuMrZvMn6AI7Z5FPncXSvdTq30Zg/YWur0t774wvPkBTcoI/djRNf9W87cFKo2QK7UMcy7pj1h/UNyYHI2sC/nMGeZZx4iFjJvGUVIU/C/b/hFn30/98myeHIOTJy+MRHwVlDLuiUb06PNKKox7mDqFVIdk4bWJABAKmuqyAuLGD7doDvLMaSr3+NH8W1rIHpQevnWZ70uRGkQhcBB4MgcRCYwhzqHXQsIKwBit+CaC7YGJwedHr2opof4428Ypxi9SXNVzazNs0p844DRvWjJ90dhECHEctD64vS/HxcYn+GLu/Lk6t8bL7GFgN8J0gqw==
         * expires_in : 3600
         */

        private String return_code;
        private String return_msg;
        private String nonce_str;
        private String sign;
        private String appid;
        private String mch_id;
        private Object sub_appid;
        private String sub_mch_id;
        private String authinfo;
        private String expires_in;

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public Object getSub_appid() {
            return sub_appid;
        }

        public void setSub_appid(Object sub_appid) {
            this.sub_appid = sub_appid;
        }

        public String getSub_mch_id() {
            return sub_mch_id;
        }

        public void setSub_mch_id(String sub_mch_id) {
            this.sub_mch_id = sub_mch_id;
        }

        public String getAuthinfo() {
            return authinfo;
        }

        public void setAuthinfo(String authinfo) {
            this.authinfo = authinfo;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }
    }
}
