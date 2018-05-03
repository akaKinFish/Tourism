package com.example.rog.tourism.cityselect.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.rog.tourism.R;

/**
 * ------------------------------------------
 * tourism
 * com.example.rog.tourism.cityselect.view
 * ROG
 * 2018/04/29/16:18
 * by KinFish
 * -------------------------------------------
 **/
public class SlideLetterBar extends View {
    private static final String[] letter = {"定位", "热门", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int choose = -1;
    private Paint mPaint = new Paint();
    private boolean showBg = false;
    private OnLetterChangedListener mOnLetterChangedListener;
    private TextView overlay;   //字母框

    public SlideLetterBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public SlideLetterBar(Context context,AttributeSet attrs){
        super(context,attrs);
    }
    public SlideLetterBar(Context context){
        super(context);
    }

    /*悬浮textview*/

    public void setOverlay(TextView overlay) {
        this.overlay = overlay;
    }

    @SuppressWarnings("deprecation")

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(showBg){
            canvas.drawColor(Color.TRANSPARENT);   //transparent = 0
        }

        int height = getHeight();
        int width = getWidth();
        int SingleHeight = height/letter.length;  //单个字母高度

        for(int i = 0;i < letter.length; i++){
            mPaint.setTextSize(getResources().getDimension(R.dimen.side_letter_bar_letter_size));
            mPaint.setColor(getResources().getColor(R.color.cityselect_gray));
            /*抗锯齿*/
            mPaint.setAntiAlias(true);
            if(i == choose){
                mPaint.setColor(getResources().getColor(R.color.cityselect_gray_deep));
                /*加粗*/
                mPaint.setFakeBoldText(true);
            }

            /*measureText获取字符串显示的宽度值*/
            float xPos = width/2 - mPaint.measureText(letter[i])/2;
            float yPos = SingleHeight * i + SingleHeight;
            canvas.drawText(letter[i],xPos,yPos,mPaint);
            mPaint.reset();
        }



    }

    /*事件拦截*/

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        /*获取事件点击并判断*/
        final float y = event.getY();
        final int oldChoose = choose;
        final OnLetterChangedListener listener = mOnLetterChangedListener;
        /*回去字母所在位置*/
        final int c = (int) (y/getHeight() * letter.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if(oldChoose != c && listener != null){
                    if(c >=0 && c < letter.length) {
                        listener.onLetterChanged(letter[c]);
                        choose = c;
                        invalidate();//更新
                        if(overlay!=null){
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(letter[c]);
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (oldChoose != c && listener != null) {
                    if (c >= 0 && c < letter.length) {
                        listener.onLetterChanged(letter[c]);
                        choose = c;
                        invalidate();
                        if (overlay != null){
                            overlay.setVisibility(VISIBLE);
                            overlay.setText(letter[c]);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                invalidate();
                if (overlay != null){
                    overlay.setVisibility(GONE);
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /*外部接口通过回调回listener*/

    public void setmOnLetterChangedListener(OnLetterChangedListener mOnLetterChangedListener) {
        this.mOnLetterChangedListener = mOnLetterChangedListener;
    }

    public interface OnLetterChangedListener{
        void onLetterChanged(String letter);

    }
}
