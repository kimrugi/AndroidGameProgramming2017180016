package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;

public class ArrowModeGenerator extends ObjectGenerator{
    BitModeGenerator bitMode;
    HashMap<Integer, CircleInfo> touchedCircles = new HashMap<Integer, CircleInfo>();
    ArrowModeCircle leftCircle;
    ArrowModeCircle rightCircle;

    ArrayList<ArrowInfo> leftArrows;
    ArrayList<ArrowInfo> rightArrows;

    ArrowModeGenerator(String jsonFileName, BitModeGenerator bitMode) {
        super(jsonFileName);
        this.bitMode = bitMode;
        leftArrows = new ArrayList<>();
        rightArrows = new ArrayList<>();

        for(ArrowInfo info : bitMode.leftCircle.arrowInfos){
            leftArrows.add(new ArrowInfo(info.angle, info.startTime, info.endTime));
        }
        for(ArrowInfo info : bitMode.rightCircle.arrowInfos){
            rightArrows.add(new ArrowInfo(info.angle, info.startTime, info.endTime));
        }
        /*leftArrows = bitMode.leftCircle.arrowInfos;
        rightArrows = bitMode.rightCircle.arrowInfos;*/
    }

    @Override
    public void update() {
        super.update();
        bitMode.update();

    }

    public Circle addCircle(int x, int y, int id) {
        CircleInfo circleInfo = new CircleInfo();
        float startTime = MainGame.getInstance().totalTime;
        circleInfo.setStartTime(startTime).
                setX(Metrics.getIntWidth(x)).
                setY(Metrics.getIntHeight(y)).
                // 임시로 큰 값
                setEndTime(startTime + 1000000).
                setArrowInfos(new ArrayList<>());
        touchedCircles.put(id, circleInfo);
        ArrowModeCircle circle = circleInfo.buildToArrowMode();
        MainGame.getInstance().add(MainGame.Layer.circle, circle);
        if(x < Metrics.width / 2){
            leftCircle = circle;
            leftCircle.setModeArrows(leftArrows);
        }else{
            rightCircle = circle;
            rightCircle.setModeArrows(rightArrows);
        }
        return circle;
    }

    public void finishCircle(Circle circle, int id) {
        ((ArrowModeCircle)circle).release();
        MainGame.getInstance().remove(circle);
        CircleInfo circleInfo = touchedCircles.get(id);
        if(circleInfo == null) return;
        touchedCircles.remove(id);
        // arrow 정보가 없으면 기록하지 않는다.
        if(circleInfo.arrowInfos.isEmpty()) return;
        // arrowInfo를 startTime 오름차순 정렬
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            circleInfo.arrowInfos.sort(new Comparator<ArrowInfo>() {
                @Override
                public int compare(ArrowInfo o1, ArrowInfo o2) {
                    if (o1.startTime > o2.startTime) {
                        return 1;
                    } else if (o1.startTime < o2.startTime) {
                        return -1;
                    }
                    return 0;
                }
            });
        }
        circleInfo.setStartTime(circleInfo.arrowInfos.get(0).startTime - 4);
        circleInfo.setEndTime(MainGame.getInstance().totalTime + 1);

        circleInfos.add(circleInfo);
        }

    @Override
    public void onTimeChanged(float time) {
        super.onTimeChanged(time);
        bitMode.onTimeChanged(time);
    }

    @Override
    public void save() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            circleInfos.sort(new Comparator<CircleInfo>() {
                @Override
                public int compare(CircleInfo a, CircleInfo b) {
                    if(a.startTime > b.startTime) return 1;
                    if(a.startTime < b.startTime) return -1;
                    return 0;
                }
            });
        }
        super.save("Result.json");
    }
}
