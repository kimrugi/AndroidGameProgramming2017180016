package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework;

import android.graphics.RectF;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game.Circle;

public class CollisionHelper {
    public static boolean collides(BoxCollidable o1, BoxCollidable o2) {
        RectF r1 = o1.getBoundingRect();
        RectF r2 = o2.getBoundingRect();

        if (r1.left > r2.right) return false;
        if (r1.top > r2.bottom) return false;
        if (r1.right < r2.left) return false;
        if (r1.bottom < r2.top) return false;

        return true;
    }

    public static boolean collides(BoxCollidable object, int x, int y) {
        RectF r1 = object.getBoundingRect();

        if (r1.left > x) return false;
        if (r1.top > y) return false;
        if (r1.right < x) return false;
        if (r1.bottom < y) return false;

        return true;
    }
}
