package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app.ChartMakingActivity;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Recyclable;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private final Paint collisionPaint = new Paint();
    Score score;

    private CollisionChecker collisionChecker;
    private MediaPlayer mediaPlayer;
    private ObjectGenerator objectGenerator;

    public enum Layer{
        background, circle, arrow, barrier, ui, controller, COUNT
    }

    public static MainGame getInstance() {
        if (singleton == null) {
            singleton = new MainGame();
        }
        return singleton;
    }

    //public float frameTime;
    public float totalTime;

    private MainGame() {
    }

    private static MainGame singleton;

    private ArrayList<ArrayList<GameObject>> layers;

    public void startMusic() {
        mediaPlayer.start();
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void changeToArrowMode() {
    }

    public void finishSaveJSON() {

    }

    public void setBps(int bps) {

    }

    public void changeMusicProgress(int percent) {
        ArrayList<Integer> layerList = new ArrayList<Integer>();
        layerList.add(Layer.circle.ordinal());
        layerList.add(Layer.arrow.ordinal());
        layerList.add(Layer.barrier.ordinal());

        for(Integer index : layerList) {
            ArrayList<GameObject> objects = layers.get(index);
            objects.clear();
        }

        int duration = mediaPlayer.getDuration();
        int milTime = (int)((float)duration / 1000 * percent);
        mediaPlayer.seekTo(milTime);

        totalTime = milTime / 1000.f;

        objectGenerator.onTimeChanged(totalTime);
    }

    private int convertToProgress(int time){
        int duration = mediaPlayer.getDuration();
        int percent = (int)(time / ((float)duration / 1000));
        return percent;
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

        objectGenerator = new ObjectGenerator("sample.json");
        add(Layer.controller, objectGenerator);

        score = new Score();
        score.set(0);
        add(Layer.ui, score);

        //play Music
        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), R.raw.lune_8bit);
        mediaPlayer.start();
    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for(int i = 0; i < count; ++i){
            layers.add(new ArrayList<>());
        }
    }

    public void update(long elapsedNanos) {
        //frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
        //frameTime = 0.1f; // 1_000_000_000.0f;
        //totalTime += frameTime;
        totalTime = mediaPlayer.getCurrentPosition() / 1000.f;
        int progressTime = convertToProgress(mediaPlayer.getCurrentPosition());
        ChartMakingActivity.getActivity().setMusicTime(progressTime);

        for(ArrayList<GameObject> objects : layers) {
            for (GameObject gobj : objects) {
                gobj.update();
            }
        }
    }

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
