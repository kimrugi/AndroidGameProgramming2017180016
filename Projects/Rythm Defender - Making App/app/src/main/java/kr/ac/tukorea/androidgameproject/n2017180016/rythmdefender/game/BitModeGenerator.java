package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import java.util.ArrayList;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;

public class BitModeGenerator extends ObjectGenerator{
    public CircleInfo leftCircle;
    public CircleInfo rightCircle;
    private float arrowAngle = 0;
    private float bps;
    public enum Position {
        left, right
    }

    BitModeGenerator(String jsonFileName) {
        super(jsonFileName);
        if(circleInfos.isEmpty()){
            leftCircle = addCircleInfo(250, 500);
            rightCircle = addCircleInfo(750, 500);
        }else{
            leftCircle = circleInfos.get(Position.left.ordinal());
            rightCircle = circleInfos.get(Position.right.ordinal());
        }
    }

    private CircleInfo addCircleInfo(int x, int y) {
        CircleInfo info = new CircleInfo();
        info.setStartTime(0).
                setEndTime(MainGame.getInstance().getDuration())
                .setX(Metrics.getWidth(x))
                .setY(Metrics.getHeight(y))
                .setArrowInfos(new ArrayList<>());
        circleInfos.add(info);
        return info;
    }

    @Override
    public void update() {
        super.update();
    }

    public void setBps(float bps){
        this.bps = bps;
    }

    public void addArrow(Position pos){
        switch (pos){
            case left:{
                addArrow(leftCircle);
                return;
            }
            case right:{
                addArrow(rightCircle);
                return;
            }
        }
    }
    private void addArrow(CircleInfo circle){
        float totalTime = MainGame.getInstance().totalTime;

        float endTime = totalTime;
        float startTime = endTime - 2.0f;

        ArrowInfo arrowInfo = new ArrowInfo(arrowAngle, startTime, endTime);
        circle.addArrow(arrowInfo);
        arrowAngle += 36.0f;
    }

}
