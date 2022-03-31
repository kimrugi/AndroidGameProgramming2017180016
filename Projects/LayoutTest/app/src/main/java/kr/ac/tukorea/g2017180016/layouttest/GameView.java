package kr.ac.tukorea.g2017180016.layouttest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    private Paint paint = new Paint();

    private Paint redRectPaint = new Paint();
    private Rect redRect = new Rect();

    private Paint blueRectPaint = new Paint();
    private Paint crossLinePaint = new Paint();
    private Rect whiteRect = new Rect();
    private Paint whiteRectPaint = new Paint();


    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint.setColor(Color.BLUE);
        Resources res = getResources();

        redRectPaint.setColor(Color.RED);
        blueRectPaint.setColor(Color.BLUE);
        crossLinePaint.setColor(Color.WHITE);
        whiteRectPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int contentWidth = width - paddingRight - paddingLeft;
        int contentHeight = height - paddingTop - paddingBottom;

        int size = contentWidth < contentHeight ? contentWidth : contentHeight;

        int centerX = paddingLeft + contentWidth / 2;
        int centerY = paddingTop + contentHeight / 2;


        drawBackground(canvas, paddingLeft, paddingTop, contentWidth, contentHeight);
        drawCrossLine(canvas, paddingLeft, paddingTop, contentWidth, contentHeight, size);
        drawWhiteBoxs(canvas, contentWidth, contentHeight, size, centerX, centerY);
        drawRedBoxs(canvas, contentWidth, contentHeight, size, centerX, centerY);


    }

    private void drawWhiteBoxs(Canvas canvas, int contentWidth, int contentHeight, int size, int centerX, int centerY) {
        drawHorizontalBox(canvas, contentWidth, centerX, centerY, size / 20 * 3, whiteRect, whiteRectPaint);
        drawVerticalBox(canvas, contentHeight, centerX, centerY, size / 20 * 3, whiteRect, whiteRectPaint);
    }

    private void drawVerticalBox(Canvas canvas, int contentHeight, int centerX, int centerY, int i, Rect whiteRect, Paint whiteRectPaint) {
        int rectWidth = i;
        whiteRect.set(centerX - rectWidth, centerY - contentHeight / 2,
                centerX + rectWidth, centerY + contentHeight / 2);
        canvas.drawRect(whiteRect, whiteRectPaint);
    }

    private void drawHorizontalBox(Canvas canvas, int contentWidth, int centerX, int centerY, int i, Rect whiteRect, Paint rectPaint) {
        int rectHeight = i;
        whiteRect.set(centerX - contentWidth / 2, centerY - rectHeight,
                centerX + contentWidth / 2, centerY + rectHeight);
        canvas.drawRect(whiteRect, rectPaint);
    }

    private void drawCrossLine(Canvas canvas, int paddingLeft, int paddingTop, int contentWidth, int contentHeight, int size) {
        crossLinePaint.setStrokeWidth(size / 10);
        int left = paddingLeft;
        int top = paddingTop - size /20;
        int right = paddingLeft + contentWidth;
        int bottom = contentHeight + size /20;

        canvas.drawLine(left, top, right,
                bottom, crossLinePaint);

        canvas.drawLine(left, bottom,
                right, top, crossLinePaint);
    }

    private void drawRedBoxs(Canvas canvas, int contentWidth, int contentHeight, int size, int centerX, int centerY) {
        drawHorizontalBox(canvas, contentWidth, centerX, centerY, size / 10, redRect, redRectPaint);
        drawVerticalBox(canvas, contentHeight, centerX, centerY, size / 10, redRect, redRectPaint);
    }

    private void drawBackground(Canvas canvas, int paddingLeft, int paddingTop, int contentWidth, int contentHeight) {
        canvas.drawRoundRect(paddingLeft, paddingTop, paddingLeft + contentWidth,
                paddingTop + contentHeight, 30, 40, paint);
    }


}
