package com.comersss.modeltwo.dialog.member;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Constant;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.adapter.SpinnerAdapter;
import com.comersss.modeltwo.adapter.SpinnerThreeAdapter;
import com.comersss.modeltwo.adapter.SpinnerTwoAdapter;
import com.comersss.modeltwo.bean.ProvinceBean;
import com.comersss.modeltwo.bean.ResultBase;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;
    private EditText et_phone;
    private EditText et_name;
    private EditText et_adress_detail;
    private EditText et_membercode;
    private HashMap<Object, Object> localHashMap;
    private RadioButton rb_man;
    private RadioButton rb_women;
    private ArrayList<String> yearData;

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

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 0.9); // 宽度
        dialogWindow.setAttributes(lp);
        initView();
        initDate();
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void initDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String[] split = sdf.format(new Date()).split("-");
        int currentYear = Integer.parseInt(split[0]);
        int currentMonth = Integer.parseInt(split[1]);
        int currentDay = Integer.parseInt(split[2]);
        Spinner sp_two1 = findViewById(R.id.sp_two1);
        Spinner sp_one1 = findViewById(R.id.sp_one1);
        Spinner sp_three1 = findViewById(R.id.sp_three1);

        //简单的string数组适配器：样式res，数组
        yearData = getYearData(currentYear);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, yearData);
        //下拉的样式res
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        sp_one1.setAdapter(spinnerAdapter);

    }

    private void initView() {
        TextView tv_button = findViewById(R.id.tv_button);
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        rb_man = findViewById(R.id.rb_man);
        rb_women = findViewById(R.id.rb_women);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_adress_detail = findViewById(R.id.et_adress_detail);
        et_membercode = findViewById(R.id.et_membercode);

        tv_one = findViewById(R.id.tv_one);
        tv_two = findViewById(R.id.tv_two);
        tv_three = findViewById(R.id.tv_three);
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
//                ToastUtils.showShort(data.get(position).getName());
                tv_one.setText(data.get(position).getName());
                proviceId = data.get(position).getId();
                cityId = "";
                townId = "";
                tv_two.setText("");
                tv_three.setText("");
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
//                ToastUtils.showShort(twodata.get(position).getName());
                tv_two.setText(twodata.get(position).getName());
                cityId = twodata.get(position).getId();
                townId = "";
                tv_three.setText("");
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
                tv_three.setText(threedata.get(position).getName());
                townId = threedata.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Save() {
        localHashMap = new HashMap<>();
        localHashMap.put("Name", et_name.getText().toString());
        localHashMap.put("Sex", rb_man.isChecked() ? 1 : 0);
        localHashMap.put("PhoneNum", et_phone.getText().toString());
        localHashMap.put("Birthday", "2002-02-02");
        localHashMap.put("AddressCode", "120119");
        localHashMap.put("Address", et_adress_detail.getText().toString());
//        localHashMap.put("EntryMode", codeStr);
//        localHashMap.put("AliUnqueId", codeStr);
//        localHashMap.put("WechatUnqueId", codeStr);
        localHashMap.put("MemberLevelId", et_membercode.getText().toString());
//        localHashMap.put("IsLocked", codeStr);

        OkGo.<String>post(Constant.URL + Constant.AddMember)
                .upJson(new Gson().toJson(localHashMap))
                .headers("Authorization", SPUtils.getInstance().getString("token", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            String body = response.body();
                            Log.i("test", "getAuthInfo :" + body);
                            ResultBase resultGetOrder = new Gson().fromJson(body, ResultBase.class);

                            ToastUtils.showShort(resultGetOrder.getMessage());

                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort("添加失败，请重试");
                        }
                        dismiss();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort("添加失败，请重试");
                        dismiss();
                    }
                });


    }


    private List<ProvinceBean> getData(String id) {
        if (ObjectUtils.isEmpty(json)) {
            try {
                json = ConvertUtils.inputStream2String(mContext.getAssets().open("map222.json"), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (ObjectUtils.isEmpty(id) || ObjectUtils.equals("000000", id)) {
            return null;
        }
        List<ProvinceBean> list = new ArrayList<>();
        Map<String, String> map = parmOne.get(id);
        for (Map.Entry<String, String> stringStringEntry : map.entrySet()) {
            list.add(new ProvinceBean(stringStringEntry.getKey(), stringStringEntry.getValue()));
        }
        return list;
    }


    //设置年
    private ArrayList<String> getYearData(int currentYear) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = currentYear; i >= 1900; i--) {
            list.add(String.valueOf(i));
        }
        return list;
    }


    //月
    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    //日
    private ArrayList<String> getDayData(int lastDay) {
        //ignore condition
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= lastDay; i++) {
            list.add(String.valueOf(i));
        }
        return list;
    }

    /**
     * 判断是否闰年
     *
     * @param year
     * @return
     */
    private boolean isLeapYear(int year) {
        return (year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * 获取特定年月对应的天数
     *
     * @param year
     * @param month
     * @return
     */
    private int getLastDay(int year, int month) {
        if (month == 2) {
            // 2月闰年的话返回29，防止28
            return isLeapYear(year) ? 29 : 28;
        }

        return month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12 ? 31 : 30;
    }
}
