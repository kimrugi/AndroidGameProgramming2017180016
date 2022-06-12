package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game.MainGame;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();

    private long previousTimeNanos;
    private int framesPerSecond;
    private Paint fpsPaint = new Paint();

    public static GameView view;
    private boolean initialized;
    private boolean running;

    protected MainGame game;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Metrics.setSize(w, h);
        
        if (!initialized) {
            initView();
            initialized = true;
            running = true;
        }
    }

    protected void initView() {
        view = this;

        game = MainGame.getInstance();
        game.init();

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        if(!running) {
            Log.d(TAG, "Running is false on doFrame()");
            return;
        }

        long now = currentTimeNanos;
        int elapsed = (int) (now - previousTimeNanos);
        if (elapsed != 0) {
            framesPerSecond = 1_000_000_000 / elapsed;
            //Log.v(TAG, "Elapsed: " + elapsed + " FPS: " + framesPerSecond);
            previousTimeNanos = now;
            game.update(elapsed);
            invalidate();
        }

        Choreographer.getInstance().postFrameCallback(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        game.draw(canvas);
        //canvas.drawText("FPS: " + framesPerSecond, framesPerSecond * 10, 100, fpsPaint);
//        Log.d(TAG, "onDraw()");
        //canvas.drawText(""+game.objectCount(), 10, 100, fpsPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return game.onTouchEvent(event);
    }

    public void pauseGame() {
        running = false;
    }

    public void resumeGame() {
        if(initialized && !running) {
            running = true;
            Choreographer.getInstance().postFrameCallback(this);
        }
    }
}
