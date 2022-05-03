package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Recyclable;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private final Paint collisionPaint = new Paint();
    Score score;

    private CollisionChecker collisionChecker;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime, totalTime;

    private MainGame() {
    }

    private static MainGame singleton;

    private ArrayList<ArrayList<GameObject>> layers;
    public enum Layer{
        background, circle, arrow, barrier, ui, controller, COUNT
    }

    public static void clear() {
        singleton = null;
    }

    public void init() {

        //objects.clear();
        initLayers(Layer.COUNT.ordinal());

        collisionPaint.setColor(Color.RED);
        collisionPaint.setStyle(Paint.Style.STROKE);
        collisionPaint.setStrokeWidth(10);
        collisionChecker = new CollisionChecker();
        add(Layer.controller, collisionChecker);

        ObjectGenerator generator = new ObjectGenerator();
        add(Layer.controller, generator);

        score = new Score();
        score.set(0);
        add(Layer.ui, score);
        //Circle circle = new Circle(500, 500);
        //add(Layer.circle, circle);

        //Arrow arrow = new Arrow(x, y, circle, 200, 200);
        //add(Layer.arrow, arrow);

    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for(int i = 0; i < count; ++i){
            layers.add(new ArrayList<>());
        }

    }

    public void update(long elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
        //frameTime = 0.1f; // 1_000_000_000.0f;
        totalTime += frameTime;
        for(ArrayList<GameObject> objects : layers) {
            for (GameObject gobj : objects) {
                gobj.update();
            }
        }
//        checkCollision();
    }

    /*private void checkCollision() {

    }*/

    public ArrayList<GameObject> objectsAt(Layer layer){
        return layers.get(layer.ordinal());
    }

    public void draw(Canvas canvas) {
        for(ArrayList<GameObject> objects : layers) {
            for (GameObject gobj : objects) {
                gobj.draw(canvas);
            }
        }
    }

    HashMap<Integer, Circle> touchedCircles = new HashMap<Integer, Circle>();

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        int pointerCount = event.getPointerCount();
        if(pointerCount > 3) pointerCount = 3;
        GameObject object;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                object = collisionChecker.checkTouchCollision(x, y);
                if (object == null) return true;
                ((Circle) object).onTouchDown(x, y);
                int key = event.getPointerId(0);
                touchedCircles.put(key, (Circle) object);
                return true;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                for(int i = 0; i < pointerCount; ++i) {
                    object = collisionChecker.checkTouchCollision(x, y);
                    if (object == null) continue;
                    ((Circle) object).onTouchDown(x, y);
                    int key = event.getPointerId(i);
                    touchedCircles.put(key, (Circle) object);
                }return true;
            case MotionEvent.ACTION_MOVE:
                for(int i = 0; i < pointerCount; ++i) {
                    int key = event.getPointerId(i);
                    Circle found = touchedCircles.get(key);
                    if(found == null) return true;
                    Circle circle = touchedCircles.get(key);
                    circle.onMove(x, y);

                }return true;
            case MotionEvent.ACTION_UP:
                int key = event.getPointerId(0);
                Circle found = touchedCircles.get(key);
                if(found == null) return true;
                found.onTouchUp();
                return true;
        }
        return false;
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
    }

    public void remove(GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                for(ArrayList<GameObject> objects : layers) {
                    boolean removed = objects.remove(gameObject);
                    if (!removed) continue;
                    if (gameObject instanceof Recyclable) {
                        RecycleBin.add((Recyclable) gameObject);
                    }
                    break;
                }
            }
        });
    }

    public int objectCount() {
        int count = 0;
        for(ArrayList<GameObject> objects : layers){
            count += objects.size();
        }
        return count;
    }
}
