package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import java.util.ArrayList;

public class ArrowModeCircle extends Circle{
    private ArrayList<ArrowModeArrow> arrows = new ArrayList<>();
    private CircleInfo info;
    private int nextArrowIndex = 0;

    public ArrowModeCircle(float x, float y, float startTime, float endTime, ArrayList<ArrowInfo> arrowInfos, CircleInfo circleInfo) {
        super(x, y, startTime, endTime, arrowInfos);
        this.info = circleInfo;
    }

    @Override
    public void update() {
        super.update();
        MainGame game = MainGame.getInstance();
        //Arrow removeArrow = null;
        if(nextArrowIndex < arrows.size()){
            Arrow arrow = arrows.get(nextArrowIndex);
            if(arrow.getStartTime() <= game.totalTime){
                MainGame.getInstance().add(MainGame.Layer.arrow, arrow);
                nextArrowIndex++;
            }
        }
    }

    @Override
    public void setArrows(ArrayList<ArrowInfo> arrowInfos) {
        for(ArrowInfo info : arrowInfos){
            if(info.startTime < MainGame.getInstance().totalTime) continue;
            arrows.add(info.buildToArrowMode(this.x, this.y, this));
        }
    }

    @Override
    public void onMove(int x, int y) {
        super.onMove(x, y);
        for(ArrowModeArrow arrow : arrows){
            arrow.onMove(angle);
        }
    }

    public void finishArrow(ArrowModeArrow arrow) {
        arrows.remove(arrow);
        info.addArrow(new ArrowInfo(arrow.angle, arrow.startTime, arrow.endTime));
    }

    public void release() {
        for(ArrowModeArrow arrow : arrows){
            MainGame.getInstance().remove(arrow);
        }
    }
}
