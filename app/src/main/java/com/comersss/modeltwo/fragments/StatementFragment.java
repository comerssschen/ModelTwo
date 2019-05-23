package com.comersss.modeltwo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Constant;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.adapter.MemberAdapter;
import com.comersss.modeltwo.bean.MemberBean;
import com.comersss.modeltwo.bean.ResultBase;
import com.comersss.modeltwo.dialog.member.MemberAddDialog;
import com.comersss.modeltwo.dialog.member.MemberRechargeDialog;
import com.comersss.modeltwo.dialog.member.MemberScreenDialog;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*
 * 账单fragment
 * Created by cc on 2016/7/27.
 * */
public class StatementFragment extends BaseFragment {


    @BindView(R.id.tv_member_num)
    TextView tvMemberNum;
    @BindView(R.id.tv_yestoday_member)
    TextView tvYestodayMember;
    @BindView(R.id.tv_lastweek_member)
    TextView tvLastweekMember;
    @BindView(R.id.tv_total_intergral)
    TextView tvTotalIntergral;
    @BindView(R.id.tv_canused)
    TextView tvCanused;
    @BindView(R.id.tv_member_charge)
    TextView tvMemberCharge;
    @BindView(R.id.tv_member_screen)
    TextView tvMemberScreen;
    @BindView(R.id.tv_member_add)
    TextView tvMemberAdd;
    @BindView(R.id.member_recyclerview)
    RecyclerView memberRecyclerview;

    Unbinder unbinder;
    private HashMap<Object, Object> localHashMap;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_statement, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    //初始化
    @Override
    protected void initView(View mChildContentView, Bundle savedInstanceState) {

        MemberAdapter memberAdapter = new MemberAdapter(R.layout.member_item);
        memberRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        memberRecyclerview.setAdapter(memberAdapter);
        List<MemberBean> beanList = new ArrayList<>();
        beanList.add(new MemberBean("111"));
        beanList.add(new MemberBean("112"));
        beanList.add(new MemberBean("113"));
        beanList.add(new MemberBean("114"));
        memberAdapter.setNewData(beanList);

        queryMemberStatistics();
        getMemberList();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.i("test", "切换到了stategragment");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_member_charge, R.id.tv_member_screen, R.id.tv_member_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //会员充值
            case R.id.tv_member_charge:
                MemberRechargeDialog memberRechargeDialog = new MemberRechargeDialog(getContext());
                memberRechargeDialog.show();
                break;
            //会员筛选
            case R.id.tv_member_screen:
                MemberScreenDialog memberScreenDialog = new MemberScreenDialog(getContext());
                memberScreenDialog.show();
                break;
            //新增会员
            case R.id.tv_member_add:
                MemberAddDialog memberAddDialog = new MemberAddDialog(getContext());
                memberAddDialog.show();
                break;
        }
    }


    //获取会员统计
    private void queryMemberStatistics() {
        OkGo.<String>post(Constant.URL + Constant.QueryMemberStatistics)
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            if (resultGetOrder.isSuccess()) {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            } else {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            }
                            Log.i("test", "queryMemberStatistics :" + body);

                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort("查询失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort("查询失败，请重试");
                    }
                });
    }


    private void getMemberList() {
        localHashMap = new HashMap<>();
        localHashMap.put("pageIndex", 1);
        localHashMap.put("pageSize", 10);
        OkGo.<String>post(Constant.URL + Constant.QueryMembers)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);
                            if (resultGetOrder.isSuccess()) {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            } else {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            }
                            Log.i("test", "getMemberList :" + body);

                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort("查询失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort("查询失败，请重试");
                    }
                });
    }


}
