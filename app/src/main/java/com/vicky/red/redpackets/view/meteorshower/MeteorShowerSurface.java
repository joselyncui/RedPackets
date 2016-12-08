package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.vicky.red.redpackets.R;

import java.util.Random;

/**
 * create by yao.cui at 2016/12/1
 */
public class MeteorShowerSurface extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener{
    private final String TAG="MeteorShowerSurface";

    private Thread mDrawThread; // thread to loop drawing
    private Thread mAddThread; // thread to add redpacket
    private Thread mAddLineThread; //thread to add line image

    private boolean isGameOver = false;
    private SurfaceHolder mHolder;

    private int mHeight;//view height
    private int mWidth;//view width


    private int mScore;

    private int redCount;//the count of redpacket

    private SpriteManager mSpriteManager;
    private Context mContext;


    public MeteorShowerSurface(Context context) {
        super(context);
        init(context);
    }

    public MeteorShowerSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MeteorShowerSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.mContext = context;
        setZOrderOnTop(true);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mDrawThread = new Thread(new DrawThread());
        mAddThread = new Thread(new AddMeteorThread());
        mAddLineThread = new Thread(new AddLineThread());


        setOnTouchListener(this);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = getMeasuredWidth();
        this.mHeight = getMeasuredHeight();
        mSpriteManager = SpriteManager.getInstance();
        mSpriteManager.init(mContext,mWidth,mHeight);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isGameOver = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isGameOver = true;
        mSpriteManager.stop();
    }

    /**
     * 开始绘制线程
     */
    public void start(){
        if (mDrawThread!= null){
            mDrawThread.start();
        }
        if (mAddThread!= null){
            mAddThread.start();
        }
        if (mAddLineThread != null){
            mAddLineThread.start();
        }
    }

    public void stop(){
        isGameOver = true;
    }

    private void clean(){
        mSpriteManager.cleanData();
    }

    private void recycle(){
        Log.i(TAG,"=====recycle");
        mSpriteManager.recycle();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                BaseSpite sprite = mSpriteManager.isContains(x,y);
                if (sprite != null){
                    mSpriteManager.addBoom((int)x,(int)y);
                    mScore++;
                    mSpriteManager.updateScore(mScore);
                    sprite.stop();
                }

                break;
        }
        return true;
    }

    class DrawThread implements Runnable{
        @Override
        public void run() {
            while(!isGameOver){
                clean();//清除滚出去的数据
                Canvas canvas = null;
                synchronized (mHolder){
                    canvas = mHolder.lockCanvas();

                    if (canvas== null){
                        isGameOver = true;
                        return;
                    }

                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    mSpriteManager.draw(canvas);

                    mHolder.unlockCanvasAndPost(canvas);

//                    try {
//                        Thread.sleep(10);
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }

                }
            }
        }
    }

    class AddMeteorThread implements Runnable{
        @Override
        public void run() {
            while (!isGameOver){
                try {
                    Thread.sleep(90);
                } catch (Exception e){
                    e.printStackTrace();
                }
                mSpriteManager.addMeteorSprite();
            }
        }
    }

    class AddLineThread implements Runnable{
        @Override
        public void run() {
            while (!isGameOver){
                try{
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                mSpriteManager.addLine();
            }
        }
    }

}
