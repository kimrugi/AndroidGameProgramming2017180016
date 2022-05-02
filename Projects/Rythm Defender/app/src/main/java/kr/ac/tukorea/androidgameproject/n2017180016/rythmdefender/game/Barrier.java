package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Metrics;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.Sprite;

public class Barrier extends Sprite implements GameObject {
    private static final float radius = Metrics.width / 12 * 1.3f;

    public Barrier(float x, float y) {
        
        super(x, y, radius, radius, R.mipmap.barrier);
    }

}
