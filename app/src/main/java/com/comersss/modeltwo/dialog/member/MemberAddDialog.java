package com.comersss.modeltwo.dialog.member;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.comersss.modeltwo.Constant;
import com.comersss.modeltwo.Listener.GetOpenIdLitener;
import com.comersss.modeltwo.NetUtil;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.adapter.LevelAdapter;
import com.comersss.modeltwo.adapter.SpinnerAdapter;
import com.comersss.modeltwo.adapter.SpinnerThreeAdapter;
import com.comersss.modeltwo.adapter.SpinnerTwoAdapter;
import com.comersss.modeltwo.bean.MemberListResult;
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

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * 作者：create by comersss on 2019/4/4 15:38
 * 邮箱：904359289@qq.com
 * 会员筛选对话框
 */
public class MemberAddDialog extends Dialog {

    private Context mContext;
    private MemberListResult.DataBeanX.DataBean memberBean;
    private String json;
    private String townId;
    private List<ProvinceBean> onedata = new ArrayList<>();
    private List<ProvinceBean> twodata = new ArrayList<>();
    private List<ProvinceBean> threedata = new ArrayList<>();
    private Spinner sp_one;
    private Spinner sp_two;
    private Spinner sp_three;
    private Map<String, Map<String, String>> parmOne;
    private SpinnerTwoAdapter twoAdapter;
    private SpinnerThreeAdapter threeAdapter;
    private EditText et_phone;
    private EditText et_name;
    private EditText et_adress_detail;
    private EditText et_membercode;
    private HashMap<Object, Object> localHashMap;
    private RadioButton rb_man;
    private RadioButton rb_women;
    private ArrayAdapter<String> threeSpinnerAdapter;
    private ArrayList<String> dayData;
    private ArrayList<String> yearData;
    private int currentYear;
    private int currentMonth;
    private ArrayList<String> monthData;
    private int currentDay;
    private LinearLayout ll_code;
    private LevelAdapter levelAdapter;
    private int memberLevelId = 0;
    private String url;
    private String titelStr;

    public interface OnOkClickListener {
        void onOkClick();
    }

    private OnOkClickListener onOkClickListener;

    public void setOnOkClickListener(OnOkClickListener onOkClickListener) {
        this.onOkClickListener = onOkClickListener;
    }

    public MemberAddDialog(Context context, MemberListResult.DataBeanX.DataBean memberBean, String titelStr) {
        super(context);
        this.mContext = context;
        this.memberBean = memberBean;
        this.titelStr = titelStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_add_dialog);

        try {
            json = ConvertUtils.inputStream2String(mContext.getAssets().open("map222.json"), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
//        Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
//        lp.width = (int) (display.getWidth() * 0.9); // 宽度
//        dialogWindow.setAttributes(lp);
        initView();
        setCancelable(true);
        setCanceledOnTouchOutside(true);

    }

    private void initView() {
        TextView right_close = findViewById(R.id.right_close);
        right_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView tv_scan = findViewById(R.id.tv_scan);
        tv_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetUtil.getInstance().getOpenId(new GetOpenIdLitener() {
                    @Override
                    public void sucess(final String openid) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                et_membercode.setText(openid);
                            }
                        });

                    }

                    @Override
                    public void fail(String errMsg) {

                    }
                });
            }
        });
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(titelStr);
        TextView tv_button = findViewById(R.id.tv_button);
        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        ll_code = findViewById(R.id.ll_code);
        rb_man = findViewById(R.id.rb_man);
        rb_women = findViewById(R.id.rb_women);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_adress_detail = findViewById(R.id.et_adress_detail);
        et_membercode = findViewById(R.id.et_membercode);

        sp_one = findViewById(R.id.sp_one);
        sp_two = findViewById(R.id.sp_two);
        sp_three = findViewById(R.id.sp_three);
        try {
            parmOne = new Gson().fromJson(json, Map.class);
            onedata = getData("100000");
            SpinnerAdapter oneAdapter = new SpinnerAdapter(mContext, onedata);
            sp_one.setAdapter(oneAdapter);
            twoAdapter = new SpinnerTwoAdapter(mContext, twodata);
            sp_two.setAdapter(twoAdapter);
            threeAdapter = new SpinnerThreeAdapter(mContext, threedata);
            sp_three.setAdapter(threeAdapter);

            sp_one.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        twodata = getData(onedata.get(position).getId());
                        twoAdapter.setData(twodata);
                        threedata = getData(twodata.get(0).getId());
                        threeAdapter.setData(threedata);
                        townId = threedata.get(0).getId();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Log.i("test", ex + "");
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            sp_two.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    threedata = getData(twodata.get(position).getId());
                    threeAdapter.setData(threedata);
                    townId = threedata.get(0).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String[] split = sdf.format(new Date()).split("-");
            currentYear = Integer.parseInt(split[0]);
            currentMonth = Integer.parseInt(split[1]);
            currentDay = Integer.parseInt(split[2]);
            Spinner sp_two1 = findViewById(R.id.sp_two1);
            Spinner sp_one1 = findViewById(R.id.sp_one1);
            Spinner sp_three1 = findViewById(R.id.sp_three1);

            yearData = getYearData(currentYear);
            ArrayAdapter<String> OneSpinnerAdapter = new ArrayAdapter<>(mContext, R.layout.item_spinner, yearData);
            OneSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_one1.setAdapter(OneSpinnerAdapter);
            sp_one1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    currentYear = Integer.parseInt(yearData.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            monthData = getMonthData();
            ArrayAdapter<String> twoSpinnerAdapter = new ArrayAdapter<>(mContext, R.layout.item_spinner, monthData);
            twoSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_two1.setAdapter(twoSpinnerAdapter);
            sp_two1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    currentMonth = Integer.parseInt(monthData.get(position));
                    dayData.clear();
                    dayData.addAll(getDayData(getLastDay(currentYear, currentMonth)));
                    threeSpinnerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            dayData = getDayData(getLastDay(currentYear, currentMonth));
            threeSpinnerAdapter = new ArrayAdapter<>(mContext, R.layout.item_spinner, dayData);
            threeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_three1.setAdapter(threeSpinnerAdapter);
            sp_three1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    currentDay = Integer.parseInt(dayData.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Spinner sp_level = findViewById(R.id.sp_level);
            levelAdapter = new LevelAdapter(mContext, Constant.mberLevelList);
            sp_level.setAdapter(levelAdapter);
            sp_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    memberLevelId = Constant.mberLevelList.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (!ObjectUtils.isEmpty(memberBean)) {
                ll_code.setVisibility(View.GONE);
                et_name.setText(memberBean.getName());
                et_phone.setText(memberBean.getPhoneNum());
                et_adress_detail.setText(memberBean.getAddress());

                if (memberBean.isSex()) {
                    rb_women.setChecked(true);
                    rb_man.setChecked(false);
                } else {
                    rb_women.setChecked(false);
                    rb_man.setChecked(true);
                }

                for (int i = 0; i < Constant.mberLevelList.size(); i++) {
                    if (ObjectUtils.equals(Constant.mberLevelList.get(i).getId(), memberBean.getMemberLevelId())) {
                        sp_level.setSelection(i);
                    }
                }

                String[] value = memberBean.getBirthday().split("-");
                for (int i = 0; i < yearData.size(); i++) {
                    if (ObjectUtils.equals(yearData.get(i), value[0])) {
                        sp_one1.setSelection(i);
                    }
                }
                for (int i = 0; i < monthData.size(); i++) {
                    if (ObjectUtils.equals(monthData.get(i), value[1])) {
                        sp_two1.setSelection(i);
                    }
                }
                for (int i = 0; i < dayData.size(); i++) {
                    if (ObjectUtils.equals(dayData.get(i), value[2])) {
                        sp_three1.setSelection(i);
                    }
                }

                for (int i = 0; i < onedata.size(); i++) {
                    if (onedata.get(i).getId().startsWith(memberBean.getAddressCode().substring(0, 2))) {
                        sp_one.setSelection(i);
                        twodata = getData(onedata.get(i).getId());
                        twoAdapter.setData(twodata);
                    }
                }
                for (int i = 0; i < twodata.size(); i++) {
                    if (twodata.get(i).getId().startsWith(memberBean.getAddressCode().substring(0, 4))) {
                        sp_two.setSelection(i);
                        threedata = getData(twodata.get(i).getId());
                        threeAdapter.setData(threedata);
                    }
                }

                for (int i = 0; i < threedata.size(); i++) {
                    if (threedata.get(i).getId().equals(memberBean.getAddressCode().substring(0, 6))) {
                        sp_three.setSelection(i);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Save() {
        String nameStr = et_name.getText().toString();
        String phoneStr = et_phone.getText().toString();
        String adressStr = et_adress_detail.getText().toString();
        String memberCode = et_membercode.getText().toString();

        if (!RegexUtils.isMobileSimple(phoneStr)) {
            ToastUtils.showShort("请输入正确的手机号");
            return;
        }
        if (ObjectUtils.isEmpty(nameStr)) {
            ToastUtils.showShort("请输入会员名称");
        }

        if (ObjectUtils.isEmpty(townId)) {
            townId = threedata.get(0).getId();
        }
        localHashMap = new HashMap<>();
        localHashMap.put("Name", nameStr);
        localHashMap.put("Sex", rb_man.isChecked() ? 1 : 0);
        localHashMap.put("PhoneNum", phoneStr);
        localHashMap.put("Birthday", currentYear + "-" + currentMonth + "-" + currentDay);
        localHashMap.put("AddressCode", townId);
        localHashMap.put("Address", adressStr);
        localHashMap.put("MemberLevelId", memberLevelId);

        if (ObjectUtils.isEmpty(memberBean)) {
            localHashMap.put("WechatUnqueId", memberCode);
            url = Constant.URL + Constant.AddMember;
        } else {
            localHashMap.put("Id", memberBean.getId());
            url = Constant.URL + Constant.UpdateMember;
        }

        OkGo.<String>post(url)
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
                                dismiss();
                            } else {
                                ToastUtils.showShort(resultGetOrder.getMessage());
                            }
                            if (onOkClickListener != null) {
                                onOkClickListener.onOkClick();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showShort("操作失败，请重试");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        ToastUtils.showShort("操作失败，请重试");
                    }
                });


    }


    private List<ProvinceBean> getData(String id) {

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
            String value = String.valueOf(i);
            if (value.length() == 1) {
                value = "0" + value;
            }
            list.add(value);
        }
        return list;
    }

    //日
    private ArrayList<String> getDayData(int lastDay) {
        //ignore condition
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= lastDay; i++) {
            String value = String.valueOf(i);
            if (value.length() == 1) {
                value = "0" + value;
            }
            list.add(value);
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
