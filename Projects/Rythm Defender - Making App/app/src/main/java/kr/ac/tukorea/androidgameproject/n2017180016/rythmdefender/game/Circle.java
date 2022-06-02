package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.BoxCollidable;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class Circle extends Sprite implements GameObject, BoxCollidable {
    public static final float radius = Metrics.height / 6;
    private static final String TAG = Circle.class.getSimpleName();
    private Barrier barrier;
    private float angle;
    private int touchx;
    private int touchy;
    private float startTime, endTime;

    private ArrayList<Arrow> arrows = new ArrayList<>();
    private float nextArrowTime = 0;
    private int nextArrowIndex = 0;

    public Circle(float x, float y, float startTime, float endTime, ArrayList<ArrowInfo> arrowInfos) {
        super(Metrics.getWidth(x), Metrics.getHeight(y), radius, radius, R.mipmap.hitcircle);
        this.startTime = startTime;
        this.endTime = endTime;
        for(ArrowInfo info : arrowInfos){
            arrows.add(info.build(this.x, this.y, this));
        }
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        if(game.totalTime >= endTime){
            game.remove(this);
            game.remove(barrier);
            return;
        }
        //Arrow removeArrow = null;
        while(game.totalTime >= nextArrowTime){
            Arrow arrow = arrows.get(nextArrowIndex);
            MainGame.getInstance().add(MainGame.Layer.arrow, arrow);
            nextArrowIndex++;
            if(arrows.size() <= nextArrowIndex){
                nextArrowTime = 100000000.f;
                return;
            }

            nextArrowTime = arrows.get(nextArrowIndex).getStartTime();
        }
        //if(removeArrow != null) arrows.remove(removeArrow);
        //angle += 1;
        //if(barrier == null) return;
    }

    @Override
    public RectF getBoundingRect() {
        return dstRect;
    }

    public void onTouchDown(int x, int y) {
        if(barrier != null) return;
        this.touchx = x;
        this.touchy = y;
        barrier = new Barrier(this.x, this.y);
        MainGame.getInstance().add(MainGame.Layer.barrier, barrier);
    }

    public void onTouchUp() {
        if(barrier == null) {
            Log.d(TAG, "no barrier but touched up");
            return;}
        MainGame.getInstance().remove(barrier);
        barrier = null;
    }

    public void onMove(int x, int y) {
        this.angle = (float) Math.toDegrees(Math.atan2(touchy - y, touchx - x));
        if(barrier == null) return;
        barrier.setAngle(angle - 90);
    }

    public float getRadius() {
        return radius;
    }

    public float getBarrierAngle() {
        return angle;
    }

    public void onTimeChanged(float time) {
        for (int index = 0; index < arrows.size(); ++index) {
            Arrow arrow = arrows.get(index);
            float start = arrow.getStartTime();
            if (start > time) {
                nextArrowIndex = index;
                nextArrowTime = start;
                break;
            }
            float end = arrow.getEndTime();
            if (end < time) continue;

            MainGame.getInstance().add(MainGame.Layer.arrow, arrow);
            arrow.onTimeChanged(time);
        }
    }
}
