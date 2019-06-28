package com.comersss.modeltwo;

import com.comersss.modeltwo.bean.MemberLevelResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局变量
 * Created by comersss on 16/12/23.
 */
public class Constant {

    public static final String URL = "http://api.pay.360yunpay.com";//正式
    public static final String DateURL = "http://api.pay.inxuetong.com/html/Data.html";//正式
    public static final String SettingURL = "http://api.pay.inxuetong.com/html/SetUp.html";//正式
    //    public static final String URL = "http://api.test.360yunpay.com";//测试
    public static String appid = "wx2b975c0ca94a6154";
    public static String mch_id = "1440771702";
    public static String sub_mch_id = "1531015161";
    public static AuthInfo authInfo = new AuthInfo();
    public static List<MemberLevelResult.DataBean> mberLevelList = new ArrayList<>();

    //获取刷脸参数
    public static final String GetSubMerchantId = "/api/PayApi/GetSubMerchantId";

    //会员消费
    public static final String Consume = "/api/MemberApi/Consume";

    //获取会员等级列表
    public static final String QueryMemberLevels = "/api/MemberApi/QueryMemberLevels";

    //获取订单号
    public static final String GenerateOrderNum = "/api/PayApi/GenerateOrderNum";

    //获取authinfo
    public static final String WechatFaceAuth = "/api/PayApi/WechatFaceAuth";

    //人脸支付
    public static final String WechatFacePay = "/api/PayApi/WechatFacePay";

    //登陆
    public static final String Login = "/api/PayApi/Login";

    //登出
    public static final String Logout = "/api/PayApi/Logout";

    //条码支付统一接口
    public static final String posPrePay = "/api/PayApi/BarcodePay";

    //退款
    public static final String Refund = "/api/PayApi/Refund";

    //单条订单查询
    public static final String OrderQuery = "/api/PayApi/OrderQuery";

    //收银员信息查询
    public static final String QueryCasherInfo = "/api/PayApi/QueryCasherInfo";

    //门店信息查询get
    public static final String QueryStoreInfo = "/api/PayApi/QueryStoreInfo";

    //添加会员信息
    public static final String AddMember = "/api/MemberApi/AddMember";

    //修改会员信息
    public static final String UpdateMember = "/api/MemberApi/UpdateMember";

    //会员状态修改
    public static final String LockOrUnlockMember = "/api/MemberApi/LockOrUnlockMember";

    //单条会员信息的查询
    public static final String QueryMember = "/api/MemberApi/QueryMember";

    //会员充值记录列表
    public static final String RechargeLog = "/api/MemberApi/RechargeLog";

    //会员消费记录列表
    public static final String ConsumeLog = "/api/MemberApi/ConsumeLog";

    //会员充值
    public static final String Recharge = "/api/MemberApi/Recharge";

    //获取会员统计
    public static final String QueryMemberStatistics = "/api/MemberApi/QueryMemberStatistics";

    //获取订单统计
    public static final String OrderStatistics = "/api/MemberApi/OrderStatistics";

    //会员分页列表
    public static final String QueryMembers = "/api/MemberApi/QueryMembers";

}
