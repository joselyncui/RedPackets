package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.vicky.red.redpackets.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * create by yao.cui at 2016/12/2
 */
public class SpriteManager {

    private WeakReference<Context> contextRef;
    private static int pWidth;
    private static int pHeight;

    private Bitmap[] redBmps;
    private Bitmap[] xianBmps;
    private Bitmap boomBmp;
    private Random random;
    private Paint paint;
    private boolean isOver;

    private ArrayList<BaseSpite> sprites = new ArrayList<>();

    private static class SingleTonHolder {
        private static final SpriteManager INSTANCE = new SpriteManager();
    }

    public static SpriteManager getInstance(int pwidth,int pheight) {
        pWidth = pwidth;
        pHeight = pheight;
        return SingleTonHolder.INSTANCE;
    }
    
    private SpriteManager() {

    }

    public void init(Context context){
        isOver = false;
        this.contextRef = new WeakReference<Context>(context);
        redBmps = new Bitmap[5];
        redBmps[0] = scaleBmp(BitmapFactory.decodeResource(context.getResources(), R.drawable.hongb0),200);
        redBmps[1] = scaleBmp(BitmapFactory.decodeResource(context.getResources(), R.drawable.hongb1),200);
        redBmps[2] = scaleBmp(BitmapFactory.decodeResource(context.getResources(), R.drawable.hongb2),200);
        redBmps[3] = scaleBmp(BitmapFactory.decodeResource(context.getResources(), R.drawable.hongb3),200);
        redBmps[4] = scaleBmp(BitmapFactory.decodeResource(context.getResources(), R.drawable.hongb4),200);
//        redBmps[5] = scaleBmp(BitmapFactory.decodeResource(context.getResources(),R.drawable.xian1),250);
//        redBmps[6] = scaleBmp(BitmapFactory.decodeResource(context.getResources(),R.drawable.xian2),250);

        boomBmp = scaleBmp(BitmapFactory.decodeResource(context.getResources(),R.drawable.boom),200);

        xianBmps = new Bitmap[2];
        xianBmps[0] = scaleBmp(BitmapFactory.decodeResource(context.getResources(),R.drawable.xian1),350);
        xianBmps[1] = scaleBmp(BitmapFactory.decodeResource(context.getResources(),R.drawable.xian2),350);

        random = new Random();
        paint = new Paint();
    }

    /**
     * 添加红包精灵
     */
    public void addMeteorSprite(){
        if (isOver){
            return;
        }
        MeteorSprite sprite = new MeteorSprite();
        sprite.init(redBmps[getRdmInt(0,redBmps.length)],paint,pWidth,pHeight,145);
        sprites.add(sprite);
    }

    public void addBoom(int x, int y){
        if (isOver){
            return;
        }
        BoomSprite boomSprite = new BoomSprite();
        boomSprite.init(boomBmp,paint,pWidth,pHeight,0);
        boomSprite.setPosition(x,y);
        sprites.add(boomSprite);
    }

    public void addLine(){
        if (isOver){
            return;
        }
        LineSprite lineSprite = new LineSprite();
        lineSprite.init(xianBmps[random.nextInt(xianBmps.length)],paint,pWidth,pHeight,150);
        sprites.add(lineSprite);
    }

    /**
     * 绘制
     * @param canvas
     */
    public void draw(Canvas canvas){
        for (int i = 0, size = sprites.size();i < size;i++){
            sprites.get(i).draw(canvas);
        }
    }

    public void cleanData(){
        List<BaseSpite> oldSprites = new ArrayList<>();

        for (int i = 0, size = sprites.size(); i < size;i++){
            if (sprites.get(i).isOver){
                oldSprites.add(sprites.get(i));
            }
        }

        for (int i = 0, size=oldSprites.size();i<size;i++){
            oldSprites.get(i).recycle();
            sprites.remove(oldSprites.get(i));
        }
    }

    public BaseSpite isContains(float x, float y){
        for (int i = 0, size = sprites.size(); i < size; i++){
            BaseSpite baseSpite = sprites.get(i);
            if (baseSpite.isContains(x,y)&& baseSpite.clickable){
                return baseSpite;
            };
        }
        return null;
    }

    public void stop(){
        isOver = true;
        recycle();
    }

    /**
     * 回收
     */
    public void recycle(){
        for (int i = 0, size = redBmps.length; i< size;i++){
            recycleBmp(redBmps[i]);
        }

        recycleBmp(boomBmp);
        for (int i = 0, size = xianBmps.length; i < size;i++){
            recycleBmp(xianBmps[i]);
        }

        for (int i = 0,size = sprites.size();i< size;i++){
            sprites.get(i).recycle();
        }

        sprites.clear();
    }
    private int getRdmInt(int start, int end){
        return start + random.nextInt(end-start);
    }

    private Bitmap scaleBmp(Bitmap srcBmp, int targetWidth){
        int height = targetWidth* srcBmp.getHeight()/srcBmp.getWidth() ;
        Bitmap newBmp = Bitmap.createScaledBitmap(srcBmp,targetWidth,height,false);
        srcBmp.recycle();
        return newBmp;
    }

    private void recycleBmp(Bitmap bmp){
        if (bmp != null && !bmp.isRecycled()){
            bmp.recycle();
            bmp = null;
        }
    }

}
