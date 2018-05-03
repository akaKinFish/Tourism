package com.example.rog.tourism.cityselect.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.view
 * ROG
 * 自定义gridview
 * 2018/04/29/17:24
 * by KinFish
 * -------------------------------------------
 **/
public class WarpHeightGridView extends GridView {
    public WarpHeightGridView(Context context) {
        super(context);
    }

    public WarpHeightGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WarpHeightGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WarpHeightGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /*重写gridview方法，否则显示不全*/
    /*显示一行*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*写listVIew、gridView的onMeasure方法就行了，加上红字部分就可以实现listView、gridView高度包裹内容。
        为什么是
        MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        首先得看看MeasureSpec的几个方法和作用


        那么其中的两个值就很好理解了
        因为32位的数据中的前两位是代表的模式，那么Integer.MAX_VALUE >> 2就代表能获取到的最大值（不含模式下的值）
        MeasureSpec.AT_MOST这个模式下面高度会在listView、gridView的item集高度和Integer.MAX_VALUE >> 2
        之间取最小值,也就是包裹内容*/
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }
}
