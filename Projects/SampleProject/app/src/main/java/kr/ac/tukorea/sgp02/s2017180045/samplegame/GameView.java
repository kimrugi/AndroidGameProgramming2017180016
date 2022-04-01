package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView  extends View implements Choreographer.FrameCallback {

    private final String TAG = GameView.class.getSimpleName();

    private Bitmap soccerBitmap;
    private Rect soccerSrcRect = new Rect();
    private Rect soccer1DstRect = new Rect();
    private int ball1Dx, ball1Dy;
    private Rect soccer2DstRect = new Rect();
    private int ball2Dx, ball2Dy;

    private Paint paint = new Paint();
    private Paint fpsPaint = new Paint();


    private long previousTimeNanos;
    private int framesPerSecond;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initView();

    }

    private void initView() {
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);

        int w = soccerBitmap.getWidth();
        int h = soccerBitmap.getHeight();
        soccerSrcRect.set(0, 0, w, h);
        soccer1DstRect.set(0, 0, 100, 100);
        soccer2DstRect.set(400, 300, 500, 400);
        ball1Dy = ball1Dx = 5;
        ball2Dx = 7;
        ball2Dy = 5;

        fpsPaint.setColor(Color.BLUE);
        fpsPaint.setTextSize(100);

        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long currentTimeNanos) {
        long now = currentTimeNanos;
//        long now = System.currentTimeMillis();
        int elapsed = (int)(now - previousTimeNanos);
        framesPerSecond = 1_000_000_000 / elapsed;
        Log.v(TAG, "Elapsed: "+ elapsed + " fps: " + framesPerSecond);
        previousTimeNanos = now;

        update();
        invalidate();
        //recall doFrame
        Choreographer.getInstance().postFrameCallback(this);
    }

    private void update() {
        soccer1DstRect.offset(ball1Dx, ball1Dy);
        if(ball1Dx > 0) {
            if (soccer1DstRect.right > getWidth()) {
                ball1Dx = -ball1Dx;
            }
        }else{
            if(soccer1DstRect.left < 0){
                ball1Dx = -ball1Dx;
            }
        }
        if(ball1Dy > 0){
            if(soccer1DstRect.bottom > getHeight()){
                ball1Dy = -ball1Dy;
            }
        }else{
            if(soccer1DstRect.top < 0){
                ball1Dy = -ball1Dy;
            }
        }
        soccer2DstRect.offset(ball2Dx, ball2Dy);
        if(ball2Dx > 0) {
            if (soccer2DstRect.right > getWidth()) {
                ball2Dx = -ball2Dx;
            }
        }else{
            if(soccer2DstRect.left < 0){
                ball2Dx = -ball2Dx;
            }
        }
        if(ball2Dy > 0){
            if(soccer2DstRect.bottom > getHeight()){
                ball2Dy = -ball2Dy;
            }
        }else{
            if(soccer2DstRect.top < 0){
                ball2Dy = -ball2Dy;
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        drawSoccer(canvas);
        canvas.drawText("FPS: " + framesPerSecond, framesPerSecond * 10, 100, fpsPaint);
        //Log.d(tag, "onDraw");


    }

    private void drawSoccer(Canvas canvas) {

        canvas.drawBitmap(soccerBitmap, soccerSrcRect, soccer1DstRect, paint);
        canvas.drawBitmap(soccerBitmap, soccerSrcRect, soccer2DstRect, paint);
    }


}
