package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.RectF;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.R;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.BoxCollidable;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Sprite;

public class Enemy extends Sprite implements BoxCollidable {

    protected float dy;
    protected RectF boundingRect = new RectF();

    public Enemy(float x, float y, float speed) {
        //super(x, y, R.dimen.enemy_radius, R.mipmap.f_01_01);
        super(x, -size/2, size, size, R.mipmap.f_01_01);
        dy = speed;
    }

    private static float size, inset;
    public static void setSize(float size) {
        Enemy.size = size;
        Enemy.inset = size / 16;
    }

    @Override
    public void update() {
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
    public RectF getBoundingRect() {
        return boundingRect;
    }
}