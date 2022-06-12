package kr.ac.tukorea.sgp02.s2017180016.smoothingpath;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class PathView extends View {
    private Bitmap bitmap;
    private float halfWidth;
    private float halfHeight;
    private PointF soccerPos = new PointF();

    public interface Listener{
        public void onAdd();
    }
    protected class Point{
        float x;
        float y;
        float dx, dy;
    }
    private Path path = new Path();

    public int getPointCount() {
        return points.size();
    }

    public void start() {
        int ptCount = points.size();
        if (ptCount < 2) {
            return;
        }
        PathMeasure pm = new PathMeasure(path, false);
        float length = pm.getLength();
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        //anim.setDuration(ptCount * 300);
        anim.setDuration((long) length);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float[] pos = new float[2];
            float[] tan = new float[2];

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = animation.getAnimatedFraction();
                pm.getPosTan(length * progress, pos, tan);
                soccerPos.x = pos[0];
                soccerPos.y = pos[1];
                //Log.d(TAG, "pos:" + fighterPos);
                invalidate();
            }
        });
        anim.start();
        
    }


    private static final int DIRECTION_FACTOR = 2;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    protected Listener listener;
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...

    protected ArrayList<Point> points = new ArrayList<>();
    protected Paint paint;

    public PathView(Context context) {
        super(context);
        init(null, 0);
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.PathView, defStyle, 0);

        mExampleColor = a.getColor(
                R.styleable.PathView_exampleColor,
                mExampleColor);

        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);
        paint.setColor(mExampleColor);

        a.recycle();

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.soccer);
        halfWidth = bitmap.getWidth() / 2;
        halfHeight = bitmap.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int ptCnt =points.size();
        if(ptCnt <= 0) return;
        Point first = points.get(0);
        if(ptCnt == 1){
            canvas.drawCircle(first.x, first.y, 5.0f, paint);
        }else{
        canvas.drawPath(path, paint);
        }
        canvas.drawBitmap(bitmap,
                soccerPos.x - halfWidth,
                soccerPos.y - halfHeight,
                null);
    }

    protected void buildPath() {
        int ptCount = points.size();
        if (ptCount < 2) return;

        for (int i = ptCount - 2; i < ptCount; i++) {
            Point pt = points.get(i);
            if (i == 0) { // only next
                Point next = points.get(i + 1);
                pt.dx = ((next.x - pt.x) / DIRECTION_FACTOR);
                pt.dy = ((next.y - pt.y) / DIRECTION_FACTOR);
            } else if (i == ptCount - 1) { // only prev
                Point prev = points.get(i - 1);
                pt.dx = ((pt.x - prev.x) / DIRECTION_FACTOR);
                pt.dy = ((pt.y - prev.y) / DIRECTION_FACTOR);
            } else { // prev and next
                Point next = points.get(i + 1);
                Point prev = points.get(i - 1);
                pt.dx = ((next.x - prev.x) / DIRECTION_FACTOR);
                pt.dy = ((next.y - prev.y) / DIRECTION_FACTOR);
            }
        }

        path = new Path();
        Point prev = points.get(0);
        path.moveTo(prev.x, prev.y);
        for (int i = 1; i < ptCount; i++) {
            Point pt = points.get(i);
            path.cubicTo(
                    prev.x + prev.dx, prev.y + prev.dy,
                    pt.x - pt.dx, pt.y - pt.dy,
                    pt.x, pt.y);
            prev = pt;
        }
    }
    public int getExampleColor() {
        return mExampleColor;
    }

    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
//        invalidateTextPaintAndMeasurements();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Point point = new Point();
            point.x = event.getX();
            point.y = event.getY();
            points.add(point);
            buildPath();
            if(listener != null){
                listener.onAdd();
            }
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    public void clear() {
        points.clear();
        path.reset();
        invalidate();
    }
}