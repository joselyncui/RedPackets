package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.Canvas;

/**
 * create by yao.cui at 2016/12/7
 */
public class LineAnimSprite extends BaseSpite {
    protected float ax = -0.5f;
    protected float ay = 0.8f;

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
        point[0] += ax * time;
        point[1] += ay * time;
    }

    @Override
    public void stop() {
        isOver = true;
    }

    @Override
    public void recycle() {

    }

    public void reset(){

    }
}
