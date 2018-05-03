package com.example.rog.tourism.cityselect.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rog.tourism.R;
import com.example.rog.tourism.cityselect.data.China_Cities;
import com.example.rog.tourism.cityselect.utils.LocateState;
import com.example.rog.tourism.cityselect.utils.PinyinUtils;
import com.example.rog.tourism.cityselect.view.WarpHeightGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.adapter
 * ROG
 * 2018/04/30/14:40
 * by KinFish
 * -------------------------------------------
 **/
public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<China_Cities> mCities;
    private HashMap<String,Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;

    public CityListAdapter(Context mContext,List<China_Cities> mCities){
        this.mContext = mContext;
        this.mCities = mCities;
        this.inflater = LayoutInflater.from(mContext);
        if(mCities == null){
            mCities = new ArrayList<>();
        }
        mCities.add(0, new China_Cities("0", "0","定位"));
        mCities.add(1,new China_Cities("1","1","热门"));
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index<size ; index++){

//            currentLetter当前城市的拼音首字母，preLetter前一个城市的拼音的首字母
//            判断是否>=1即判断是否存在拼音，即是否为城市，如果不存在将其设为""
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getPinyin());
            String preLetter = index >=1 ? PinyinUtils.getFirstLetter(mCities.get(index-1).getPinyin()):"";

//            通过与前一个城市的首字母进行比对是否相等，将相同首字母的位置记录下来
            if(!TextUtils.equals(currentLetter,preLetter)){
                letterIndexes.put(currentLetter,index);
                sections[index] = currentLetter;
            }
        }
    }


    /*更新定位信息*/
    public void updateLocateState(int state,String city){
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /*获取字母索引的位置，得到当前字母所在list的位置*/
    public int getLetterPosition(String letter) {
//        1、Integer是int的包装类，int则是java的一种基本数据类型
//        2、Integer变量必须实例化后才能使用，而int变量不需要
//        3、Integer实际是对象的引用，当new一个Integer时，实际上是生成一个指针指向此对象；而int则是直接存储数据值
//        4、Integer的默认值是null，int的默认值是0
        Integer integer = letterIndexes.get(letter);
//        int integer = letterIndexes.get(letter);
        return integer == null ? -1:integer;
    }

    /*三种类型*/
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }
    /*类型返回*/
    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0: mCities.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType){
            case 0:     //定位
                view = inflater.inflate(R.layout.cityselect_view_locate_city, parent, false);
                ViewGroup container =  view.findViewById(R.id.layout_locate);
                TextView state =  view.findViewById(R.id.tv_located_city);
                switch (locateState){
                    case LocateState.LOCATING:
                        state.setText(mContext.getString(R.string.cityselect_locating));
                        break;
                    case LocateState.FAILED:
                        state.setText(R.string.cityselect_located_failed);
                        break;
                    case LocateState.SUCCESS:
                        state.setText(locatedCity);
                        break;
                }
                //再次点击的时候重新定位，定位成功则返回城市
                container.setOnClickListener(v -> {
                    if (locateState == LocateState.FAILED){
                        //重新定位
                        if (onCityClickListener != null){
                            onCityClickListener.onLocateClick();
                        }
                    }else if (locateState == LocateState.SUCCESS){
                        //返回定位城市
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(locatedCity);
                        }
                    }
                });
                break;
            case 1:     //热门
                view = inflater.inflate(R.layout.cityselect_view_hot_city, parent, false);
                WarpHeightGridView gridView = view.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener((AdapterView<?> parent1, View view1, int position1, long id) -> {
                    if (onCityClickListener != null){
                        onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position1));
                    }
                });
                break;
            case 2:     //所有
                if (view == null){
                    view = inflater.inflate(R.layout.cityselect_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                }else{
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 1){
                    final String cityname = mCities.get(position).getCityname();
                    holder.name.setText(cityname);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getPinyin());
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getPinyin()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)){
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    }else{
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(cityname);
                            }
                        }
                    });
                }
                break;
        }
        return view;
    }

    public interface OnCityClickListener {
        void onCityClick(String cityname);
        void onLocateClick();
    }

    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    private class CityViewHolder {
        TextView letter;
        TextView name;
    }
}
