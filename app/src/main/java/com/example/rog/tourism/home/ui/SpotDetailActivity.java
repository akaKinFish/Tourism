package com.example.rog.tourism.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rog.tourism.R;
import com.example.rog.tourism.TourismApp;
import com.example.rog.tourism.home.adapter.SpotDetailAdapter;
import com.example.rog.tourism.home.data.SpotDetails.ResultBean;
import com.example.rog.tourism.home.net.SpotDetailAPIService;
import com.example.rog.tourism.utils.ResUtils;
import com.example.rog.tourism.utils.RxSchedulers;
import com.example.rog.tourism.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.r0adkll.slidr.Slidr;


import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.home.ui
 * ROG
 * 2018/05/02/13:02
 * by KinFish
 * -------------------------------------------
 **/
public class SpotDetailActivity extends AppCompatActivity {

    private static final String TAG = "SpotDetailActivity";
    private FloatingActionButton fab_top;
    private RecyclerView spot_detail;
    private CompositeDisposable mSubscriptions;
    private SpotDetailAdapter mAdapter;
    public SwipeRefreshLayout spot_detail_refresh;

    private String key = "95944e42de8a4472a58bb7a91a02837e";
    private String sid = "29828";
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private ArrayList<ResultBean> mResults;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_detail);
//        左滑退出
        Slidr.attach(this);
        initData();
        initView();
        spot_detail_refresh.setRefreshing(true);
        fetchSpotDetails(true);
    }

    //    获取信息spotdetails
    private void fetchSpotDetails(boolean isRefresh) {
        Disposable subscribe = SpotDetailAPIService.getInstance().spotDetailsAPIs.fetchSpotDetails(sid,key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((Disposable subscription) ->{spot_detail_refresh.setRefreshing(false);
                    Logger.v("状态更新成功");})
                .doFinally(() ->spot_detail_refresh.setRefreshing(false))
                .subscribe(data ->{
                    if(data==null)Logger.v("data数据为空");
                    if(data !=null &&data.getResult()!=null&&data.getResult().size()>0){
                        Logger.v("dataResult",data.getResult().size()+"");
                        ArrayList<ResultBean> resultBeans = data.getResult();
                        mAdapter.addAll(resultBeans);
                        ToastUtils.showShort(TourismApp.getContext(),ResUtils.getString(R.string.load_sucess));
                    }
                }, RxSchedulers::processRequestException);



        mSubscriptions.add(subscribe);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    private void initView() {
        spot_detail = findViewById(R.id.spot_detail);
        fab_top = findViewById(R.id.detail_fab_top);
        spot_detail_refresh = findViewById(R.id.spot_detail_refresh);
        spot_detail_refresh.setOnRefreshListener(()->fetchSpotDetails(true));
        final GridLayoutManager layoutManager = new GridLayoutManager(TourismApp.getContext(), 1);
        spot_detail.setLayoutManager(layoutManager);
        spot_detail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findFirstVisibleItemPosition() != 0) {
                    fabInAnim();
                } else {
                    fabOutAnim();

                }
            }
        });
        fab_top.setOnClickListener(v -> {
            LinearLayoutManager manager = (LinearLayoutManager) spot_detail.getLayoutManager();
            //如果超过50项直接跳到开头，不然要滚好久
            if(manager.findFirstVisibleItemPosition() < 50) {
                spot_detail.smoothScrollToPosition(0);
            } else {
                spot_detail.scrollToPosition(0);
                fabOutAnim();
            }
        });

        mAdapter = new SpotDetailAdapter(SpotDetailActivity.this,mResults);

        spot_detail.setAdapter(mAdapter);


    }


    /* 悬浮按钮显示动画 */
    private void fabInAnim() {
        if (fab_top.getVisibility() == View.GONE) {
            fab_top.setVisibility(View.VISIBLE);
            ViewCompat.animate(fab_top).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .setInterpolator(INTERPOLATOR).withLayer().setListener(null).start();
        }
    }

    /* 悬浮图标隐藏动画 */
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


    private void initData() {
        sid = getIntent().getStringExtra("sid");
        mResults = new ArrayList<>();
        mSubscriptions = new CompositeDisposable();
    }

}
