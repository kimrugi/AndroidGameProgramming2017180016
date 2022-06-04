package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import java.util.ArrayList;

public class BitModeGenerator extends ObjectGenerator{
    private CircleInfo leftCircle;
    private CircleInfo rightCircle;
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
        }
    }

    private CircleInfo addCircleInfo(int x, int y) {
        CircleInfo info = new CircleInfo();
        info.setStratTime(0).
                setEndTime(MainGame.getInstance().getDuration())
                .setX(x).setY(y)
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
    }
}
