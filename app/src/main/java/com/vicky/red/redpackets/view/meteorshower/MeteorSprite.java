package com.vicky.red.redpackets.view.meteorshower;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.AccelerateInterpolator;

/**
 * create by yao.cui at 2016/12/1
 */
public class MeteorSprite  extends BaseSpite {
    private Bitmap newBmp;
    private ValueAnimator animator;
    protected float factor = 1.6f;

        @Override
    public void init(Bitmap srcBmp,Paint paint,int pWidth,int pHeihgt,int angle) {
        super.init(srcBmp, paint, pWidth, pHeihgt, angle);

        int oldWidth = width;
        int oldHeight = height;

        width = 100+random.nextInt(oldWidth/2);
        height = width* oldHeight/oldWidth;
        newBmp = Bitmap.createScaledBitmap(srcBmp,width,height,false);
        clickable = true;

        initAnimator();
    }

    private void initAnimator(){
        final int[] point = newPosition(true,0,0);

        animator = ValueAnimator.ofInt(point[0],-newBmp.getWidth());
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                x = (int)valueAnimator.getAnimatedValue();
                y =(int) (Math.abs(Math.tan(angle)) *(point[0]- x)) + point[1];
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOver = true;
            }
        });

        animator.setDuration((int)(startX*factor));
        animator.start();
    }


    /**
     * 绘制图片
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas){
        canvas.save();
        canvas.drawBitmap(newBmp,x,y,paint);

        canvas.restore();
    }

    @Override
    public void stop(){
        animator.cancel();
        isOver = true;
    }

    @Override
    public void recycle(){
        if (newBmp!= null && !newBmp.isRecycled()){
            newBmp.recycle();
        }
        if (animator != null){
            animator =null;
        }
    }
}
