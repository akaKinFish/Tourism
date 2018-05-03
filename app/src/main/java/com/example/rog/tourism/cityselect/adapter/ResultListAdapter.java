package com.example.rog.tourism.cityselect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rog.tourism.R;
import com.example.rog.tourism.cityselect.data.China_Cities;

import java.util.List;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.adapter
 * ROG
 * 2018/04/30/16:25
 * by KinFish
 * -------------------------------------------
 **/
public class ResultListAdapter extends BaseAdapter {
    private Context mContext;
    private List<China_Cities> mCities;

    public ResultListAdapter(Context mContext, List<China_Cities> mCities) {
        this.mCities = mCities;
        this.mContext = mContext;
    }
    //清空，加载list,变更适配
    public void changeData(List<China_Cities> list){
        if (mCities == null){
            mCities = list;
        }else{
            mCities.clear();
            mCities.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCities == null ? 0 : mCities.size();
    }

    @Override
    public China_Cities getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ResultViewHolder holder;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.cityselect_item_search_result_listview, parent, false);
            holder = new ResultViewHolder();
            holder.name =  view.findViewById(R.id.tv_item_result_listview_name);
            view.setTag(holder);
        }else{
            holder = (ResultViewHolder) view.getTag();
        }
        holder.name.setText(mCities.get(position).getCityname());
        return view;
    }

    public static class ResultViewHolder{
        TextView name;
    }
}
