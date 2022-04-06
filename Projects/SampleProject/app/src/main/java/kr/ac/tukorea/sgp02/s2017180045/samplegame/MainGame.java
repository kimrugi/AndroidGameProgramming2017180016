package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

public class MainGame {
    public static MainGame singleton;

    public static MainGame getInstance() {
        if(singleton == null){
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime;

    private MainGame(){

    }

    private static final int BALL_COUNT = 5;
    private ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private Fighter fighter;

    public void init() {
        Random random = new Random();

        for(int i = 0; i < BALL_COUNT; ++i){
            int dx = random.nextInt(100) + 100;
            int dy = random.nextInt(100) + 100;
            objects.add(new Ball(dx, dy));
        }

        fighter = new Fighter(Metrics.width / 2, Metrics.height/2);
        objects.add(fighter);
    }

    public void update(int elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.f
        for(GameObject object : objects){
            object.update();
        }
        fighter.update();
    }

    public void draw(Canvas canvas) {
        for(GameObject object : objects){
            object.draw(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX();
                int y = (int) event.getY();
                fighter.setTargetPosition(x, y);
                return true;
        }
        return false;
    }
}
