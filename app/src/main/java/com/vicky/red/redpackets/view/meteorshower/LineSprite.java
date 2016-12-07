package com.vicky.red.redpackets.view.meteorshower;/**
 * Created by lenovo on 2016/12/6.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.vicky.red.redpackets.R;
import com.vicky.red.redpackets.util.DensityUtil;

import java.util.Random;

/**
 * create by yao.cui at 2016/12/6
 */
public class LineSprite extends LineAnimSprite {
    private int[] imgIds = new int[]{
            R.drawable.xian1,
            R.drawable.xian2
    };
    private Random random = new Random();

    public LineSprite(Context context,int pWidth, int pHeight){
        super(context,pWidth,pHeight);

        int index = random.nextInt(imgIds.length);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imgIds[index]);
        srcBmp = scaleBmp(bmp, DensityUtil.dp2px(context,140),true);

        point = newPosition(true,0,0);

        ax = -0.6f;
        ay = 0.9f;
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        if (point[0] + srcBmp.getWidth() < 0){
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
