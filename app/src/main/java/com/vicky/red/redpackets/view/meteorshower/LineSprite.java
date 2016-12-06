package com.vicky.red.redpackets.view.meteorshower;/**
 * Created by lenovo on 2016/12/6.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * create by yao.cui at 2016/12/6
 */
public class LineSprite extends MeteorSprite {
    @Override
    public void init(Bitmap srcBmp, Paint paint, int pWidth, int pHeihgt, int angle) {
        super.init(srcBmp, paint, pWidth, pHeihgt, angle);
        clickable = false;
        factor = 1.4f;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public void recycle() {
        super.recycle();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
