package com.example.rog.tourism.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rog.tourism.R;
import com.example.rog.tourism.home.data.SpotDetails.ResultBean;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.home.adapter
 * ROG
 * 2018/05/02/15:53
 * by KinFish
 * -------------------------------------------
 **/
public class SpotDetailAdapter extends RecyclerView.Adapter<SpotDetailAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ResultBean> mResults = new ArrayList<>();

    public SpotDetailAdapter(Context mContext,ArrayList<ResultBean> mResults){
        this.mContext = mContext;
        this.mResults = mResults;
    }

    public void addAll(ArrayList<ResultBean> data){
        mResults.clear();
        mResults.addAll(data);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spot_detail_tourism,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mResults.get(position));

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class ViewHolder extends  RecyclerView.ViewHolder{

        TextView spot_detail_title;
        TextView spot_detail_referral;
        ImageView spot_detail_img;

         ViewHolder(View itemView) {
            super(itemView);
            spot_detail_title = itemView.findViewById(R.id.spot_detail_title);
            spot_detail_referral = itemView.findViewById(R.id.spot_detail_referral);
            spot_detail_img = itemView.findViewById(R.id.spot_detail_img);
        }

         void bind(ResultBean resultBean) {
             Glide.with(mContext)
                     .load(resultBean.getImg())
                     .apply(new RequestOptions()
                     .centerCrop())
                     .into(spot_detail_img);
             spot_detail_title.setText(resultBean.getTitle());
             spot_detail_referral.setText(resultBean.getReferral());
        }
    }
}
