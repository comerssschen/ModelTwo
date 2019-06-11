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
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.MemberListResult;
import com.comersss.modeltwo.dialog.home.QrCodePayDialog;
import com.comersss.modeltwo.dialog.home.SucessDialog;

import java.math.BigDecimal;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 会员充值对话框
 */
public class MemberItemRechargeDialog extends Dialog {

    private Context mContext;
    private MemberListResult.DataBeanX.DataBean memberBean;
    private EditText et_money;
    private String paymoney;
    private QrCodePayDialog qrCodePayDialog;
    private String money;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public MemberItemRechargeDialog(Context context, MemberListResult.DataBeanX.DataBean memberBean) {
        super(context);
        this.mContext = context;
        this.memberBean = memberBean;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_recharge_item_dialog);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        TextView tv_content = findViewById(R.id.tv_content);
        tv_content.setText("姓名： " + memberBean.getName() + "等级：" + memberBean.getMemberLevelName() + "余额：" + memberBean.getBalance() + "元");
        et_money = findViewById(R.id.et_money);
        TextView right_close = findViewById(R.id.right_close);
        right_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        LinearLayout ll_scan = findViewById(R.id.ll_scan);
        LinearLayout ll_qrcode = findViewById(R.id.ll_qrcode);

        ll_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyMoney()) {
                    NetUtil.getInstance().getOrder(1, money, memberBean.getId(), new BaseResultLitener() {
                        @Override
                        public void sucess(String serverRetData) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showSucessDialog(paymoney);
                                    dismiss();
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
                            NetUtil.getInstance().Recharge(memberBean.getId(), paymoney, result, "", "", "", new BaseResultLitener() {
                                @Override
                                public void sucess(String serverRetData) {
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
                        dismiss();
                        qrCodePayDialog.show();
                    }
                }
            }
        });

//        tvCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private boolean verifyMoney() {
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

    private void showSucessDialog(String money) {
        SucessDialog dialog = new SucessDialog(mContext, "充值金额：" + money + "元", "充值成功");
        dialog.show();
    }

    private void showfailDialog() {
        SucessDialog dialog = new SucessDialog(mContext, "", "充值失败");
        dialog.show();
    }

}
