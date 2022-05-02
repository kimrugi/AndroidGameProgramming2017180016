package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Random;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.BoxCollidable;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Metrics;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.R;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.GameObject;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.GameView;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.Recyclable;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private Paint collisionPaint = new Paint();
    Score score;

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    public float frameTime;

    private MainGame() {
    }

    private static MainGame singleton;

    private static final int BALL_COUNT = 10;
    //private ArrayList<GameObject> objects = new ArrayList<>();
    private ArrayList<ArrayList<GameObject>> layers;
    public enum Layer{
        bg, bullet, enemy, player, ui, controller, COUNT
    }


    private Fighter fighter;

    public static void clear() {
        singleton = null;
    }

    public void init() {

        //objects.clear();
        initLayers(Layer.COUNT.ordinal());

        add(Layer.controller, new EnemyGenerator());
        add(Layer.controller, new CollisionChecker());
        Random random = new Random();

        float fighterY = Metrics.height - Metrics.size(R.dimen.fighter_y_offset);
        fighter = new Fighter(Metrics.width / 2, fighterY);
        add(Layer.player, fighter);

        score = new Score();
        add(Layer.ui, score);

        add(Layer.bg, new HorzScrollBackground(R.mipmap.bg_city, Metrics.size(R.dimen.bg_speed_city)));
        add(Layer.bg, new HorzScrollBackground(R.mipmap.clouds, Metrics.size(R.dimen.bg_speed_cloud)));

        collisionPaint.setColor(Color.RED);
        collisionPaint.setStyle(Paint.Style.STROKE);
        collisionPaint.setStrokeWidth(10);
    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for(int i = 0; i < count; ++i){
            layers.add(new ArrayList<>());
        }

    }

    public void update(int elapsedNanos) {
        frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
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
                if (gobj instanceof BoxCollidable) {
                    RectF rect = ((BoxCollidable) gobj).getBoundingRect();
                    //canvas.drawRect(rect, collisionPaint);
                }
            }
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
