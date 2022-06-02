package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework;

public class Util {
    static public float lerp(float x1, float x2, float factor){
        return x1 * (factor) + x2 * (1 - factor);
    }
}
