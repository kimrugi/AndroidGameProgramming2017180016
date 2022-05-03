package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

public class ArrowInfo {
    public float stratTime, endTime;
    public float x, y;

    ArrowInfo(float x, float y, float stratTime, float endTime){
        this.x = x;
        this.y = y;
        this.stratTime = stratTime;
        this.endTime = endTime;
    }

    public Arrow build(float x, float y, Circle circle) {
        this.x += x;
        this.y += y;
        return new Arrow(x, y, circle, stratTime, endTime);
    }
}
