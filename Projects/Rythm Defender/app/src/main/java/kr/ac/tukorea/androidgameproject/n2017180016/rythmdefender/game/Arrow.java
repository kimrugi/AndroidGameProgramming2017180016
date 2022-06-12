package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.media.ResourceBusyException;
import android.util.Log;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app.GameActivity;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sound;
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
    private boolean isActivated = false;

    public Arrow(float x, float y, float cx, float cy, float angle, Circle circle, float startTime, float endTime) {
        super(x + cx, y +cy, width, height, R.mipmap.arrow);
        this.startTime = startTime;
        this.endTime = endTime;
        this.angle = angle;
        this.circle = circle;
        this.originx = this.x;
        this.originy = this.y;
        float radius = Circle.radius * 1.1f;
        float distance = radius - width/2;
        headx = (float) (cx + Math.cos(Math.toRadians(angle)) * distance);
        heady = (float) (cy + Math.sin(Math.toRadians(angle)) * distance);
        isActivated = true;
    }

    @Override
    public void update() {
        if(!isActivated) return;
        MainGame game = MainGame.getInstance();
        if(game.totalTime >= endTime){
            if(collisionCheckWithBarrier()){
                game.remove(this);
                isActivated = false;
                game.score.add(10);
                Sound.playEffect(R.raw.drum);
                return;
            }else if(game.totalTime >= endTime + Metrics.floatValue(R.dimen.barrier_time) ){
                game.remove(this);
                isActivated = false;
                return;
            }
        }
        float factor = (game.totalTime - startTime) / (endTime - startTime);
        factor *= factor * factor;
        x = Util.lerp(headx, originx, factor);
        y = Util.lerp(heady, originy, factor);
    }

    private boolean collisionCheckWithBarrier() {
        if(!circle.barrierInvalid()) {
            return false;}
        //if(game.totalTime < endTime - Metrics.floatValue(R.dimen.barrier_time)) return false;
        float barrierAngle = circle.getBarrierAngle() - 180;
        if(barrierAngle < 0) barrierAngle += 360;
        if(Math.abs(barrierAngle - angle) < 45f )
            return true;
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        if(!isActivated) return;
        setDstRect(width, height);
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }
}
