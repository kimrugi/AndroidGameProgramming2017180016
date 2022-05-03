package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;

public class ObjectGenerator implements GameObject {
    private Random random = new Random();
    private float randomTime, timePassed;

    ObjectGenerator(){
        randomTime = 0;
        timePassed = 0;
    }
    @Override
    public void update() {
        timePassed += MainGame.getInstance().frameTime;
        if(timePassed >= randomTime){
            addCircle();
            randomTime = random.nextFloat() * 5 + 3 + timePassed;
        }
    }

    private void addCircle() {

        float cx = random.nextFloat() * 900 + 100;
        float cy = random.nextFloat() * 900 + 100;

        float stime = timePassed;
        float endTime = timePassed + random.nextFloat() * 10 + 5;
        float etime = timePassed + endTime;
        ArrayList<ArrowInfo> arrowList = new ArrayList<>();
        for(int i = 0; i < random.nextInt(5)+1; ++i){
            float ax = random.nextFloat();
            float ay = random.nextFloat();
            float length = ax +ay;
            ax = ax / length;
            ay = ay / length;
            float astime = stime + random.nextFloat() *
                    (etime - stime - 1);
            float aetime = astime + 1;
            arrowList.add(new ArrowInfo(ax, ay, astime, aetime));
        }
        Circle circle = new Circle(cx, cy, stime, etime, arrowList);
        MainGame.getInstance().add(MainGame.Layer.circle, circle);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
