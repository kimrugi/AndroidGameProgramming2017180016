package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.CollisionHelper;
import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework.GameObject;

public class CollisionChecker implements GameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName()    ;

    @Override
    public void update() {
        MainGame game = MainGame.getInstance();
        ArrayList<GameObject> enemies = game.objectsAt(MainGame.Layer.enemy);
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
                    game.remove(bullet);
                    float power = bullet.getPower();
                    Boolean dead =  enemy.decreaseLife(power);
                    if(dead) {
                        game.remove(enemy);
                        game.score.add(enemy.getScore());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
