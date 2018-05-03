package com.example.rog.tourism.home.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rog.tourism.R;
import com.example.rog.tourism.home.data.ViewSpot.ResultBean;
import com.example.rog.tourism.home.ui.SpotDetailActivity;

import java.util.ArrayList;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.home.adapter
 * ROG
 * 2018/05/01/21:34
 * by KinFish
 * -------------------------------------------
 **/
public class SpotAdapter extends RecyclerView.Adapter<SpotAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<ResultBean>  mResults = new ArrayList<>();
    private FragmentManager mFragmentManager;

    public SpotAdapter(Context mContext,ArrayList<ResultBean> mResults){
        this.mContext = mContext;
        this.mResults = mResults;
    }
    public void addAll(ArrayList<ResultBean> data){
        mResults.clear();
        mResults.addAll(data);
        notifyDataSetChanged();
    }

    public void loadMore(ArrayList<ResultBean> data){
        mResults.addAll(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spot_tourism, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(mResults.get(position));

    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView spot_img;
        TextView spot_title;
        TextView spot_address;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            spot_img = itemView.findViewById(R.id.spot_img);
            spot_title = itemView.findViewById(R.id.spot_title);
            spot_address = itemView.findViewById(R.id.spot_address);
            cardView = itemView.findViewById(R.id.card_view);
        }

        void bind(ResultBean data){
            Glide.with(mContext)
                    .load(data.getImgurl())
                    .apply(new RequestOptions()
                    .centerCrop())
                    .into(spot_img);
            spot_title.setText(data.getTitle());
            spot_address.setText(data.getAddress());
            cardView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, SpotDetailActivity.class);
                intent.putExtra("sid",data.getSid());
                mContext.startActivity(intent);
            });
        }
    }
}
