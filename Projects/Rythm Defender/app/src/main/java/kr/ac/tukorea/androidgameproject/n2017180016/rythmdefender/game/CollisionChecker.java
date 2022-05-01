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
        /*ArrayList<GameObject> enemies = game.objectsAt(MainGame.Layer.enemy);
        ArrayList<GameObject> bullets = game.objectsAt(MainGame.Layer.bullet);
        for (GameObject o1 : enemies) {
            if (!(o1 instanceof Enemy)) {
                continue;
            }
            Enemy enemy = (Enemy) o1;
            for (GameObject o2 : bullets) {
                if (!(o2 instanceof Bullet)) {
                    continue;
                }
                Bullet bullet = (Bullet) o2;
                if(CollisionHelper.collides(enemy, bullet)){
                    Log.d(TAG, "collision");
                    game.remove(enemy);
                    game.remove(bullet);
                    game.score.add(enemy.getScore());
                    break;
                }
            }
        }*/
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