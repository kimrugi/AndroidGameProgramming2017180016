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
    private float randomTime, timePassed;

    ObjectGenerator(String jsonFileName) {
        randomTime = 0;
        timePassed = 0;
        parseJson(jsonFileName);
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
                int startTime = point.getInt("startTime");
                int endTime = point.getInt("endTime");
                int x = point.getInt("x");
                int y = point.getInt("y");

                JSONArray arrows = point.getJSONArray("arrow");
                /*for(int j = 0; j < arrows.length(); ++j){
                    JSONObject arrow = arrows.getJSONObject(j);
                    int startTime = arrow.getInt("startTime");
                    int endTime = arrow.getInt("endTime");
                    int x = arrow.getInt("x");
                    int y = arrow.getInt("y");
                }*/
            }
        }catch (JSONException ex){

        }
    }

    @Override
    public void update() {
        timePassed += MainGame.getInstance().frameTime;
        if(timePassed >= randomTime){
            addCircle();
            //randomTime = random.nextFloat() * 5 + 3 + timePassed;
            randomTime = 5f + timePassed;
        }
    }

    private void addCircle() {

        float cx = random.nextFloat() * 600 + 200;
        float cy = random.nextFloat() * 600 + 200 + Circle.radius;
        //float cy = Circle.radius + 1000;

        float stime = timePassed;
        float endTime = timePassed + 10;
        float etime = timePassed + endTime;
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

    @Override
    public void draw(Canvas canvas) {

    }
}
