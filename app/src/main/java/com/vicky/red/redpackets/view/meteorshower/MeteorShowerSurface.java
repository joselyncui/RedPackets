package com.vicky.red.redpackets.view.meteorshower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * create by yao.cui at 2016/12/1
 */
public class MeteorShowerSurface extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener{
    private final String TAG="MeteorShowerSurface";

    private Thread mDrawThread; // 用来绘制的线程
    private Thread mAddThread; // 用于添加红包的线程
    private Thread mAddLineThread; //用于添加流星线的线程

    private boolean isGameOver = false;
    private SurfaceHolder mHolder;

    private int mHeight;//该surface的高度
    private int mWidth;//该surface的宽度

    private int mScore;//点中红包数量

    private int mRedCount = 100;//红包数量
    private int mDuration = 10*1000;//红包雨时长

    private SpriteManager mSpriteManager;
    private Context mContext;
    //倒计时
    private CountDownTimer mCountDownTimer;
    private GameListener mGameListener;
    private int mAddPacketInterval;

    public interface GameListener{
        /**
         * 开始之前调用
         */
        void preGame();

        /**
         * 开始种，倒计时循环调用
         */
        void inGameInterval();

        /**
         * 游戏完成即倒计时结束调用
         * @param score
         */
        void postGame(int score);
    }


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
        if (mGameListener != null){
            mGameListener.preGame();
        }

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
     * 设置红包雨时长
     * @param duration 毫秒
     * @return
     */
    public MeteorShowerSurface setDuration(int duration){
        this.mDuration = duration;
        mCountDownTimer = new CountDownTimer(duration,1000) {
            @Override
            public void onTick(long l) {
                mSpriteManager.updateTime((int)(l/1000));
                if (mGameListener != null){
                    mGameListener.inGameInterval();
                }
            }

            @Override
            public void onFinish() {
                mSpriteManager.updateTime(0);
                isGameOver = true;
                if (mGameListener != null){
                    mGameListener.postGame(mScore);
                }
            }
        };
        return this;
    }

    /**
     * 设置红包数量
     * @param count
     * @return
     */
    public MeteorShowerSurface setRedCount(int count){
        this.mRedCount = count;
        return this;
    }

    public void setmGameListener(GameListener listener){
        this.mGameListener = listener;
    }
    /**
     * 开始绘制线程
     */
    public void start(){
        if (mRedCount<=0){
            return;
        }
        mAddPacketInterval = mDuration / mRedCount;
        if (mDrawThread!= null){
            mDrawThread.start();
        }
        if (mAddThread!= null){
            mAddThread.start();
        }
        if (mAddLineThread != null){
            mAddLineThread.start();
        }

        //开始倒计时
        mCountDownTimer.start();
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
                BaseSprite sprite = mSpriteManager.isContains(x,y);
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
//                        Thread.sleep(5);
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }
                }
            }

            mSpriteManager.stop();
        }
    }

    class AddMeteorThread implements Runnable{
        @Override
        public void run() {
            while (!isGameOver){
                mSpriteManager.addMeteorSprite();
                try {
                    Thread.sleep(mAddPacketInterval);
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

    class AddLineThread implements Runnable{
        @Override
        public void run() {
            while (!isGameOver){
                mSpriteManager.addLine();
                try{
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }

}
