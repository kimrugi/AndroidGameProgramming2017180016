package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView  extends View {

    private Bitmap soccerBitmap;
    private Rect srcRect = new Rect();
    private Rect soccerRect = new Rect();
    private Paint paint = new Paint();

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources res = getResources();
        soccerBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
    }

    @Override
    public void onDraw(Canvas canvas){
        int w = soccerBitmap.getWidth();
        int h = soccerBitmap.getHeight();
        srcRect.set(0, 0, w, h);
        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        int radius = 100;

        soccerRect.set(centerX - radius, centerY - radius,
                centerX + radius,centerY + radius);
        canvas.drawBitmap(soccerBitmap, srcRect, soccerRect, paint);
    }

}
