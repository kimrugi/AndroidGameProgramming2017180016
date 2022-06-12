package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.CollisionHelper;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework.GameObject;


public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName()    ;

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
    }

    @Override
    public void draw(Canvas canvas) {

    }

    public GameObject checkTouchCollision(int x, int y){
        MainGame game = MainGame.getInstance();
        ArrayList<GameObject> circles = game.objectsAt(MainGame.Layer.circle);
        for(GameObject object : circles){
            if(!(object instanceof Circle)) continue;
            Circle circle = (Circle) object;
            if(CollisionHelper.collides(circle, x, y)){
                return circle;
            }
        }
        return null;
    }
}