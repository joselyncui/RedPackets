package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.vicky.red.redpackets.R;
import com.vicky.red.redpackets.util.DensityUtil;

/**
 * 爆炸效果的精灵
 * create by yao.cui at 2016/12/2
 */
public class BoomSprite extends BaseSprite {

    private float scale = 0.1f;

    public BoomSprite(Context context, int pWidth, int pHeight){
        super(context,pWidth,pHeight);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);
        srcBmp = scaleBmp(bmp, DensityUtil.dp2px(context,100),true);

    }

    @Override
    public void stop() {

    }

    public void setPosition(int x, int y){
        point[0] = x;
        point[1]= y;
    }
    @Override
    public void draw(Canvas canvas) {
        if (scale >= 1){
            isOver = true;
            return;
        }

        canvas.save();

        int scaleWidth = (int)(srcBmp.getWidth() * scale);
        scale += 0.1f;

        Bitmap newBmp = scaleBmp(srcBmp,scaleWidth,false);
        canvas.drawBitmap(newBmp,point[0]-newBmp.getWidth()/2,point[1]-newBmp.getHeight()/2,paint);
        canvas.restore();
    }

}
