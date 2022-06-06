package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;

public class ArrowInfo {
    static private final float distance = Metrics.size(R.dimen.arrow_distance);
    public float stratTime, endTime;
    public float x, y;
    public float angle;

    ArrowInfo(float degree, float stratTime, float endTime){
        this.angle = degree;
        float radians = (float) Math.toRadians(degree);
        this.x = (float) (Math.cos(radians) * distance);
        this.y =(float) (Math.sin(radians) * distance);
        //this.angle = (float) Math.toDegrees(Math.atan2(x, y)) + 180f;
        this.stratTime = stratTime;
        this.endTime = endTime;
    }

    public Arrow build(float x, float y, Circle circle) {
        //this.x += x;
        //this.y += y;
        return new Arrow(this.x, this.y, x, y, angle, circle, stratTime, endTime);
    }

    public ArrowModeArrow buildToArrowMode(float x, float y, Circle circle){
        return new ArrowModeArrow(this.x, this.y, x, y, angle, circle, stratTime, endTime);
    }
}
