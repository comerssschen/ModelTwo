package com.comersss.modeltwo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
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
import com.comersss.modeltwo.AuthInfo;
import com.comersss.modeltwo.Constant;
import com.comersss.modeltwo.EditTextUtils;
import com.comersss.modeltwo.Listener.PayResultLitener;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.ArgGetAuthInfo;
import com.comersss.modeltwo.bean.ResultFacePay;
import com.comersss.modeltwo.bean.ResultGetOrder;
import com.comersss.modeltwo.dialog.home.ChoseMemberDialog;
import com.comersss.modeltwo.dialog.home.PayMoneyDialog;
import com.comersss.modeltwo.dialog.home.QrCodePayDialog;
import com.comersss.modeltwo.dialog.home.RefundDialog;
import com.comersss.modeltwo.dialog.home.SucessDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.util.Log.d;

/**
 * 收银主页
 * Created by cc on 2016/9/19.
 */
public class CashierPlatformFragment extends BaseFragment {

    private static final String TAG = "test";
    @BindView(R.id.paymoney_edit)
    EditText paymoneyEdit;
    @BindView(R.id.tv_member)
    TextView tvMember;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;
    @BindView(R.id.ll_qrcode)
    LinearLayout llQrcode;
    Unbinder unbinder;
    private String paymoney;
    private String money;

    private String appid = "wx2b975c0ca94a6154";
    private String mch_id = "1440771702";
    private String sub_mch_id = "1531015161";
    private HashMap localHashMap;
    private SPUtils spUtils;
    private SucessDialog sucessDialog;
    private View cashFragmentView;
    private PayMoneyDialog payMoneyDialog;
    private QrCodePayDialog qrCodePayDialog;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "createView");
        cashFragmentView = inflater.inflate(R.layout.fragment_cashier_platform, container, false);
        return cashFragmentView;
    }

    //初始化界面
    protected void initView(View mChildContentView, Bundle savedInstanceState) {
        Log.i(TAG, "initView");
        unbinder = ButterKnife.bind(this, mChildContentView);
        EditTextUtils.setPriceEditText(paymoneyEdit);

        spUtils = SPUtils.getInstance();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            payMoneyDialog.dismiss();
            if (ObjectUtils.isEmpty(sucessDialog)) {
                sucessDialog = new SucessDialog(cashFragmentView.getContext());
                sucessDialog.setContent("收款金额：" + paymoney + "元");
            }
            if (!sucessDialog.isShowing()) {
                sucessDialog.show();
            }
            paymoneyEdit.setText("");
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
                ToastUtils.showShort("选择会员");
                ChoseMemberDialog choseMemberDialog = new ChoseMemberDialog(getContext());
                choseMemberDialog.show();

                break;
            case R.id.tv_refund:
                RefundDialog refundDialog = new RefundDialog(getContext());
                refundDialog.show();
                ToastUtils.showShort("退款");
                break;
            case R.id.ll_scan:
                if (verifyMoney()) {
//                    if (ObjectUtils.isEmpty(payMoneyDialog)) {
                    payMoneyDialog = new PayMoneyDialog(getContext(), "刷脸", paymoney + "元", "请刷脸收款");
                    payMoneyDialog.setOnOkClickListener(new PayMoneyDialog.OnOkClickListener() {
                        @Override
                        public void onOkClick() {
                            NetUtil.getInstance().getOrder(money, new PayResultLitener() {
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
                            NetUtil.getInstance().getQrCodePay(result, money, new PayResultLitener() {
                                @Override
                                public void sucess(String serverRetData) {
                                    ToastUtils.showShort(serverRetData);
                                }

                                @Override
                                public void fail(String errMsg) {
                                    ToastUtils.showShort(errMsg);
                                }
                            });
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
        BigDecimal minMoney = new BigDecimal(paymoney);
        money = minMoney.multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        if (ObjectUtils.isEmpty(paymoney) || TextUtils.equals(paymoney, "0") || TextUtils.equals(paymoney, "0.") || TextUtils.equals(paymoney, "0.0") || TextUtils.equals(paymoney, "0.00")) {
            ToastUtils.showShort("请输入有效金额");
            return false;
        } else if (Double.parseDouble(paymoney) > 100000) {
            ToastUtils.showShort("最大金额99999.99");
            return false;
        } else {
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



