package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Choreographer;
import android.view.MotionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app.GameActivity;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Background;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameView;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Recyclable;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.RecycleBin;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    private final Paint collisionPaint = new Paint();
    Score score;

    private CollisionChecker collisionChecker;
    private MediaPlayer mediaPlayer;

    private String chartFileName;
    private String musicFileName;
    private String backGroundFileName;
    private Background pausedImage;
    private boolean isPaused = false;

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
        isPaused = false;
    }

    public void pauseMusic() {
        mediaPlayer.pause();
        isPaused = true;
    }

    public void setMusic(String fileName) {
        musicFileName = fileName;
    }

    public void setMediaPlayer() {
        AssetManager assets = GameView.view.getContext().getAssets();
        mediaPlayer = new MediaPlayer();
        try {
            if(musicFileName == null){
                Log.e(TAG, "Music is not set. Instead, Play default Music");
                musicFileName = "lune_8bit.mp3";
            }
            AssetFileDescriptor afd = assets.openFd(musicFileName);
            mediaPlayer.setDataSource(afd);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setChart(String chartFileName){
        this.chartFileName = chartFileName;
    }

    public void setBackGround(String fileName) { this.backGroundFileName = fileName; }

    public void onBackPressed() {
        if (!isPaused) {
            pauseMusic();
            pausedImage = new Background(R.mipmap.paused);
            add(Layer.ui, pausedImage);
        }else {
            remove(pausedImage);
            Choreographer.getInstance().postFrameCallbackDelayed(new Choreographer.FrameCallback() {
                @Override
                public void doFrame(long l) {
                    startMusic();
                }
            }, 3000);
        }
    }

    public void endGame() {
        Choreographer.getInstance().postFrameCallbackDelayed(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long l) {
                if(mediaPlayer.isPlaying()){
                    Choreographer.getInstance().postFrameCallbackDelayed(this, 1000);
                }
                GameActivity.getInstance().finish();
            }
        }, 1000);
    }

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

        ObjectGenerator generator = new ObjectGenerator(chartFileName);
        add(Layer.controller, generator);

        score = new Score();
        score.set(0);
        add(Layer.ui, score);

        Background background = new Background(backGroundFileName);
        add(Layer.background, background);
        //play Music
        if(mediaPlayer == null) {
            setMediaPlayer();
        }
        totalTime = -3.0f;
        Choreographer.getInstance().postFrameCallbackDelayed(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long l) {
                startMusic();
            }
        }, 3000);
    }

    private void initLayers(int count) {
        layers = new ArrayList<>();
        for(int i = 0; i < count; ++i){
            layers.add(new ArrayList<>());
        }
    }

    public void update(long elapsedNanos) {
        if(mediaPlayer.isPlaying()) {
            totalTime = mediaPlayer.getCurrentPosition() / 1000.f;
        }else if (!isPaused){
            float frameTime = elapsedNanos * 1e-9f; // 1_000_000_000.0f;
            totalTime += frameTime;
        }
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
