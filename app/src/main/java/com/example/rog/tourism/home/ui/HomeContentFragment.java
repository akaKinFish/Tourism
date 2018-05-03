package com.example.rog.tourism.home.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.example.rog.tourism.R;
import com.example.rog.tourism.TourismApp;
import com.example.rog.tourism.home.adapter.SpotAdapter;
import com.example.rog.tourism.home.data.ViewSpot.ResultBean;
import com.example.rog.tourism.home.net.SpotAPIService;
import com.example.rog.tourism.utils.ResUtils;
import com.example.rog.tourism.utils.RxSchedulers;
import com.example.rog.tourism.utils.ToastUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.ui.fragment
 * ROG
 * 2018/04/25/20:32
 * by KinFish
 * -------------------------------------------
 **/
public class HomeContentFragment extends Fragment{

    private static final String TAG = "HomeContentFragment";
    private SwipeRefreshLayout spot_refresh;
    private FloatingActionButton fab_top;
    private RecyclerView rec_spot;
    private CompositeDisposable mSubscriptions;
    private SpotAdapter mAdapter;
    private  static final int PRELOAD_SIZE = 10;
    private int mPage = 1;
    private ArrayList<ResultBean> mData;
    private String key = "424f608ec9a843af8a88fdd45d01ca37";

    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private Context mContext;

    public static HomeContentFragment newInstance() {


        HomeContentFragment fragment = new HomeContentFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_content,container,false);
        spot_refresh = view.findViewById(R.id.spot_refresh);
        rec_spot = view.findViewById(R.id.rec_spot);
        fab_top = view.findViewById(R.id.fab_top);
        spot_refresh.setOnRefreshListener(()->{
            mPage = 1;
            fetchSpot(true);
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        rec_spot.setLayoutManager(layoutManager);
        rec_spot.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//加载更多
                    if (layoutManager.getItemCount() - recyclerView.getChildCount() <= layoutManager.findFirstVisibleItemPosition()) {
                        ++mPage;
                        fetchSpot(false);
                    }
                }
                if (layoutManager.findFirstVisibleItemPosition() != 0) {
                    fabInAnim();
                } else {
                    fabOutAnim();
                }
            }
        });
        fab_top.setOnClickListener(view1 -> {
            LinearLayoutManager manager = (LinearLayoutManager)rec_spot.getLayoutManager();
            if(manager.findFirstVisibleItemPosition()<50){
                rec_spot.smoothScrollToPosition(0);
            }else {
                rec_spot.scrollToPosition(0);
                fabOutAnim();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mSubscriptions = new CompositeDisposable();
        mData = new ArrayList<>();
        mAdapter = new SpotAdapter(mContext,mData);
        rec_spot.setAdapter(mAdapter);
        spot_refresh.setRefreshing(true);
        fetchSpot(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    /*悬浮按钮隐藏动画*/
    private void fabOutAnim() {
        if (fab_top.getVisibility() == View.VISIBLE) {
            ViewCompat.animate(fab_top).scaleX(0.0F).scaleY(0.0F).alpha(0.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(View view) {

                }

                @Override
                public void onAnimationEnd(View view) {
                    fab_top.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(View view) {

                }
            }).start();
        }

    }

   /* 悬浮按钮显示动画*/
    private void fabInAnim() {
        if (fab_top.getVisibility() == View.GONE) {
            fab_top.setVisibility(View.VISIBLE);
            ViewCompat.animate(fab_top).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null).start();
        }
    }

    private void fetchSpot(boolean isRefresh) {
        Disposable subscribe = SpotAPIService.getInstance().spotAPIs.fetchViewSpot(mPage,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(subscription->spot_refresh.setRefreshing(false))
                .doFinally(()->spot_refresh.setRefreshing(false))
                .subscribe(data -> {
                    if(data !=null && data.getResult()!=null&&data.getResult().size()>0){
                        ArrayList<ResultBean> results = data.getResult();
                        if(isRefresh){
                            mAdapter.addAll(results);
                            ToastUtils.showShort(TourismApp.getContext(),"刷新成功！！！！！");
                        }else {
                            mAdapter.loadMore(results);
                            String msg = String.format(ResUtils.getString(R.string.load_more_num),results.size(),"景点信息");
                            ToastUtils.showShort(TourismApp.getContext(),msg);
                        }


                    }
                }, RxSchedulers::processRequestException);
        mSubscriptions.add(subscribe);
    }


}
