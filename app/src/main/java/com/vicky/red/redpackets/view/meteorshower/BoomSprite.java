package com.vicky.red.redpackets.view.meteorshower;/**
 * Created by lenovo on 2016/12/2.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * create by yao.cui at 2016/12/2
 */
public class BoomSprite extends BaseSpite {
    private ValueAnimator valueAnimator;
    private float scale;
    private int x;
    private int y;

    @Override
    public void recycle() {

    }

    @Override
    public void stop() {
        if (valueAnimator!= null){
            valueAnimator.cancel();
        }
        isOver = true;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        int scaleWidth = (int)(srcBmp.getWidth() * scale);
        int scaleHeight = (int)(srcBmp.getHeight() * scale);

        Bitmap newBmp = Bitmap.createScaledBitmap(srcBmp,scaleWidth,scaleHeight,false);
        canvas.drawBitmap(newBmp,x-newBmp.getWidth()/2,y-newBmp.getHeight()/2,paint);
        canvas.restore();
        newBmp.recycle();
    }

    @Override
    public void init(Bitmap srcBmp, Paint paint, int pWidth, int pHeihgt, int angle) {
        super.init(srcBmp,paint,pWidth,pHeihgt,angle);

        valueAnimator = ValueAnimator.ofFloat(0.1f,1);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                scale = (float)valueAnimator.getAnimatedValue();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOver = true;
            }
        });
        valueAnimator.start();
    }
}
