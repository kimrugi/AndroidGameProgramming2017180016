package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Util;

public class Arrow extends Sprite implements GameObject {
    static private final float height = Metrics.height / 24;
    static private final float width = Metrics.height / 12;

    public float startTime;
    private float endTime;
    private float angle;
    private Circle circle;
    private float headx, heady;
    private float originx, originy;

    public Arrow(float x, float y, float cx, float cy, float angle, Circle circle, float startTime, float endTime) {
        super(x + cx, y +cy, width, height, R.mipmap.arrow);
        this.startTime = startTime;
        this.endTime = endTime;
        this.angle = angle;
        this.circle = circle;
        this.originx = this.x;
        this.originy = this.y;
        float radius = circle.getRadius();
        float distance = radius - width/2;
        headx = (float) (cx + Math.cos(Math.toRadians(angle)) * distance);
        heady = (float) (cy + Math.sin(Math.toRadians(angle)) * distance);
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        if(game.totalTime >= endTime){
            game.remove(this);
            return;
        }
        if(collisionCheck()){
            game.remove(this);
            game.score.add(10);
            return;
        }
        float factor = (game.totalTime - startTime) / (endTime - startTime);
        factor *= factor * factor;
        x = Util.lerp(headx, originx, factor);
        y = Util.lerp(heady, originy, factor);
    }

    private boolean collisionCheck() {
        MainGame game = MainGame.getInstance();
        if(game.totalTime < endTime - Metrics.floatValue(R.dimen.barrier_time)) return false;
        float barrierAngle = circle.getBarrierAngle() - 180;
        if(barrierAngle < 0) barrierAngle += 360;
        if(Math.abs(barrierAngle - angle) < 45f )
            return true;
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        setDstRect(width, height);
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }
}
