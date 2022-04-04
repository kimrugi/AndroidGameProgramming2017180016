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

    private static final int BALL_COUNT = 5;
    private final String TAG = GameView.class.getSimpleName();

//    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private Fighter fighter;

    private Paint fpsPaint = new Paint();

    private long previousTimeNanos;
    private int framesPerSecond;

    public static GameView view;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();

    }

    private void initView() {
        view = this;
        Random random = new Random();

        for(int i = 0; i < BALL_COUNT; ++i){
            int dx = random.nextInt(10) + 5;
            int dy = random.nextInt(10) + 5;
            objects.add(new Ball(dx, dy));
        }

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);

        fighter = new Fighter();
        objects.add(fighter);

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

            update();
            invalidate();
        }
        //recall doFrame
        Choreographer.getInstance().postFrameCallback(this);
    }


    private void update() {
        for(GameObject object : objects){
            object.update();
        }
        //fighter.update();
    }

    @Override
    public void onDraw(Canvas canvas){
        drawSoccer(canvas);
        canvas.drawText("FPS: " + framesPerSecond, framesPerSecond * 10, 100, fpsPaint);
        //Log.d(tag, "onDraw");
        //fighter.draw(canvas);
    }

    private void drawSoccer(Canvas canvas) {
        for(GameObject object : objects){
            object.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
