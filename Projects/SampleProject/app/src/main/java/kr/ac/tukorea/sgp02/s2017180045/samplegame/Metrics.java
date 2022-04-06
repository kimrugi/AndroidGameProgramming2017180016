package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.content.res.Resources;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResid){
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResid);
    }
}
