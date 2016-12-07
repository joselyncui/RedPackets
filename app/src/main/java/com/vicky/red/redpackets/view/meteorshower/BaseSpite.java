package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;

/**
 * 素材基础类
 *
 * create by yao.cui at 2016/12/1
 */
public abstract class BaseSpite {

    public boolean isOver = false;
    protected Random random;
    protected int pWidth;
    protected int pHeight;
    protected Paint paint;
    protected int width;
    protected int height;

    protected int[] point = new int[2];
    protected int angle;
    protected Bitmap srcBmp;
    protected int startX;
    private int dif;
    protected boolean clickable;
    protected int time;

    public BaseSpite(Context context,int pWidth, int pHeight){
        this.pWidth = pWidth;
        this.pHeight = pHeight;
        this.random = new Random();
        this.paint = new Paint();
    }

    /**
     * 用于绘制界面展示内容
     * @param canvas
     */
    public abstract void draw(Canvas canvas);

    public abstract void stop();

    public void recycle(){
        recycleBmp(srcBmp);
    };

    protected int[] newPosition(boolean isRandom,int x,int y){
        int[] point = new int[2];
        if (isRandom){
            startX = pWidth/5+random.nextInt((int)(pWidth*1.5f));

            point[0] = startX;

            if (startX > pWidth){
                point[0] = pWidth + width;
                point[1] = (int)(Math.abs(Math.tan(angle)) * (startX-point[0]));
            }
        } else {
            point[0] = x;
            point[1] =y;
            startX = x;
        }

        return point;
    }

    /**
     * 判断当前点是否包含在区域内
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isContains(float x, float y){
        return this.point[0]-dif < x && this.point[0]+dif + width > x
                && this.point[1]-dif< y && this.point[1]+dif +height>y;
    };

    protected void recycleBmp(Bitmap bitmap){
        if (bitmap!= null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

    protected Bitmap scaleBmp(Bitmap srcBmp, int targetWidth,boolean recycle){
        int height = targetWidth* srcBmp.getHeight()/srcBmp.getWidth() ;
        Bitmap newBmp = Bitmap.createScaledBitmap(srcBmp,targetWidth,height,false);
        if (recycle){
            srcBmp.recycle();
        }

        return newBmp;
    }
}
