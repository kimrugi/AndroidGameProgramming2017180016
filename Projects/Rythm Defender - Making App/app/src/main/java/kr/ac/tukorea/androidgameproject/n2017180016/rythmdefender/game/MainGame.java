package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app.ChartMakingActivity;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Recyclable;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.RecycleBin;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private final Paint collisionPaint = new Paint();
    Score score;

    private CollisionChecker collisionChecker;
    private MediaPlayer mediaPlayer;

    private ObjectGenerator generator;
    private ObjectGenerator objectGenerator;
    private BitModeGenerator bitModeGenerator;
    private ArrowModeGenerator arrowModeGenerator;

    protected String jsonFileName;

    public enum EditMode{
        bit, arrow, play, COUNT
    }
    private EditMode editMode = EditMode.arrow;

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

    public void changeMode(EditMode mode) {
        this.editMode = mode;
        init();
    }

    public void finishSaveJSON() {
        generator.save("Result.json");
        changeMode(EditMode.play);
    }

    public void setBps(int bps) {
        if(bitModeGenerator != null) {
            bitModeGenerator.setBps(bps);
        }
    }

    public float getDuration() {
        return (float)mediaPlayer.getDuration() / 1000.f;
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
        generator.onTimeChanged(totalTime);
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
        initLayers(Layer.COUNT.ordinal());

        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), R.raw.lune_8bit);

        collisionPaint.setColor(Color.RED);
        collisionPaint.setStyle(Paint.Style.STROKE);
        collisionPaint.setStrokeWidth(10);
        collisionChecker = new CollisionChecker();
        add(Layer.controller, collisionChecker);
        switch(editMode){
            case bit:
                bitModeGenerator = new BitModeGenerator("Bit.json");
                generator = bitModeGenerator;
                break;
            case play:
                objectGenerator = new ObjectGenerator("Result.json");
                generator = objectGenerator;
                break;
            case arrow:
                if(bitModeGenerator == null){
                    bitModeGenerator = new BitModeGenerator("Bit.json");
                }
                arrowModeGenerator = new ArrowModeGenerator("Arrows.json", bitModeGenerator);
                generator = arrowModeGenerator;
                break;
        }
        add(Layer.controller, generator);

        score = new Score();
        score.set(0);
        add(Layer.ui, score);

        //play Music

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
        switch(editMode){
            case bit:{
                return bitTouchEvent(event);
            }
            case play: {
                return playTouchEvent(event);
            }
            case arrow:{
                return arrowTouchEvent(event);
            }
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

    public void setEditMode(EditMode editMode){
        this.editMode = editMode;
    }

    private boolean playTouchEvent(MotionEvent event){
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        int x = (int) event.getX(index);
        int y = (int) event.getY(index);
        GameObject object;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                object = collisionChecker.checkTouchCollision(x, y);
                if (object == null) break;

                ((Circle) object).onTouchDown(x, y);
                touchedCircles.put(id, (Circle) object);
                return true;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:{
                Circle found = touchedCircles.get(id);
                if (found == null) break;
                found.onTouchUp();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int count = event.getPointerCount();
                Log.d("TAG", "count: "+ count);
                for(int pointerIndex = 0; pointerIndex < count; ++pointerIndex){
                    int pointerId = event.getPointerId(pointerIndex);
                    Circle found = touchedCircles.get(pointerId);
                    if (found == null) break;
                    //Circle circle = touchedCircles.get(key);
                    int pointerX = (int) event.getX(pointerIndex);
                    int pointerY = (int) event.getY(pointerIndex);
                    found.onMove(pointerX, pointerY);
                }return true;
            }
        }
        return false;
    }

    private boolean bitTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int x = (int) event.getX();
                BitModeGenerator.Position pos;
                if(x < Metrics.width / 2){
                    pos = BitModeGenerator.Position.left;
                }
                else{
                    pos = BitModeGenerator.Position.right;
                }
                bitModeGenerator.addArrow(pos);
                return true;
        }
        return false;
    }

    private boolean arrowTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        int x = (int) event.getX(index);
        int y = (int) event.getY(index);
        GameObject object;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                Circle circle = arrowModeGenerator.addCircle(x, y, id);
                circle.onTouchDown(x, y);
                touchedCircles.put(id, circle);
                return true;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:{
                Circle found = touchedCircles.get(id);
                arrowModeGenerator.finishCircle(found, id);
                if (found == null) break;
                found.onTouchUp();
                touchedCircles.remove(id);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                int count = event.getPointerCount();
                for(int pointerIndex = 0; pointerIndex < count; ++pointerIndex){
                    int pointerId = event.getPointerId(pointerIndex);
                    Circle found = touchedCircles.get(pointerId);
                    if (found == null) break;
                    //Circle circle = touchedCircles.get(key);
                    int pointerX = (int) event.getX(pointerIndex);
                    int pointerY = (int) event.getY(pointerIndex);
                    found.onMove(pointerX, pointerY);
                }return true;
            }
        }
        return false;
    }


    public void onBackPressed() {
        if(bitModeGenerator != null){
            bitModeGenerator.save(jsonFileName);
        }
        pauseMusic();
    }
}
