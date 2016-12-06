package com.vicky.red.redpackets.view.chris;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public abstract class BaseGameSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {

    public interface GameListener {
        public void gameOver();
    }

    /***********************************************************************************************
     * Global System Parameters
     ***********************************************************************************************/
    protected final long TIME_SLOT = 50;
    protected final long DEFAULT_MILISEC = 10000;

    /***********************************************************************************************
     * Surface View width & height
     ***********************************************************************************************/
    protected int viewWidth = 0;
    protected int viewHeight = 0;
    private SurfaceHolder holder = null;

    /***********************************************************************************************
     * Game Control
     ***********************************************************************************************/
    private Thread gameThread = null;
    private boolean stop = false;
    private GameListener listener = null;

    /***********************************************************************************************
     * Parameters
     ***********************************************************************************************/
    private long miliseconds = DEFAULT_MILISEC;

    public BaseGameSurfaceView(Context context) {
        this(context, null);
    }

    public BaseGameSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseGameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseGameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setZOrderOnTop(true);
        holder = this.getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSPARENT);
        setOnTouchListener(this);
        inits();
    }

    /***********************************************************************************************
     * SurfaceView related functions
     ***********************************************************************************************/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new Thread(this);
        stop = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        viewWidth = width;
        viewHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop = true;
        miliseconds = 0;
    }

    /***********************************************************************************************
     * Touch Events
     ***********************************************************************************************/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown(event);
                break;

            case MotionEvent.ACTION_MOVE:
                touchMove(event);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touchUp(event);
                break;
        }
        return false;
    }

    /***********************************************************************************************
     * Explored functions
     ***********************************************************************************************/
    public BaseGameSurfaceView setListener(GameListener l) {
        listener = l;
        return this;
    }

    public BaseGameSurfaceView setDuration(long miliseconds) {
        this.miliseconds = miliseconds;
        return this;
    }

    public void start() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                gameThread.start();
            }
        }, 500);
    }

    /***********************************************************************************************
     * Game Thread
     ***********************************************************************************************/
    @Override
    public void run() {
        while (!stop && miliseconds > 0) {
            preDraw();

            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                drawing(canvas);
                holder.unlockCanvasAndPost(canvas);
            }

            try {
                Thread.sleep(TIME_SLOT);
            } catch (Exception e) {
                e.printStackTrace();
            }
            miliseconds -= TIME_SLOT;

            postDraw();
        }

        over();
        if (listener != null) {
            listener.gameOver();
        }
    }

    /***********************************************************************************************
     * Abstract Functions
     ***********************************************************************************************/
    protected long getMiliseconds() {
        return miliseconds;
    }

    /***********************************************************************************************
     * Abstract Functions
     ***********************************************************************************************/
    protected abstract void inits();

    protected abstract void touchDown(MotionEvent event);
    protected abstract void touchMove(MotionEvent event);
    protected abstract void touchUp(MotionEvent event);

    protected abstract void preDraw();
    protected abstract void drawing(Canvas canvas);
    protected abstract void postDraw();
    protected abstract void over();
}
