package com.comersss.modeltwo.dialog.member;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Listener.BaseResultLitener;
import com.comersss.modeltwo.Listener.MemberInfoLitener;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.MemberResult;
import com.comersss.modeltwo.dialog.home.QrCodePayDialog;
import com.comersss.modeltwo.dialog.home.SucessDialog;

import java.math.BigDecimal;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 会员充值对话框
 */
public class MemberRechargeDialog extends Dialog {

    private Context mContext;
    private TextView tv_content;
    private EditText et_code;
    private EditText et_money;
    private QrCodePayDialog qrCodePayDialog;
    private String paymoney;
    private String money;
    private int memberid;

    public MemberRechargeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_recharge_dialog);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        initView();
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void initView() {
        TextView right_close = findViewById(R.id.right_close);
        right_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        et_code = findViewById(R.id.et_code);
        et_money = findViewById(R.id.et_money);
        tv_content = findViewById(R.id.tv_content);
        TextView tv_parm1 = findViewById(R.id.tv_parm1);
        TextView tv_parm2 = findViewById(R.id.tv_parm2);
        LinearLayout ll_scan = findViewById(R.id.ll_scan);
        LinearLayout ll_qrcode = findViewById(R.id.ll_qrcode);

        tv_parm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtil.getInstance().QueryMemberByOpenId(new MemberInfoLitener() {
                    @Override
                    public void sucess(MemberResult.DataBean dataBean) {
                        tv_content.setText("姓名： " + dataBean.getName() + "等级：" + dataBean.getMemberLevelName() + "余额：" + dataBean.getBalance() + "元");
                        memberid = dataBean.getId();
                    }

                    @Override
                    public void fail(String errMsg) {
                        ToastUtils.showShort(errMsg);
                    }
                });
            }
        });
        tv_parm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeStr = et_code.getText().toString().trim();
                if (ObjectUtils.isEmpty(codeStr)) {
                    ToastUtils.showShort("请输入会员唯一识别码");
                    return;
                } else {
                    NetUtil.getInstance().QueryMember(codeStr, new MemberInfoLitener() {
                        @Override
                        public void sucess(MemberResult.DataBean dataBean) {
                            tv_content.setText("姓名： " + dataBean.getName() + "等级：" + dataBean.getMemberLevelName() + "余额：" + dataBean.getBalance() + "元");
                            memberid = dataBean.getId();
                        }

                        @Override
                        public void fail(String errMsg) {

                        }
                    });
                }
            }
        });

        ll_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyMoney()) {
                    NetUtil.getInstance().getOrder(1, money, memberid, new BaseResultLitener() {
                        @Override
                        public void sucess(String serverRetData) {
                            dismiss();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showSucessDialog(paymoney);
                                }
                            });

                        }

                        @Override
                        public void fail(String errMsg) {
                            ToastUtils.showShort(errMsg);
                        }
                    });
                }
            }
        });

        ll_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyMoney()) {
                    qrCodePayDialog = new QrCodePayDialog(getContext(), "会员充值", "充值金额" + paymoney + "元", "请扫描客户条形码充值");
                    qrCodePayDialog.setOnOkClickListener(new QrCodePayDialog.OnOkClickListener() {
                        @Override
                        public void onResult(String result) {
                            NetUtil.getInstance().Recharge(memberid, paymoney, result, "", "", "", new BaseResultLitener() {
                                @Override
                                public void sucess(String serverRetData) {
                                    dismiss();
                                    qrCodePayDialog.dismiss();
                                    showSucessDialog(paymoney);
                                }

                                @Override
                                public void fail(String errMsg) {
                                    qrCodePayDialog.dismiss();
                                    showfailDialog();
                                }
                            });
                        }
                    });
                    if (!qrCodePayDialog.isShowing()) {
                        qrCodePayDialog.setContent(paymoney + "元");
                        qrCodePayDialog.show();
                    }
                }
            }
        });

    }

    private void showSucessDialog(String money) {
        SucessDialog dialog = new SucessDialog(mContext, "充值", "充值金额：" + money + "元", "充值成功");
        dialog.show();
    }

    private void showfailDialog() {
        SucessDialog dialog = new SucessDialog(mContext, "充值", "", "充值失败");
        dialog.show();
    }

    private boolean verifyMoney() {
        if (ObjectUtils.isEmpty(memberid)) {
            ToastUtils.showShort("请选择会员");
            return false;
        }
        paymoney = et_money.getText().toString().trim();
        if (ObjectUtils.isEmpty(paymoney) || TextUtils.equals(paymoney, "0") || TextUtils.equals(paymoney, "0.") || TextUtils.equals(paymoney, "0.0") || TextUtils.equals(paymoney, "0.00")) {
            ToastUtils.showShort("请输入有效金额");
            return false;
        } else if (Double.parseDouble(paymoney) > 100000) {
            ToastUtils.showShort("最大金额99999.99");
            return false;
        } else {
            BigDecimal minMoney = new BigDecimal(paymoney);
            money = minMoney.multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
            return true;
        }
    }

}
