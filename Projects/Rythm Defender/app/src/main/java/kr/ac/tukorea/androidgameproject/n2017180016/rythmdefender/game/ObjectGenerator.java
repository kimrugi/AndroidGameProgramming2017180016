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
