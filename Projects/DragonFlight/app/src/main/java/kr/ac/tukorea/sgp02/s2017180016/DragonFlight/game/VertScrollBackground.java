package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Canvas;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Sprite;

public class VertScrollBackground extends Sprite {
    private final float speed;
    private final int height;

    public VertScrollBackground(int resId, float speed){
        super(Metrics.width / 2, Metrics.height / 2, Metrics.width, Metrics.height, resId);

        height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setDstRect(Metrics.width, height);
        /*float width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        setDstRect(width, Metrics.height);*/

        this.speed = speed;
    }

    @Override
    public void update() {
        this.y += speed * MainGame.getInstance().frameTime;
//        if(y > Metrics.height) y= 0;
//        setDstRect(Metrics.width, height);
    }

    @Override
    public void draw(Canvas canvas) {
//        super.draw(canvas);
        int curr = (int) y % height;
        if(curr > 0) curr -= height;
        while(curr < Metrics.height){
            dstRect.set(0, curr, Metrics.width, curr+height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += height;
        }
    }
}
