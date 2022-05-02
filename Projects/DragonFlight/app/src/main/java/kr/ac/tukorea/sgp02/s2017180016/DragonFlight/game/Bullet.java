package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.BoxCollidable;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.GameObject;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.R;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Recyclable;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.RecycleBin;

public class Bullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = Bullet.class.getSimpleName();
    protected float x, y;
    protected final float length;
    protected final float dy;
    protected static Paint paint;
    protected static float laserWidth;

    protected RectF boundingBox = new RectF();
    private float power;

    public static Bullet get(float x, float y, float power) {
        Bullet bullet = (Bullet) RecycleBin.get(Bullet.class);
        if(bullet != null){
            bullet.set(x,y, power);
            return bullet;
        }
        return new Bullet(x, y, power);
    }

    private void set(float x, float y, float power) {
        this.x = x;
        this.y = y;
        this.power = power;

    }

    private Bullet(float x, float y, float power) {
        set(x,y, power);
        this.length = Metrics.size(R.dimen.laser_length);
        this.dy = -Metrics.size(R.dimen.laser_speed);

        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.RED);
            laserWidth = Metrics.size(R.dimen.laser_width);
            paint.setStrokeWidth(laserWidth);
        }
        Log.d(TAG, "bullet created");
    }

    @Override
    public void update() {
        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;

        float hw = laserWidth / 2;
        boundingBox.set(x - hw, y, x+hw, y-length);
        if(y < -length){
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine(x, y, x, y - length, paint);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingBox;
    }

    @Override
    public void finish() {

    }

    public float getPower() {
        return power;
    }
}
