package kr.ac.tukorea.sgp02.s2017180016.cookierun.framework.res;

import android.content.res.Resources;
import android.util.TypedValue;

import kr.ac.tukorea.ge.sgp02.s12345678.dragonflight.framework.view.GameView;

public class Metrics {
    public static int width;
    public static int height;

    public static float size(int dimenResId) {
        Resources res = GameView.view.getResources();
        float size = res.getDimension(dimenResId);
        return size;
    }

    public static float floatValue(int dimenResId) {
        Resources res = GameView.view.getResources();
//        return res.getFloat(dimenResId);

        TypedValue outValue = new TypedValue();
        res.getValue(dimenResId, outValue, true);
        float value = outValue.getFloat();
        return value;
    }
}
