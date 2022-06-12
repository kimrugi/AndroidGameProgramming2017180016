package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.framework;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Background extends Sprite {

    private Paint grayPaint;

    public Background(String fileName) {
        super();
        this.x = Metrics.width / 2;
        this.y = Metrics.height / 2;
        float w = Metrics.width;
        float h = Metrics.height;
        this.radius = w / 2;
        dstRect.set(x - w / 2, y - h / 2, x + w / 2, y + h / 2);

        bitmap = BitmapPool.get(fileName);
        float width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();
        //float height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setDstRect(width, Metrics.height);

        grayPaint = new Paint();
        int level = 200;
        grayPaint.setARGB(level, level, level,level);
    }

    public Background(int id) {
        super(Metrics.width / 2, Metrics.height / 2, Metrics.width, Metrics.height, id);
        grayPaint = new Paint();
        int level = 50;
        grayPaint.setARGB(level, level, level,level);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(dstRect, grayPaint);
    }
}

