package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.vicky.red.redpackets.R;
import com.vicky.red.redpackets.util.DensityUtil;

/**
 * create by yao.cui at 2016/12/8
 */
public class TimerSprite extends BaseSprite {
    private Paint mTxtPaint;
    private Rect mScoreBounds = new Rect();
    private Rect mTargetRect;
    private Rect mOrgRect;
    private int mTime;//倒计时还剩多少秒

    public TimerSprite(Context context,int pWidth, int pHeight){
        super(context,pWidth,pHeight);
        srcBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.coutdown);

        int targetWidth =(int) (pWidth/2.5f);
        int targetHeight = targetWidth* srcBmp.getHeight()/srcBmp.getWidth();
        mTargetRect = new Rect(30, DensityUtil.dp2px(context,10)
                ,targetWidth+30,DensityUtil.dp2px(context,10)+targetHeight);
        mOrgRect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());

        mTxtPaint = new Paint();
        mTxtPaint.setAntiAlias(true);
        mTxtPaint.setColor(Color.RED);
        mTxtPaint.setTextSize(DensityUtil.sp2px(context,20));
        mTxtPaint.getTextBounds("000",0,3,mScoreBounds);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(srcBmp,mOrgRect,mTargetRect,new Paint());
        canvas.drawText(mTime +" ",mTargetRect.centerX()-mScoreBounds.width()/2,
                mTargetRect.centerY()+mScoreBounds.height()/2, mTxtPaint);
    }

    @Override
    public void recycle() {
        super.recycle();
    }

    @Override
    public void stop() {
        isOver = true;
    }

    public void updateTime(int time){
        this.mTime = time;
    }
}
