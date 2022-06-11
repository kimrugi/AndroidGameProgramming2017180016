package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

public class ArrowModeCircle extends Circle{
    private ArrayList<ArrowModeArrow> modeArrows = new ArrayList<>();
    private CircleInfo circleInfo;
    private int nextArrowIndex = 0;

    public ArrowModeCircle(float x, float y, float startTime, float endTime,
                           ArrayList<ArrowInfo> arrowInfos, CircleInfo circleInfo) {
        super(x, y, startTime, endTime, arrowInfos, circleInfo);
        this.circleInfo = circleInfo;
    }

    @Override
    public void update() {
        //super.update();
        MainGame game = MainGame.getInstance();
        //Arrow removeArrow = null;
        if(nextArrowIndex < modeArrows.size()){
            Arrow arrow = modeArrows.get(nextArrowIndex);
            if(arrow.getStartTime() <= game.totalTime){
                Log.d("TAG", "Arrow Gen index: " + nextArrowIndex + " startTime: " + arrow.startTime +", endTime: " + arrow.endTime);
                MainGame.getInstance().add(MainGame.Layer.arrow, arrow);
                nextArrowIndex++;
            }
        }
    }

    public void setModeArrows(ArrayList<ArrowInfo> arrowInfos) {
        Log.d("TAG", "Circle Generated, arrow count: " + arrowInfos.size());
        for(ArrowInfo info : arrowInfos){
            if(info.endTime < MainGame.getInstance().totalTime) continue;
            modeArrows.add(info.buildToArrowMode(this.x, this.y, this));
            //Log.d("TAG", "Arrow Gen: startTime: " + info.startTime +", endTime: " + info.endTime);
        }
        Log.d("TAG", "Actual arrow count: " + modeArrows.size());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            modeArrows.sort((a, b) -> {
                if(a.startTime > b.startTime) return 1;
                if(a.startTime < b.startTime) return -1;
                return 0;
            });
        }
    }

    @Override
    public void onMove(int x, int y) {
        super.onMove(x, y);
        for(ArrowModeArrow arrow : modeArrows){
            arrow.onMove(angle);
        }
    }

    public void finishArrow(ArrowModeArrow arrow) {
        //modeArrows.remove(arrow);
        circleInfo.addArrow(new ArrowInfo(arrow.angle, arrow.startTime, arrow.endTime));
    }

    public void release() {
        for(ArrowModeArrow arrow : modeArrows){
            MainGame.getInstance().remove(arrow);
        }
    }
}
