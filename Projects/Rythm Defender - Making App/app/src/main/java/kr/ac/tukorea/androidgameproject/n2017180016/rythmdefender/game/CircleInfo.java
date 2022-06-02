package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;


import java.util.ArrayList;

public class CircleInfo {
    public float stratTime, endTime;
    public float x;
    public float y;
    ArrayList<ArrowInfo> arrowInfos;

    public CircleInfo setStratTime(float stratTime) {
        this.stratTime = stratTime;
        return this;
    }

    public CircleInfo setEndTime(float endTime) {
        this.endTime = endTime;
        return this;
    }

    public CircleInfo setX(float x) {
        this.x = x;
        return this;
    }

    public CircleInfo setY(float y) {
        this.y = y;
        return this;
    }

    public CircleInfo setArrowInfos(ArrayList<ArrowInfo> arrowInfos) {
        this.arrowInfos = arrowInfos;
        return this;
    }

    public CircleInfo(){}



    public Circle build() {
        //this.x += x;
        //this.y += y;
        return new Circle(x, y, stratTime, endTime, arrowInfos);
    }

    public float getStartTime() {
        return this.stratTime;
    }

    public float getEndTime(){
        return this.endTime;
    }
}
