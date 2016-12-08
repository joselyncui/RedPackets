package com.vicky.red.redpackets.view.meteorshower;/**
 * Created by lenovo on 2016/12/6.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vicky.red.redpackets.R;
import com.vicky.red.redpackets.util.DensityUtil;

import java.util.Random;

/**
 * 流星线精灵
 * create by yao.cui at 2016/12/6
 */
public class LineSprite extends LineAnimSprite {
    private int[] mImgIds = new int[]{
            R.drawable.xian1,
            R.drawable.xian2
    };
    private Random mRandom = new Random();

    public LineSprite(Context context,int pWidth, int pHeight){
        super(context,pWidth,pHeight);

        int index = mRandom.nextInt(mImgIds.length);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), mImgIds[index]);
        srcBmp = scaleBmp(bmp, DensityUtil.dp2px(context,140),true);

        point = newPosition(true,0,0);

        mAx = -0.6f;
        mAy = 0.9f;
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
