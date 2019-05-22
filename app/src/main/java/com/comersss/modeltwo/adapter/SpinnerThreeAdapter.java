package com.comersss.modeltwo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.comersss.modeltwo.R;
import com.comersss.modeltwo.bean.ProvinceBean;

import java.util.List;

/**
 * 作者：create by comersss on 2019/5/22 16:12
 * 邮箱：904359289@qq.com
 */
public class SpinnerThreeAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater li;
    private List<ProvinceBean> dataList;
    public void setData(List<ProvinceBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }
    public SpinnerThreeAdapter(Context ctx, List<ProvinceBean> dataList) {
        this.ctx = ctx;
        this.li = LayoutInflater.from(ctx);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return ObjectUtils.isEmpty(dataList) ? 0 : dataList.size();
    }

    @Override
    public ProvinceBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_spinner, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();// get convertView's holder
        holder.car_cost.setText(getItem(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView car_cost;

        public ViewHolder(View convertView) {
            car_cost = convertView.findViewById(R.id.tv_spinner);
            convertView.setTag(this);//set a viewholder
        }
    }


}
