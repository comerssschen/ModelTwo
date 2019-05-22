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

import com.comersss.modeltwo.R;
import com.comersss.modeltwo.adapter.MemberAdapter;
import com.comersss.modeltwo.bean.MemberBean;
import com.comersss.modeltwo.dialog.member.MemberAddDialog;
import com.comersss.modeltwo.dialog.member.MemberRechargeDialog;
import com.comersss.modeltwo.dialog.member.MemberScreenDialog;

import java.util.ArrayList;
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
            case R.id.tv_member_charge:
                MemberRechargeDialog memberRechargeDialog = new MemberRechargeDialog(getContext());
                memberRechargeDialog.show();
                break;
            case R.id.tv_member_screen:
                MemberScreenDialog memberScreenDialog = new MemberScreenDialog(getContext());
                memberScreenDialog.show();
                break;
            case R.id.tv_member_add:
                MemberAddDialog memberAddDialog = new MemberAddDialog(getContext());
                memberAddDialog.show();
                break;
        }
    }
}
