package kr.ac.tukorea.sgp02.s2017180045.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

public class Fighter implements GameObject{

    private static final String TAG = Fighter.class.getSimpleName();
    private static Bitmap bitmap;
    private static Rect srcRect = new Rect();
    private RectF dstRect = new RectF();

    private float x, y;
//    private float dx, dy;
    private float tx, ty;
    private float radius;
    public Fighter(float x, float y){
        this.x = x;
        this.y = y;
        tx = x;
        ty = y;
//        float radius_dp = 100;
//        DisplayMetrics displayMetrics = GameView.view.getResources().getDisplayMetrics();
//        radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius_dp, displayMetrics);
        radius = Metrics.size(R.dimen.fighter_radius);
        dstRect.set(x - radius,
                y - radius,
                x+radius,
                y+radius);

        if (bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            srcRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    public void update(){

        float angle = (float) Math.atan2(ty-y, tx-x);
        float speed = Metrics.size(R.dimen.fighter_speed);
        float dist = speed * MainGame.getInstance().frameTime;
        float dx = (float) (dist * Math.cos(angle));
        float dy = (float) (dist * Math.sin(angle));
        //Log.d(TAG, "x: " + x + "y: "+y);
        //Log.d(TAG, "tx: " + tx + "ty: "+ty);
        if(Math.abs( tx - x) < dx){
            x = tx;
        }else{
            x += dx;
        }

        if(Math.abs( ty - y) < dy){
            y = ty;
        }else{
            y += dy;
        }
//        x += dx;
//        y += dy;

        /*float speed = 1000;
        float dx = x - tx;
        float dy = y - ty;
        float size = dx+dy;
        dx = (dx / size) * speed;
        dy = (dy / size) * speed;
        x += dx ;
        y += dy ;*/

        dstRect.set(x - radius,
                y - radius,
                x+radius,
                y+radius);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
    }

    public void setTargetPosition(int x, int y) {
        tx = x;
        ty = y;
//        int radius = dstRect.width() /2;
//        dstRect.set(x - radius, y - radius, x+radius, y+radius);
    }
}
