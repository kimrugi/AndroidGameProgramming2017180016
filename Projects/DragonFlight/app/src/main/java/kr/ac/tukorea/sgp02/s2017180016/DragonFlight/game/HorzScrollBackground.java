package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Canvas;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Sprite;

public class HorzScrollBackground extends Sprite {
    private final float speed;
    private final int width;

    public HorzScrollBackground(int resId, float speed){
        super(Metrics.width / 2, Metrics.height / 2, Metrics.width, Metrics.height, resId);

        width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        setDstRect(width, Metrics.height);

        this.speed = speed;
    }

    @Override
    public void update() {
        this.x += speed * MainGame.getInstance().frameTime;
//        if(y > Metrics.height) y= 0;
//        setDstRect(Metrics.width, height);
    }

    @Override
    public void draw(Canvas canvas) {
//        super.draw(canvas);
        int curr = (int) x % width;
        if(curr > 0) curr -= width;
        while(curr < Metrics.width){
            dstRect.set(curr, 0, curr + width, Metrics.height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }
}
