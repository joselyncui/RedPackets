package com.vicky.red.redpackets.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.vicky.red.redpackets.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义surfaceview 不断修改 红包 坐标实现
 *
 * 公司里大神的方法 ^_^
 */
public class GameSurfaceView extends BaseGameSurfaceView {

    protected final int SPRITE_MEAN_NUMBER = 2;
    protected final float GRAVITY = 98 / 2;
    protected final float ANGLE = (float) (Math.PI / 0.25f);

    private static int count = 0;

    /***********************************************************************************************
     * Parameters
     ***********************************************************************************************/
    private float angle = ANGLE;
    private float acceleration = 0;

    /***********************************************************************************************
     * Sprites
     ***********************************************************************************************/
    private List<Sprite> sprites = new ArrayList<>();

    public GameSurfaceView(Context context) {
        this(context, null);
    }

    public GameSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void inits() {
        acceleration = (float) (-GRAVITY * Math.PI / angle);
    }

    @Override
    protected void touchDown(MotionEvent event) {
        testHint(event.getX(), event.getY());
    }

    @Override
    protected void touchMove(MotionEvent event) {
    }

    @Override
    protected void touchUp(MotionEvent event) {
    }

    @Override
    protected void preDraw() {
        recyclerSprites();
        genSprites();
    }

    @Override
    protected void drawing(Canvas canvas) {
        moveSprites(canvas);
    }

    @Override
    protected void postDraw() {
    }

    @Override
    protected void over() {
        recyclerAll();
        Log.e("TAG", "hint count = " + count);
    }

    private void recyclerSprites() {
        List<Sprite> out = new ArrayList<>();
        for (int i = sprites.size() - 1; i >= 0; i--) {
            Sprite sprite = sprites.get(i);
            if (sprite.canRecycler()) {
                out.add(sprite);
            }
        }

        for (Sprite sprite : out) {
            sprites.remove(sprite);
        }
    }

    private void genSprites() {
        if (getMiliseconds() % (TIME_SLOT * 2) != 0) {
            return;
        }
        /***************************************************************************************
         * SPRITE_MEAN_NUMBER ~ 1.5 * SPRITE_MEAN_NUMBER red packets
         ***************************************************************************************/
        int number = (int) (SPRITE_MEAN_NUMBER / 2 + Math.random() * SPRITE_MEAN_NUMBER);
        for (int i = 0; i < number; i++) {
            sprites.add(new Sprite());
        }
    }

    private void moveSprites(Canvas canvas) {
        for (Sprite sprite : sprites) {
            sprite.move(acceleration, (float) (GRAVITY * (0.5f + Math.random())));
            sprite.draw(canvas);
        }
    }

    private void recyclerAll() {
        for (Sprite sprite : sprites) {
            sprite.bitmap.recycle();
        }
    }

    private void testHint(float x, float y) {
        for (Sprite sprite : sprites) {
            if (sprite.testHint(x, y)) {
                sprite.recycler = true;
                count++;
                break;
            }
        }
    }

    /***********************************************************************************************
     * Sprite Object
     ***********************************************************************************************/
    public class Sprite {
        private float x = 0;
        private float y = 0;
        private int width = 0;
        private int height = 0;
        private Bitmap bitmap = null;
        private Paint paint = null;
        private boolean recycler = false;

        public Sprite() {
            this.bitmap = rotate(BitmapFactory.decodeResource(getResources(), R.drawable.red_envelope));
            this.paint = new Paint();

            this.width = bitmap.getWidth();
            this.height = bitmap.getHeight();

            /***************************************************************************************
             * 0.0 ~ 1.0 surface view width
             ***************************************************************************************/
            this.x = (float) (Math.random() * viewWidth);
            this.y = -height;
        }

        public boolean canRecycler() {
            boolean can = false;
            if (y >= viewHeight || x + width < 0 || x > viewWidth || recycler) {
                can = true;
                bitmap.recycle();
            }
            return can;
        }

        public void move(float disX, float disY) {
            x += disX;
            y += disY;
        }

        public void draw(Canvas canvas) {
            if (canvas != null && !bitmap.isRecycled() && x > 0 &&
                    x < viewWidth && y > 0 && y < viewHeight) {
                canvas.drawBitmap(bitmap, x, y, paint);
            }
        }

        private Bitmap rotate(Bitmap bitmap) {
            Bitmap bm = null;
            if (bitmap != null && !bitmap.isRecycled()) {
                Matrix matrix = new Matrix();
                matrix.setRotate(angle);
                bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                if (!bm.equals(bitmap)) {
                    bitmap.recycle();
                }
            }
            return bm;
        }

        private boolean testHint(float pointX, float pointY) {
            boolean hint = false;
            if (pointX >= x && pointX <= x + width && pointY > y && pointY < y + height) {
                hint = true;
            }
            return hint;
        }
    }
}
