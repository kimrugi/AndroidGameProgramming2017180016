package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class Arrow extends Sprite implements GameObject {
    static private final float height = Metrics.height / 24;
    static private final float width = Metrics.height / 12;

    public float startTime;
    private float endTime;
    private float angle;

    public Arrow(float x, float y, float angle, Circle circle, float startTime, float endTime) {
        super(x, y, width, height, R.mipmap.arrow);
        this.startTime = startTime;
        this.endTime = endTime;
        this.angle = angle;
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        if(game.totalTime >= endTime){
            game.remove(this);
            return;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, x, y);
        canvas.drawBitmap(bitmap, null, dstRect, null);
        canvas.restore();
    }
}
