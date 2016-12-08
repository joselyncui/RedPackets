package com.vicky.red.redpackets.view.bezier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.vicky.red.redpackets.view.bezier.BezierEvaluator;

import java.util.Random;

/**
 * create by yao.cui at 2016/11/29
 */
public class Sprites {
    public int x;
    public int y;
    public Bitmap bitmap;
    public boolean isOver = false;

    private Random random;
    private int mParentHeight;
    private int mParentWidth;
    private ValueAnimator mAnimator;

    public Sprites(Bitmap bitmap,int pWidth,int pHeight){
        random = new Random();
        this.x = random.nextInt(pWidth-bitmap.getWidth());
        this.y = 0;
        this.bitmap = bitmap;
        this.mParentWidth = pWidth;
        this.mParentHeight = pHeight;

        initAnimator();
    }

    private void initAnimator(){

        PointF point0 = new PointF(random.nextInt(mParentWidth-bitmap.getWidth()),-bitmap.getHeight());
        PointF point1 = new PointF(point0.x-bitmap.getWidth()+random.nextInt((int)bitmap.getWidth()*2),random.nextInt((mParentHeight-100)/2));
        PointF point2 = new PointF(point0.x-bitmap.getWidth()+random.nextInt((int)bitmap.getWidth()*2),mParentHeight/2+random.nextInt((mParentHeight/2)));
        PointF point3 = new PointF(point0.x-bitmap.getWidth()+random.nextInt((int)bitmap.getWidth()*2),mParentHeight);

        BezierEvaluator evaluator = new BezierEvaluator(point1,point2);//传入中间两个点
        mAnimator = ValueAnimator.ofObject(evaluator,point0,point3);//传入开始位置结束位置
        mAnimator.addUpdateListener(new BezierListener());
        mAnimator.setDuration(3000);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOver = true;
            }
        });
        mAnimator.start();
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener{

        public BezierListener() {
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            PointF pointF = (PointF) valueAnimator.getAnimatedValue();
            x = (int)pointF.x;
            y = (int)pointF.y;
        }
    }



    public boolean isInner(int x, int y){
//        Region region = new Region(this.x,this.y,this.x+bitmap.getWidth(),this.y+bitmap.getHeight());
//        return region.contains(x,y);

        return this.x < x && this.x + bitmap.getWidth() > x && this.y< y && this.y +bitmap.getHeight()>y;
    }

    public void stop(){
        if (mAnimator!= null){
            mAnimator.cancel();
            mAnimator = null;
        }
        isOver = true;
    }

    public void draw(Canvas canvas, Paint p){
        canvas.drawBitmap(bitmap,x,y,p);
    }
}
