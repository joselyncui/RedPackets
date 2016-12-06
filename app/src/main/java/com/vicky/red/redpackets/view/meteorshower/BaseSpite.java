package com.vicky.red.redpackets.view.meteorshower;

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
    protected int x;
    protected int y;
    protected int angle;
    protected Bitmap srcBmp;
    protected int startX;
    private int dif;
    protected boolean clickable;

    public BaseSpite(){}

    public void init(Bitmap srcBmp,Paint paint,int pWidth,int pHeihgt,int angle){
        this.srcBmp = srcBmp;
        this.paint = paint;
        this.pWidth = pWidth;
        this.pHeight = pHeihgt;
        this.angle = angle;
        this.width = srcBmp.getWidth();
        this.height = srcBmp.getHeight();
        this.random = new Random();
    }
    /**
     * 用于绘制界面展示内容
     * @param canvas
     */
    public abstract void draw(Canvas canvas);

    public abstract void stop();

    public abstract void recycle();

    protected int[] newPosition(boolean isRandom,int x,int y){
        int[] point = new int[2];
        if (isRandom){
            startX = pWidth/3+random.nextInt((int)(pWidth*1.5f));

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
        return this.x-dif < x && this.x+dif + width > x && this.y-dif< y && this.y+dif +height>y;
    };

    protected void recycleBmp(Bitmap bitmap){
        if (bitmap!= null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }
}
