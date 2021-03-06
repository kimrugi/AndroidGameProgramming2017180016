package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.R;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Sprite;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.BitmapPool;

public class Fighter extends Sprite {

    private static final String TAG = Fighter.class.getSimpleName();
    private Bitmap targetBitmap;
    private RectF targetRect = new RectF();

    private float fireRatePerSecond;

    private float dx;
    private float tx;
    private float elapsedTimeForFire = 0.0f;

    public Fighter(float x, float y) {
        super(x, y, R.dimen.fighter_radius, R.mipmap.plane_240);
        setTargetPosition(x, y);

        targetBitmap = BitmapPool.get(R.mipmap.target);
        fireRatePerSecond = Metrics.floatValue(R.dimen.fighter_fire_rate);
    }

    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        elapsedTimeForFire += frameTime;
        float secondPerFire = 1/fireRatePerSecond;
        if(elapsedTimeForFire > secondPerFire){
            fire();
            elapsedTimeForFire -= secondPerFire;
        }
        if (dx == 0)
            return;


        float dx = this.dx * frameTime;
        if ((dx > 0 && x + dx > tx) || (dx < 0 && x + dx < tx)) {
            dx = tx - x;
            x = tx;
            this.dx = 0;
        } else {
            x += dx;
        }
        dstRect.offset(dx, 0);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, null, dstRect, null);

        if (dx != 0) {
            canvas.drawBitmap(targetBitmap, null, targetRect, null);
        }
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        targetRect.set(tx - radius/2, y - radius/2,
                tx + radius/2, y + radius/2);

        float speed = Metrics.size(R.dimen.fighter_speed);
        dx = Metrics.size(R.dimen.fighter_speed);
        if(tx < x) {
            dx = -dx;
        }
    }

    public void fire() {
        float score = MainGame.getInstance().score.get();
        if(score > 10000) score = 10000;
        float power = 10 + score / 100;
        Bullet bullet = Bullet.get(x, y, power);
        MainGame.getInstance().add(MainGame.Layer.bullet, bullet);
    }
}
