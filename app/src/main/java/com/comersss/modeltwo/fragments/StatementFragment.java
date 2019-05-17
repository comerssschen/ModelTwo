package com.comersss.modeltwo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comersss.modeltwo.R;

/*
* 账单fragment
* Created by cc on 2016/7/27.
* */
public class StatementFragment extends BaseFragment {



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_statement, container, false);
    }

    //初始化
    @Override
    protected void initView(View mChildContentView, Bundle savedInstanceState) {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Log.i("test", "切换到了stategragment");
        }
    }


}
