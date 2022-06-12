package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class Barrier extends Sprite implements GameObject {
    private static final float radius = Circle.radius * 1.3f;
    private float angle;
    private Rect srcRect = new Rect();

    public Barrier(float x, float y) {
        super(x, y, radius, radius, R.mipmap.barrier);
        angle = 0;
        float srcSize = bitmap.getWidth();
        srcRect.set(0, 0, (int)srcSize / 2, (int)srcSize / 2);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    @Override
    public void draw(Canvas canvas) {
        //canvas.drawArc(dstRect, angle - 45f, 45f, false, null);
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }

    public static float getRadius(){
        return radius;
    }
}
