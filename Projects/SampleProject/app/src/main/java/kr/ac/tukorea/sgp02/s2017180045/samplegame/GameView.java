package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView  extends View implements Choreographer.FrameCallback {


    private final String TAG = GameView.class.getSimpleName();
//    private static final int BALL_COUNT = 5;

//    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
//    private Fighter fighter;

    private Paint fpsPaint = new Paint();

    private long previousTimeNanos;
    private int framesPerSecond;

    public static GameView view;
    private boolean initialized;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

//        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.width = w;
        Metrics.height = h;

        if(!initialized) {
            initView();
            initialized = true;
        }
    }

    private void initView() {
        view = this;

        MainGame game = MainGame.getInstance();
        game.init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        long now = currentTimeNanos;
//        long now = System.currentTimeMillis();
        int elapsed = (int)(now - previousTimeNanos);
        if(elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            Log.v(TAG, "Elapsed: " + elapsed + " fps: " + framesPerSecond);
            previousTimeNanos = now;

            MainGame.getInstance().update(elapsed);
            invalidate();
        }
        //recall doFrame
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void onDraw(Canvas canvas){
        MainGame.getInstance().draw(canvas);

        canvas.drawText("FPS: " + framesPerSecond, framesPerSecond * 10, 100, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return MainGame.getInstance().onTouchEvent(event);
    }
}
