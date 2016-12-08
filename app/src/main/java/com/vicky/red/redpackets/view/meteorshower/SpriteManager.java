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
    private ScoreSprite scoreSprite;
    private TimerSprite timerSprite;

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
        scoreSprite = new ScoreSprite(contextRef.get(),pWidth,pHeight);
        timerSprite = new TimerSprite(contextRef.get(),pwidth,pheight);

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
     * 更新分数
     * @param score
     */
    public void updateScore(int score){
        scoreSprite.updateScore(score);
    }

    /**
     * 更新倒计时时间
     * @param time
     */
    public void updateTime(int time){
        timerSprite.updateTime(time);
    }

    /**
     * 绘制
     * @param canvas
     */
    public void draw(Canvas canvas){
        if (isOver){
            return;
        }
        for (int i = 0, size = sprites.size();i < size;i++){
            sprites.get(i).draw(canvas);
        }
        scoreSprite.draw(canvas);
        timerSprite.draw(canvas);
    }

    /**
     * 清除脏数据
     */
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

    /**
     * 判断坐标是否点击到某个精灵
     * @param x
     * @param y
     * @return
     */
    public BaseSpite isContains(float x, float y){
        for (int i = 0, size = sprites.size(); i < size; i++){
            BaseSpite baseSpite = sprites.get(i);
            if (baseSpite.isContains(x,y)&& baseSpite.clickable){
                return baseSpite;
            };
        }
        return null;
    }

    /**
     * 停止
     */
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

        scoreSprite.recycle();
        timerSprite.recycle();
    }


}
