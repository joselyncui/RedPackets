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
public class TimerSprite extends BaseSpite {
    private Paint txtPaint;
    private Rect mScoreBounds = new Rect();
    private Rect mTargetRect;
    private Rect mOrgRect;
    private int time;//倒计时还剩多少秒

    public TimerSprite(Context context,int pWidth, int pHeight){
        super(context,pWidth,pHeight);
        srcBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.coutdown);

        int targetWidth =(int) (pWidth/2.5f);
        int targetHeight = targetWidth* srcBmp.getHeight()/srcBmp.getWidth();
        mTargetRect = new Rect(30, DensityUtil.dp2px(context,10)
                ,targetWidth+30,DensityUtil.dp2px(context,10)+targetHeight);
        mOrgRect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(Color.RED);
        txtPaint.setTextSize(DensityUtil.sp2px(context,20));
        txtPaint.getTextBounds("000",0,3,mScoreBounds);
    }


    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(srcBmp,mOrgRect,mTargetRect,new Paint());
        canvas.drawText(time+" ",mTargetRect.centerX()-mScoreBounds.width()/2,
                mTargetRect.centerY()+mScoreBounds.height()/2,txtPaint);
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
        this.time = time;
    }
}
