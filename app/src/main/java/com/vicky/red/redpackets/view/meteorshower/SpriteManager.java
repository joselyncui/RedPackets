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

    private boolean isOver;

    private ArrayList<BaseSpite>  sprites= new ArrayList<>();

    private static class SingleTonHolder {
        private static final SpriteManager INSTANCE = new SpriteManager();
    }

    public static SpriteManager getInstance() {
        return SingleTonHolder.INSTANCE;
    }
    
    private SpriteManager() {
    }

    public void init(Context context,int pwidth,int pheight){
        this.pWidth = pwidth;
        this.pHeight = pheight;

        isOver = false;
        this.contextRef = new WeakReference<Context>(context);

    }

    /**
     * 添加红包精灵
     */
    public void addMeteorSprite(){
        if (isOver){
            return;
        }
        MeteorSprite sprite = new MeteorSprite(contextRef.get(),pWidth,pHeight);
        sprites.add(sprite);
    }

    public void addBoom(int x, int y){
        if (isOver){
            return;
        }
        BoomSprite boomSprite = new BoomSprite(contextRef.get(),pWidth,pHeight);
        boomSprite.setPosition(x,y);
        sprites.add(boomSprite);
    }

    public void addLine(){
        if (isOver){
            return;
        }
        LineSprite lineSprite = new LineSprite(contextRef.get(),pWidth,pHeight);

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

        for (int i = 0,size = sprites.size();i< size;i++){
            sprites.get(i).recycle();
        }
        sprites.clear();
    }


}
