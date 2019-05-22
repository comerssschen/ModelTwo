package com.comersss.modeltwo.dialog.member;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.adapter.SpinnerAdapter;
import com.comersss.modeltwo.adapter.SpinnerThreeAdapter;
import com.comersss.modeltwo.adapter.SpinnerTwoAdapter;
import com.comersss.modeltwo.bean.ProvinceBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 会员筛选对话框
 */
public class MemberAddDialog extends Dialog {


    private Context mContext;
    private String json;
    private String proviceId;
    private String cityId;
    private String townId;
    private List<ProvinceBean> data;
    private List<ProvinceBean> twodata;
    private List<ProvinceBean> threedata;
    private Spinner sp_one;
    private Spinner sp_two;
    private Spinner sp_three;
    private Map<String, Map<String, String>> parmOne;
    private SpinnerTwoAdapter twoAdapter;
    private SpinnerThreeAdapter threeAdapter;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public MemberAddDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_add_dialog);
        sp_one = findViewById(R.id.sp_one);
        sp_two = findViewById(R.id.sp_two);
        sp_three = findViewById(R.id.sp_three);
        try {
            json = ConvertUtils.inputStream2String(mContext.getAssets().open("map222.json"), "utf-8");
            parmOne = new Gson().fromJson(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        data = getData("100000");
        SpinnerAdapter oneAdapter = new SpinnerAdapter(mContext, data);
        sp_one.setAdapter(oneAdapter);
        sp_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(data.get(position).getName());
                proviceId = data.get(position).getId();
                cityId = "";
                townId = "";
                sp_three.setClickable(false);
                sp_two.setClickable(true);
                twodata = getData(proviceId);
                twoAdapter.setData(twodata);
                sp_two.setClickable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        twoAdapter = new SpinnerTwoAdapter(mContext, twodata);
        sp_two.setAdapter(twoAdapter);
        sp_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(twodata.get(position).getName());
                cityId = twodata.get(position).getId();
                townId = "";

                threedata = getData(cityId);
                sp_three.setClickable(true);
                threeAdapter.setData(threedata);
                sp_three.setClickable(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        threeAdapter = new SpinnerThreeAdapter(mContext, threedata);
        sp_three.setAdapter(threeAdapter);
        sp_three.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.showShort(threedata.get(position).getName());
                townId = threedata.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        Set<Map.Entry<String, String>> entrys = objectMap.entrySet();

//        for(Map.Entry<String, String> entry:entrys){
//            result.put(entry.getKey(),entry.getValue());
//        }


//        TextView tvConfirm = findViewById(R.id.tv_parm1);
//        TextView tvCancle = findViewById(R.id.tv_parm2);
//
//        tvConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onOkClickListener != null)
//                    onOkClickListener.onOkClick();
//            }
//        });
//        tvCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void Verify() {
        if (ObjectUtils.isEmpty(proviceId) || ObjectUtils.isEmpty(cityId)) {
            sp_three.setClickable(false);
        } else {
            sp_three.setClickable(true);
        }
        if (ObjectUtils.isEmpty(proviceId)) {
            sp_two.setClickable(false);
        } else {
            sp_two.setClickable(true);
        }
    }

    private List<ProvinceBean> getData(String id) {
        if (ObjectUtils.isEmpty(json)) {
            try {
                json = ConvertUtils.inputStream2String(mContext.getAssets().open("map222.json"), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ObjectUtils.isEmpty(id)) {
            return null;
        }
        List<ProvinceBean> list = new ArrayList<>();
        Map<String, String> map = parmOne.get(id);
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(new ProvinceBean(stringStringEntry.getKey(), stringStringEntry.getValue()));
        }
        return list;
//        for (Map.Entry<String, Map<String, String>> entry : parmOne.entrySet()) {
//            String key = entry.getKey();
//            Map<String, String> value = entry.getValue();
//            Log.i("test", value.toString());
//        }

    }

}
