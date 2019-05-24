package com.comersss.modeltwo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.EditTextUtils;
import com.comersss.modeltwo.Listener.BaseResultLitener;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.MemberResult;
import com.comersss.modeltwo.dialog.home.ChoseMemberDialog;
import com.comersss.modeltwo.dialog.home.PayMoneyDialog;
import com.comersss.modeltwo.dialog.home.QrCodePayDialog;
import com.comersss.modeltwo.dialog.home.RefundDialog;
import com.comersss.modeltwo.dialog.home.SucessDialog;
import com.tencent.wxpayface.WxPayFace;

import java.math.BigDecimal;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "test";
    @BindView(R.id.paymoney_edit)
    EditText paymoneyEdit;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.ll_qrcode)
    LinearLayout llQrcode;
    Unbinder unbinder;
    private String paymoney;
    private String money;
    private int memberid = 0;

    private SucessDialog sucessDialog;
    private View cashFragmentView;
    private PayMoneyDialog payMoneyDialog;
    private QrCodePayDialog qrCodePayDialog;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "createView");
        cashFragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        return cashFragmentView;
    }

    //初始化界面
    protected void initView(View mChildContentView, Bundle savedInstanceState) {
        Log.i(TAG, "initView");
        unbinder = ButterKnife.bind(this, mChildContentView);
        EditTextUtils.setPriceEditText(paymoneyEdit);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            payMoneyDialog.dismiss();
            if (ObjectUtils.isEmpty(sucessDialog)) {
                sucessDialog = new SucessDialog(mContext, "收款金额：" + paymoney + "元", "收款成功");
            }
            if (!sucessDialog.isShowing()) {
                sucessDialog.show();
            }
            paymoneyEdit.setText("");
            memberid = 0;
            tvContent.setText("");
        }
    };


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.i("test", "切换到了cashfragment");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (sucessDialog != null && sucessDialog.isShowing()) {
            sucessDialog.dismiss();
        }
        WxPayFace.getInstance().releaseWxpayface(getContext());
    }

    @OnClick({R.id.tv_member, R.id.tv_refund, R.id.ll_scan, R.id.ll_qrcode, R.id.soft_keyboard_btn_1, R.id.soft_keyboard_btn_2, R.id.soft_keyboard_btn_3, R.id.soft_keyboard_btn_4, R.id.soft_keyboard_btn_5, R.id.soft_keyboard_btn_6, R.id.soft_keyboard_btn_7, R.id.soft_keyboard_btn_8, R.id.soft_keyboard_btn_9, R.id.tv_point, R.id.soft_keyboard_btn_0, R.id.rl_delete})
    public void onViewClicked(View view) {
        Editable editable = paymoneyEdit.getText();
        int start = paymoneyEdit.getSelectionStart();
        switch (view.getId()) {
            case R.id.tv_member:
                ChoseMemberDialog choseMemberDialog = new ChoseMemberDialog(getContext());
                choseMemberDialog.setOnOkClickListener(new ChoseMemberDialog.OnOkClickListener() {
                    @Override
                    public void onOkClick(MemberResult.DataBean dataBean) {
                        memberid = dataBean.getId();
                        tvContent.setText("姓名： " + dataBean.getName() + "  余额：" + dataBean.getBalance() + "元");
                    }
                });
                choseMemberDialog.show();
                break;
            case R.id.tv_refund:
                RefundDialog refundDialog = new RefundDialog(getContext());
                refundDialog.show();
                break;
            case R.id.ll_scan:
                if (verifyMoney()) {
//                    if (ObjectUtils.isEmpty(payMoneyDialog)) {
                    payMoneyDialog = new PayMoneyDialog(getContext(), "刷脸", paymoney + "元", "请刷脸收款");
                    payMoneyDialog.setOnOkClickListener(new PayMoneyDialog.OnOkClickListener() {
                        @Override
                        public void onOkClick() {
                            NetUtil.getInstance().getOrder(0, money, memberid, new BaseResultLitener() {
                                @Override
                                public void sucess(String serverRetData) {
                                    Message message = Message.obtain(mHandler);
                                    message.what = 0;
                                    mHandler.sendMessage(message);
                                }

                                @Override
                                public void fail(String errMsg) {
                                    ToastUtils.showShort(errMsg);
                                }
                            });
                        }
                    });
//                    }
//                    if (!payMoneyDialog.isShowing()) {
                    payMoneyDialog.setContent(paymoney + "元");
                    payMoneyDialog.show();
//                    }
                }
                break;
            case R.id.ll_qrcode:
                if (verifyMoney()) {
                    qrCodePayDialog = new QrCodePayDialog(getContext(), "扫码", paymoney + "元", "请扫码收款");
                    qrCodePayDialog.setOnOkClickListener(new QrCodePayDialog.OnOkClickListener() {
                        @Override
                        public void onResult(String result) {
                            if (memberid == 0) {
                                NetUtil.getInstance().getQrCodePay(result, money, memberid, new BaseResultLitener() {
                                    @Override
                                    public void sucess(String serverRetData) {
                                        qrCodePayDialog.dismiss();
                                        if (ObjectUtils.isEmpty(sucessDialog)) {
                                            sucessDialog = new SucessDialog(mContext, "收款金额：" + paymoney + "元", "收款成功");
                                        }
                                        if (!sucessDialog.isShowing()) {
                                            sucessDialog.show();
                                        }
                                        paymoneyEdit.setText("");
                                        memberid = 0;
                                        tvContent.setText("");
                                    }

                                    @Override
                                    public void fail(String errMsg) {
                                        ToastUtils.showShort(errMsg);
                                    }
                                });
                            } else {
                                NetUtil.getInstance().Consume(memberid, money, result, "", "", "", new BaseResultLitener() {
                                    @Override
                                    public void sucess(String serverRetData) {
                                        qrCodePayDialog.dismiss();
                                        if (ObjectUtils.isEmpty(sucessDialog)) {
                                            sucessDialog = new SucessDialog(mContext, "收款金额：" + paymoney + "元", "收款成功");
                                        }
                                        if (!sucessDialog.isShowing()) {
                                            sucessDialog.show();
                                        }
                                        paymoneyEdit.setText("");
                                        memberid = 0;
                                        tvContent.setText("");
                                    }

                                    @Override
                                    public void fail(String errMsg) {
                                        ToastUtils.showShort(errMsg);
                                    }
                                });
                            }

                        }
                    });
                    if (!qrCodePayDialog.isShowing()) {
                        qrCodePayDialog.setContent(paymoney + "元");
                        qrCodePayDialog.show();
                    }
                }
                break;

            case R.id.soft_keyboard_btn_0:
                editable.insert(start, Character.toString('0'));
                break;
            case R.id.soft_keyboard_btn_1:
                editable.insert(start, Character.toString('1'));
                break;
            case R.id.soft_keyboard_btn_2:
                editable.insert(start, Character.toString('2'));
                break;
            case R.id.soft_keyboard_btn_3:
                editable.insert(start, Character.toString('3'));
                break;
            case R.id.soft_keyboard_btn_4:
                editable.insert(start, Character.toString('4'));
                break;
            case R.id.soft_keyboard_btn_5:
                editable.insert(start, Character.toString('5'));
                break;
            case R.id.soft_keyboard_btn_6:
                editable.insert(start, Character.toString('6'));
                break;
            case R.id.soft_keyboard_btn_7:
                editable.insert(start, Character.toString('7'));
                break;
            case R.id.soft_keyboard_btn_8:
                editable.insert(start, Character.toString('8'));
                break;
            case R.id.soft_keyboard_btn_9:
                editable.insert(start, Character.toString('9'));
                break;
            case R.id.tv_point:
                if (!paymoneyEdit.getText().toString().contains(".")) {
                    editable.insert(start, Character.toString('.'));
                }
                break;
            case R.id.rl_delete:
                rollBack(editable, start);
                break;
        }
    }

    private boolean verifyMoney() {
        paymoney = paymoneyEdit.getText().toString().trim();
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

    /**
     * 回退
     */
    private void rollBack(Editable editable, int start) {
        if (editable != null && editable.length() > 0) {
            if (start > 0) {
                editable.delete(start - 1, start);
            }
        }
    }

}



