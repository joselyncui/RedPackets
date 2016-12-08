package com.vicky.red.redpackets.view.bezier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.Random;

/**
 * create by yao.cui at 2016/11/29
 */
public class Sprites {
    public int mX;
    public int mY;
    public Bitmap mBitmap;
    public boolean isOver = false;

    private Random mRandom;
    private int mParentHeight;
    private int mParentWidth;
    private ValueAnimator mAnimator;

    public Sprites(Bitmap bitmap,int pWidth,int pHeight){
        mRandom = new Random();
        this.mX = mRandom.nextInt(pWidth-bitmap.getWidth());
        this.mY = 0;
        this.mBitmap = bitmap;
        this.mParentWidth = pWidth;
        this.mParentHeight = pHeight;

        initAnimator();
    }

    private void initAnimator(){

        PointF point0 = new PointF(mRandom.nextInt(mParentWidth- mBitmap.getWidth()),-mBitmap.getHeight());
        PointF point1 = new PointF(point0.x- mBitmap.getWidth()+ mRandom.nextInt((int) mBitmap.getWidth()*2), mRandom.nextInt((mParentHeight-100)/2));
        PointF point2 = new PointF(point0.x- mBitmap.getWidth()+ mRandom.nextInt((int) mBitmap.getWidth()*2),mParentHeight/2+ mRandom.nextInt((mParentHeight/2)));
        PointF point3 = new PointF(point0.x- mBitmap.getWidth()+ mRandom.nextInt((int) mBitmap.getWidth()*2),mParentHeight);

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
            mX = (int)pointF.x;
            mY = (int)pointF.y;
        }
    }



    public boolean isInner(int x, int y){
//        Region region = new Region(this.mX,this.mY,this.mX+mBitmap.getWidth(),this.mY+mBitmap.getHeight());
//        return region.contains(mX,mY);

        return this.mX < x && this.mX + mBitmap.getWidth() > x && this.mY < y && this.mY + mBitmap.getHeight()>y;
    }

    public void stop(){
        if (mAnimator!= null){
            mAnimator.cancel();
            mAnimator = null;
        }
        isOver = true;
    }

    public void draw(Canvas canvas, Paint p){
        canvas.drawBitmap(mBitmap, mX, mY,p);
    }
}
