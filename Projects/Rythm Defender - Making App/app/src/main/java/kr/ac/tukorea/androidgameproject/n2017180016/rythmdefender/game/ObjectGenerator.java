package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;

public class ObjectGenerator implements GameObject {
    private Random random = new Random();
    protected ArrayList<CircleInfo> circleInfos = new ArrayList<>();
    protected float nextCircleTime = 0;
    protected int nextCircleIndex = 0;

    ObjectGenerator(String jsonFileName) {
        String jsonString = loadFile(jsonFileName);
        parseJson(jsonString);
        if(!circleInfos.isEmpty()) {
            nextCircleTime = circleInfos.get(0).getStartTime();
        }
    }

    private String loadFile(String jsonFileName) {
        File file = new File(Environment.getExternalStorageDirectory() +
                "/zRythmDefender/" + jsonFileName);
        String result = new String();
        String str;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while(((str = br.readLine()) != null)){
                result += str + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void update() {
        float totalTime = MainGame.getInstance().totalTime;
        while(totalTime >= nextCircleTime){
            Circle circle = circleInfos.get(nextCircleIndex).build();
            MainGame.getInstance().add(MainGame.Layer.circle, circle);
            nextCircleIndex++;
            if(circleInfos.size() <= nextCircleIndex){
                nextCircleTime = 100000000.f;
                return;
            }
            nextCircleTime = circleInfos.get(nextCircleIndex).getStartTime();
        }
    }

    public void onTimeChanged(float time){
        for(int index = 0; index < circleInfos.size(); ++index){
            CircleInfo info = circleInfos.get(index);
            float start = info.getStartTime();
            if(start > time) {
                nextCircleIndex = index;
                nextCircleTime = start;
                break;
            }
            float end = info.getEndTime();
            if(end < time) continue;
            Circle circle = info.build();
            MainGame.getInstance().add(MainGame.Layer.circle, circle);
            circle.onTimeChanged(time);
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }

    private void parseJson(String jsonString) {
        String json = jsonString;
        if(json.isEmpty()){
            nextCircleTime = 100000000.f;
            return;
        }
        //parse
        try{
            JSONObject object = new JSONObject(json);
            String musicName = object.getString("musicName");

            JSONArray points = object.getJSONArray("points");
            for(int i = 0; i < points.length(); ++i){
                JSONObject point = points.getJSONObject(i);
                float startTime = (float) point.getDouble("startTime");
                float endTime = (float) point.getDouble("endTime");
                int x = point.getInt("x");
                int y = point.getInt("y");

                ArrayList<ArrowInfo> arrowInfos = new ArrayList<>();
                JSONArray arrows = point.getJSONArray("arrow");
                for(int j = 0; j < arrows.length(); ++j){
                    JSONObject arrow = arrows.getJSONObject(j);
                    float astartTime = (float) arrow.getDouble("startTime");
                    float aendTime = (float) arrow.getDouble("endTime");
                    float degree =(float) arrow.getDouble("degree");

                    ArrowInfo arrowInfo = new ArrowInfo(degree, astartTime, aendTime);
                    arrowInfos.add(arrowInfo);
                }
                CircleInfo circleInfo = new CircleInfo();
                circleInfo.setStartTime(startTime).setEndTime(endTime)
                        .setX(x).setY(y)
                        .setArrowInfos(arrowInfos);
                circleInfos.add(circleInfo);
            }
        }catch (JSONException ex){
            ex.printStackTrace();
        }
    }

    private void addRandomCircle() {

        float cx = random.nextFloat() * 600 + 200;
        float cy = random.nextFloat() * 600 + 200 + Circle.radius;
        //float cy = Circle.radius + 1000;
        float totalTime = MainGame.getInstance().totalTime;
        float stime = totalTime;
        float endTime = totalTime + 10;
        float etime = totalTime + endTime;
        ArrayList<ArrowInfo> arrowList = new ArrayList<>();
        for(int i = 0; i < random.nextInt(5)+1; ++i){
//            float astime = stime + random.nextFloat() *
//                    (etime - stime - 1);
            float astime = stime + i;
            float aetime = astime + 2;
            float degree = random.nextFloat() * 360f;
            arrowList.add(new ArrowInfo(degree, astime, aetime));
        }
        Circle circle = new Circle(cx, cy, stime, etime, arrowList);
        MainGame.getInstance().add(MainGame.Layer.circle, circle);
    }

    public void save(String jsonFileName){
        String fileTitle = jsonFileName;
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
                circleObject.put("startTime", circle.startTime);
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
