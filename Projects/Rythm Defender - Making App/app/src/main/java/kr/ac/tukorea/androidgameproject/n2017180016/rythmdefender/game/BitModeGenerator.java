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

public class BitModeGenerator extends ObjectGenerator{
    private static final String TAG = BitModeGenerator.class.getSimpleName();
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
        arrowAngle += 36.0f;
    }

    public void saveBits(String jsonFileName){
        String fileTitle = "Bit.json";
        String dirString = Environment.getExternalStorageDirectory() + "/zRythmDefender/";
        JSONObject object = getJSON();
        saveJsonFile(fileTitle, dirString, object);
    }

    private JSONObject getJSON() {
        try{
            JSONObject object = new JSONObject();
            object.put("musicName", "null");
            String musicName = object.getString("musicName");

            JSONArray points = new JSONArray();
            for(CircleInfo circle : circleInfos){
                JSONObject circleObject = new JSONObject();
                circleObject.put("startTime", circle.stratTime);
                circleObject.put("endTime", circle.endTime);
                circleObject.put("x", circle.x);
                circleObject.put("y", circle.y);
                JSONArray arrows = new JSONArray();
                for(ArrowInfo arrow : circle.arrowInfos){
                    JSONObject arrowObject = new JSONObject();
                    arrowObject.put("startTime", arrow.stratTime);
                    arrowObject.put("endTime", arrow.endTime);
                    arrowObject.put("degree", arrow.angle);
                    arrows.put(arrowObject);
                }
                circleObject.put("arrow", arrows);
                points.put(circleObject);
            }
            object.put("points", points);
            Log.v(TAG, object.toString());
            return object;
        }catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private void saveJsonFile(String fileTitle, String dirString, JSONObject object) {
        File dirFile = new File(dirString);
        File file = new File(dirString, fileTitle);
        Log.v("Save", file.toString());
        try {
             if (!dirFile.exists()){
                 dirFile.mkdir();
             }

            //파일이 존재하지 않다면 생성
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            writer.write(object.toString());

            writer.close();

        } catch (IOException e) {
            Log.i("저장오류",e.getMessage());
        }
    }
}
