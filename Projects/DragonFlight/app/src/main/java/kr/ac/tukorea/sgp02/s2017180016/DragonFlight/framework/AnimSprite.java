package kr.ac.tukorea.sgp02.s2017180016.DragonFlight.framework;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.sgp02.s2017180016.DragonFlight.game.MainGame;

public class AnimSprite extends Sprite{
    protected final int imageWidth;
    protected final int imageHeight;
    protected final int frameCount;
    protected Rect srcRect = new Rect();
    private final float framesPerSecond;

    private long createdOn;

    public AnimSprite(float x, float y, float w, float h, int bitmapResId, float framesPerSecond, int frameCount) {
        super(x, y, w, h, bitmapResId);


        imageHeight = bitmap.getHeight();
        if(frameCount == 0){
            imageWidth = imageHeight;
            frameCount = bitmap.getWidth() / imageHeight;
        }else {
            imageWidth = bitmap.getWidth() / frameCount;
        }
        this.framesPerSecond = framesPerSecond;
        this.frameCount = frameCount;

        srcRect.set(0, 0, imageWidth, imageHeight);
        createdOn = System.currentTimeMillis();
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * framesPerSecond) % frameCount;
        srcRect.set(imageWidth * frameIndex, 0,
                imageWidth * (frameIndex + 1), imageHeight);
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }
}
