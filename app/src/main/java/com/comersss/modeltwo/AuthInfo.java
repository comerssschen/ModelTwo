package com.comersss.modeltwo;

/**
 * 作者：create by comersss on 2019/5/17 17:42
 * 邮箱：904359289@qq.com
 */
public class AuthInfo {
    public String authinfo;
    public long expire_time;

    public String getAuthinfo() {
        return authinfo;
    }

    public void setAuthinfo(String authinfo) {
        this.authinfo = authinfo;
    }

    public long getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(long expire_time) {
        this.expire_time = expire_time;
    }
}
