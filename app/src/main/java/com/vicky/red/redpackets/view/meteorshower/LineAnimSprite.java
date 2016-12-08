package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.Canvas;

/**
 * 线性运动的精灵
 * create by yao.cui at 2016/12/7
 */
public class LineAnimSprite extends BaseSpite {
    protected float mAx = -0.5f;
    protected float mAy = 0.8f;

    public LineAnimSprite(Context context, int pWidth, int pHeihgt) {
        super(context, pWidth, pHeihgt);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        updatePosition();
        canvas.drawBitmap(srcBmp,point[0],point[1],paint);
        canvas.restore();
    }

    protected void updatePosition(){
        time++;
        point[0] += mAx * time;
        point[1] += mAy * time;
    }

    @Override
    public void stop() {
        isOver = true;
    }

    @Override
    public void recycle() {

    }

    /**
     * 重置精灵位置数据，用于复用
     */
    public void reset(){

    }
}
