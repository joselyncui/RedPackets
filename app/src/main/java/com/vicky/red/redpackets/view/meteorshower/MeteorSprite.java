package com.vicky.red.redpackets.view.meteorshower;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.AccelerateInterpolator;

import com.vicky.red.redpackets.R;
import com.vicky.red.redpackets.util.DensityUtil;

import java.util.Random;

/**
 * 红包雨精灵
 * create by yao.cui at 2016/12/1
 */
public class MeteorSprite extends LineAnimSprite {

    private int[] imgIds = new int[]{
        R.drawable.hongb0,
        R.drawable.hongb1,
        R.drawable.hongb2,
        R.drawable.hongb3,
        R.drawable.hongb4
    };
    private Random random = new Random();

    public MeteorSprite(Context context, int pWidth, int pHeihgt) {
        super(context,pWidth,pHeihgt);
        clickable = true;

        int index = random.nextInt(imgIds.length);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),imgIds[index]);
        width = DensityUtil.dp2px(context,60)+random.nextInt(DensityUtil.dp2px(context,30));
        srcBmp = scaleBmp(bmp,width,true);
        bmp.recycle();
        height = srcBmp.getHeight();

        point = newPosition(true,0,0);
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        //精灵滑出屏幕标记可完成，使其被回收
        if (point[0] + srcBmp.getWidth()< 0){
            isOver = true;
        }
    }

    @Override
    public void reset() {
        super.reset();
        point = newPosition(true,0,0);
        time = 0;
    }
}
