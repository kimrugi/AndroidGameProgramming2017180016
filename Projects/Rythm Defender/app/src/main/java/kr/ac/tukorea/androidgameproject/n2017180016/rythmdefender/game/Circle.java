package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.BoxCollidable;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class Circle extends Sprite implements GameObject, BoxCollidable {
    static private final float radius = Metrics.height / 6;
    private static final String TAG = Circle.class.getSimpleName();
    private Barrier barrier;
    private float angle;

    public Circle(float x, float y) {
        super(x, y, radius, radius, R.mipmap.hitcircle);
    }

    @Override
    public void update() {
        angle += 1;
        if(barrier == null) return;
        barrier.setAngle(angle);
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }

    public void onTouchDown() {
        if(barrier != null) return;
        barrier = new Barrier(x,y);
        MainGame.getInstance().add(MainGame.Layer.barrier, barrier);
    }

    public void onTouchUp() {
        if(barrier == null) {
            Log.d(TAG, "no barrier but touched up");
            return;}
        MainGame.getInstance().remove(barrier);
        barrier = null;
    }
}
