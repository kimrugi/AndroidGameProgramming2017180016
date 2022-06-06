package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;


import java.util.ArrayList;

public class CircleInfo {
    public float startTime, endTime;
    public float x;
    public float y;
    ArrayList<ArrowInfo> arrowInfos;

    public CircleInfo setStartTime(float startTime) {
        this.startTime = startTime;
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
        return new Circle(x, y, startTime, endTime, arrowInfos);
    }

    public ArrowModeCircle buildToArrowMode(){
        return new ArrowModeCircle(x, y, startTime, endTime, arrowInfos, this);
    }

    public float getStartTime() {
        return this.startTime;
    }

    public float getEndTime(){
        return this.endTime;
    }

    public void addArrow(ArrowInfo arrow){
        arrowInfos.add(arrow);
    }
}
