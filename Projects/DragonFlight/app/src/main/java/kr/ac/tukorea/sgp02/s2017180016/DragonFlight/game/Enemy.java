package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.R;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.AnimSprite;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.BitmapPool;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.BoxCollidable;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Recyclable;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.RecycleBin;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Sprite;

public class Enemy extends AnimSprite implements BoxCollidable, Recyclable {


    private static final String TAG = Enemy.class.getSimpleName();

    private int level;
    private float life;
    private float maxLife;
    protected float dy;
    protected RectF boundingRect = new RectF();
    private Gauge gauge;
    protected static int[] BITMAP_IDS = {
            R.mipmap.enemy_01, R.mipmap.enemy_02,R.mipmap.enemy_03,R.mipmap.enemy_04,
            R.mipmap.enemy_05,R.mipmap.enemy_06,R.mipmap.enemy_07,R.mipmap.enemy_08,
            R.mipmap.enemy_09,R.mipmap.enemy_10,R.mipmap.enemy_11,R.mipmap.enemy_12,
            R.mipmap.enemy_13,R.mipmap.enemy_14,R.mipmap.enemy_15,R.mipmap.enemy_16,
            R.mipmap.enemy_17,R.mipmap.enemy_18,R.mipmap.enemy_19,R.mipmap.enemy_20,
    };
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = BITMAP_IDS.length;

    public static Enemy get(int level, float x, float speed) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if(enemy != null){
            enemy.set(level, x, speed);
            return enemy;
        }
        return new Enemy(level, x, speed);
    }

    private void set(int level, float x, float speed) {
        bitmap = BitmapPool.get(BITMAP_IDS[level-1]);
        this.x = x;
        this.y =-size/2;
        dy = speed;
        this.level = level;
        life = level * 10;
        maxLife = life;
        gauge.setValue(1.0f);
    }

    private Enemy(int level, float x, float speed) {
        //super(x, y, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x, -size/2, size, size, BITMAP_IDS[level - 1], 6, 0);
        this.level = level;
        maxLife = life = level * 10;
        dy = speed;
        gauge = new Gauge(Metrics.size(R.dimen.enemy_gauge_width_fg), R.color.enemy_gauge_fg,
                Metrics.size(R.dimen.enemy_gauge_width_bg), R.color.enemy_gauge_bg,
                size);
        gauge.setValue(1.0f);
        Log.d(TAG, "Enemy Created");
    }

    private static float size, inset;
    public static void setSize(float size) {
        Enemy.size = size;
        Enemy.inset = size / 16;
    }

    @Override
    public void update() {
        super.update();

        float frameTime = MainGame.getInstance().frameTime;
        y += dy * frameTime;
        setDstRectWithRadius();
        boundingRect.set(dstRect);
        boundingRect.inset(inset, inset);
        if (dstRect.top > Metrics.height) {
            MainGame.getInstance().remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        gauge.draw(canvas, x, y + size / 2);
    }

    @Override
    public RectF getBoundingRect() {
        return boundingRect;
    }

    @Override
    public void finish() {

    }

    public int getScore() {
        return level * 10;
    }

    public Boolean decreaseLife(float power) {
        life -= power;
        if(life <= 0) return true;
        gauge.setValue(life / maxLife);
        return false;
    }
}