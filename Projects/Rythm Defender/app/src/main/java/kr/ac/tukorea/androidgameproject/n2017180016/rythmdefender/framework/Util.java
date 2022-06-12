package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework;

public class Util {
    static public float lerp(float x1, float x2, float factor){
        return x1 * (factor) + x2 * (1 - factor);
    }

    public static float squaredDistance(float cx, float cy, float x, float y) {
        float dx = cx - x;
        float dy = cy - y;
        return (float) (Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}
