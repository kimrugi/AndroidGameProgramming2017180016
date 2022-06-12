package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.util.HashMap;

public class BitmapPool {
    private static HashMap<Integer, Bitmap> bitmaps = new HashMap<>();
    private static HashMap<String, Bitmap> assetBitmaps = new HashMap<>();
    public static Bitmap get(int mipmapResId) {
        Bitmap bitmap = bitmaps.get(mipmapResId);
        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, mipmapResId);
            bitmaps.put(mipmapResId, bitmap);
        }
        return bitmap;
    }

    public static Bitmap get(String fileName) {
        Bitmap bitmap = assetBitmaps.get(fileName);

            if (bitmap == null) {
                AssetManager assets = GameView.view.getContext().getAssets();
                try {
                    bitmap = BitmapFactory.decodeStream(assets.open(fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assetBitmaps.put(fileName, bitmap);
            }
        return bitmap;
    }
}
