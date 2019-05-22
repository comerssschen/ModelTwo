package com.comersss.modeltwo.bean;

/**
 * 作者：create by comersss on 2019/5/22 10:45
 * 邮箱：904359289@qq.com
 */
public class MemberBean {

    public MemberBean(String memberId) {
        this.memberId = memberId;
    }

    private String memberId;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
