package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;

public class ArrowModeGenerator extends ObjectGenerator{
    BitModeGenerator bitMode;
    HashMap<Integer, CircleInfo> touchedCircles = new HashMap<Integer, CircleInfo>();
    Circle leftCircle;
    Circle rightCircle;

    ArrayList<ArrowInfo> leftArrows;
    ArrayList<ArrowInfo> rightArrows;

    ArrowModeGenerator(String jsonFileName, BitModeGenerator bitMode) {
        super(jsonFileName);
        this.bitMode = bitMode;
        leftArrows = bitMode.leftCircle.arrowInfos;
        rightArrows = bitMode.rightCircle.arrowInfos;
    }

    @Override
    public void update() {
        super.update();
        bitMode.update();

    }

    public Circle addCircle(int x, int y, int id) {
        CircleInfo circleInfo = new CircleInfo();
        float startTime = MainGame.getInstance().totalTime;
        circleInfo.setStratTime(startTime).
                setX(Metrics.getIntWidth(x)).
                setY(Metrics.getIntHeight(y)).
                // 임시로 큰 값
                setEndTime(startTime + 1000000).
                setArrowInfos(new ArrayList<>());
        touchedCircles.put(id, circleInfo);
        Circle circle = circleInfo.build();
        MainGame.getInstance().add(MainGame.Layer.circle, circle);
        if(x < Metrics.width){
            leftCircle = circle;
            leftCircle.setArrows(leftArrows);
        }else{
            rightCircle = circle;
            rightCircle.setArrows(rightArrows);
        }
        return circle;
    }

    public void finishCircle(Circle circle, int id) {
        MainGame.getInstance().remove(circle);
        CircleInfo circleInfo = touchedCircles.get(id);
        if(circleInfo == null) return;

        circleInfo.setEndTime(MainGame.getInstance().totalTime);
        // arrow 정보가 없으면 기록하지 않는다.
        if(circleInfo.arrowInfos.isEmpty()) return;

        circleInfos.add(circleInfo);
    }
}
