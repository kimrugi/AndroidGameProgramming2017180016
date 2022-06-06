package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import java.util.ArrayList;

public class ArrowModeCircle extends Circle{
    public ArrowModeCircle(float x, float y, float startTime, float endTime, ArrayList<ArrowInfo> arrowInfos) {
        super(x, y, startTime, endTime, arrowInfos);
    }

    @Override
    public void update() {
        super.update();

    }

    @Override
    public void setArrows(ArrayList<ArrowInfo> arrowInfos) {
        setArrows(arrowInfos);
    }

    @Override
    public void onMove(int x, int y) {
        super.onMove(x, y);
    }
}
