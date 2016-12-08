package com.vicky.red.redpackets.view.meteorshower;/**
 * Created by lenovo on 2016/12/8.
 */

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
public class ScoreSprite extends BaseSpite {

    private Paint txtPaint;
    private Rect mScoreBounds = new Rect();
    private Rect mTargetRect;
    private Rect mOrgRect;
    private int score;

    public ScoreSprite(Context context, int pWidth, int pHeight){
        super(context,pWidth,pHeight);

        srcBmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.hbs);
        int targetWidth =(int) (pWidth/2.5f);
        int targetHeight = targetWidth* srcBmp.getHeight()/srcBmp.getWidth();
        mTargetRect = new Rect(pWidth-targetWidth-30,DensityUtil.dp2px(context,10)
                ,pWidth-30,DensityUtil.dp2px(context,10)+targetHeight);
        mOrgRect = new Rect(0,0,srcBmp.getWidth(),srcBmp.getHeight());

        txtPaint = new Paint();
        txtPaint.setAntiAlias(true);
        txtPaint.setColor(Color.RED);
        txtPaint.setTextSize(DensityUtil.sp2px(context,20));
        txtPaint.getTextBounds("000",0,3,mScoreBounds);
    }

    public void updateScore(int score){
        this.score = score;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(srcBmp,mOrgRect,mTargetRect,new Paint());
        canvas.drawText(score+" ",mTargetRect.centerX()-mScoreBounds.width()/2,
                mTargetRect.centerY()+mScoreBounds.height()/2,txtPaint);
    }

    @Override
    public void stop() {
    }

    @Override
    public void recycle() {
        super.recycle();
    }
}
