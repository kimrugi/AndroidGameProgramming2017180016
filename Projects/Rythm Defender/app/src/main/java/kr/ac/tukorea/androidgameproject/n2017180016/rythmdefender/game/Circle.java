package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.BoxCollidable;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class Circle extends Sprite implements GameObject, BoxCollidable {
    static private final float radius = Metrics.height / 6;
    private Barrier barrier;

    public Circle(float x, float y) {
        super(x, y, radius, radius, R.mipmap.hitcircle);
    }

    @Override
    public void update() {

    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }

    public void onTouchDown() {
        barrier = new Barrier(x,y);
        MainGame.getInstance().add(MainGame.Layer.barrier, barrier);
    }
}
