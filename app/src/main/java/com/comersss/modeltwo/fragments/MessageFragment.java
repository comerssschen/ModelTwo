package com.comersss.modeltwo.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comersss.modeltwo.R;

/*
* 接收消息的fragment
* Created by cc on 2016/7/27.
* */
public class MessageFragment extends BaseFragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //初始化界面
    protected void initView(final View mChildContentView, Bundle savedInstanceState) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
