package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class Arrow extends Sprite implements GameObject {
    static private final float height = Metrics.height / 12;
    static private final float width = Metrics.height / 24;

    public float startTime;
    private float endTime;

    public Arrow(float x, float y, Circle circle, float startTime, float endTime) {
        super(x, y, width, height, R.mipmap.arrow);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        if(game.totalTime >= endTime){
            game.remove(this);
            return;
        }
    }
}
