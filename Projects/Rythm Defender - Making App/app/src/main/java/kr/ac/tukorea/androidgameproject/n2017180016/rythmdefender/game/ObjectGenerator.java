package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;

public class ObjectGenerator implements GameObject {
    private Random random = new Random();
    protected ArrayList<CircleInfo> circleInfos = new ArrayList<>();
    protected float nextCircleTime = 0;
    protected int nextCircleIndex = 0;

    ObjectGenerator(String jsonFileName) {
        parseJson(jsonFileName);
        nextCircleTime = circleInfos.get(0).getStartTime();
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

    private void parseJson(String jsonFileName) {
        String json;
        //load
        try {
            InputStream is = GameView.view.getContext().getAssets().open(jsonFileName);
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
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
                circleInfo.setStratTime(startTime).setEndTime(endTime)
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
}
