package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework;

import android.content.res.Resources;
import android.util.TypedValue;

public class Metrics {
    public static int width;
    public static int height;

    public static float baseWidth;
    public static float baseHeight;

    public static void setSize(int width, int height){
        Metrics.width =width;
        Metrics.height = height;
        Metrics.baseWidth = width / 1000;
        Metrics.baseHeight = height / 1000;
    }

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        return res.getDimension(dimenResId);
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        float value = outValue.getFloat();
        return value;
    }

    public static float getWidth(float pos){
        return pos * baseWidth;
    }

    public static float getHeight(float pos){
        return pos * baseHeight;
    }

    public static int getIntWidth(float pos){
        return (int) (pos / baseWidth);
    }

    public static int getIntHeight(float pos){
        return (int) (pos / baseHeight);
    }
}
