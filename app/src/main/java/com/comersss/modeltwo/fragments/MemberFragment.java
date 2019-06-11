package com.comersss.modeltwo.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.comersss.modeltwo.Listener.MemberResultLitener;
import com.comersss.modeltwo.Listener.RefreshLitener;
import com.comersss.modeltwo.Listener.TopCountLitener;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.adapter.MemberAdapter;
import com.comersss.modeltwo.bean.ArgTopCountResult;
import com.comersss.modeltwo.bean.MemberListResult;
import com.comersss.modeltwo.dialog.member.MemberAddDialog;
import com.comersss.modeltwo.dialog.member.MemberItemInfoDialog;
import com.comersss.modeltwo.dialog.member.MemberItemRechargeDialog;
import com.comersss.modeltwo.dialog.member.MemberRechargeDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 会员
 */
public class MemberFragment extends BaseFragment {

    @BindView(R.id.tv_member_num)
    TextView tvMemberNum;
    @BindView(R.id.tv_yestoday_member)
    TextView tvYestodayMember;
    @BindView(R.id.tv_lastweek_member)
    TextView tvLastweekMember;
    @BindView(R.id.tv_member_charge)
    TextView tvMemberCharge;
    @BindView(R.id.tv_member_add)
    TextView tvMemberAdd;
    @BindView(R.id.member_recyclerview)
    RecyclerView memberRecyclerview;
    Unbinder unbinder;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private MemberAdapter memberAdapter;
    private static final int PAGE_SIZE = 10;
    private int mNextRequestPage = 1;
    private MemberItemRechargeDialog memberItemRechargeDialog;
    private MemberItemInfoDialog memberItemInfoDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_member, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    //初始化
    @Override
    protected void initView(View mChildContentView, Bundle savedInstanceState) {
        memberAdapter = new MemberAdapter(R.layout.member_item);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecle();
            }
        });
        mSwipeRefreshLayout.setRefreshing(true);

        memberAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MemberListResult.DataBeanX.DataBean memberBean = memberAdapter.getData().get(position);
                if (view.getId() == R.id.tv_member_charge) {
                    memberItemRechargeDialog = new MemberItemRechargeDialog(getContext(), memberBean);
                    memberItemRechargeDialog.show();
                } else if (view.getId() == R.id.tv_member_screen) {
                    memberItemInfoDialog = new MemberItemInfoDialog(getContext(), position, memberBean, new RefreshLitener() {
                        @Override
                        public void refresh(int position) {
                            refreshRecle();
                        }
                    });
                    memberItemInfoDialog.show();
                }
            }
        });
        memberAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                memberRecyclerview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                    }

                }, 300);
            }
        }, memberRecyclerview);
        memberAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        memberRecyclerview.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        memberRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        memberRecyclerview.setAdapter(memberAdapter);
        refreahTop();
        refreshRecle();
    }

    private void refreahTop() {
        NetUtil.getInstance().queryMemberStatistics(new TopCountLitener() {
            @Override
            public void sucess(ArgTopCountResult.DataBean memberBeanList) {
                tvMemberNum.setText(memberBeanList.getAll() + "个");
                tvYestodayMember.setText(memberBeanList.getYesterdayDay() + "个");
                tvLastweekMember.setText(memberBeanList.getLastWeek() + "个");
            }

            @Override
            public void fail(String errMsg) {
                ToastUtils.showShort(errMsg);
            }
        });
    }

    private void refreshRecle() {
        mNextRequestPage = 1;
        memberAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        NetUtil.getInstance().getMemberList(mNextRequestPage, new MemberResultLitener() {
            @Override
            public void sucess(List<MemberListResult.DataBeanX.DataBean> memberBeanList) {
                setData(true, memberBeanList);
                memberAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void fail(String errMsg) {
                memberAdapter.setEnableLoadMore(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void loadMore() {
        NetUtil.getInstance().getMemberList(mNextRequestPage, new MemberResultLitener() {
            @Override
            public void sucess(List<MemberListResult.DataBeanX.DataBean> memberBeanList) {
                boolean isRefresh = mNextRequestPage == 1;
                setData(isRefresh, memberBeanList);
            }

            @Override
            public void fail(String errMsg) {
                memberAdapter.loadMoreFail();
            }
        });

    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            memberAdapter.setNewData(data);
        } else {
            if (size > 0) {
                memberAdapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            memberAdapter.loadMoreEnd(isRefresh);
        } else {
            memberAdapter.loadMoreComplete();
        }
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

    @OnClick({R.id.tv_member_charge, R.id.tv_member_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //会员充值
            case R.id.tv_member_charge:
                MemberRechargeDialog memberRechargeDialog = new MemberRechargeDialog(getContext());
                memberRechargeDialog.show();
                break;
            //新增会员
            case R.id.tv_member_add:
                MemberAddDialog memberAddDialog = new MemberAddDialog(getContext(), null, "新增会员");
                memberAddDialog.setOnOkClickListener(new MemberAddDialog.OnOkClickListener() {
                    @Override
                    public void onOkClick() {
                        //新增成功时刷新页面
                        refreshRecle();
                        refreahTop();
                    }
                });
                memberAddDialog.show();
                break;
        }
    }


}
