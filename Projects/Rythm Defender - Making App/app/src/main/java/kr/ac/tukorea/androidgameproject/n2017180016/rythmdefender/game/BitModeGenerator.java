package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;

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
