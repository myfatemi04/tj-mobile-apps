package com.myfatemi.lab08drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {

    private final Paint SOLID = new Paint(0x00000000);
    private final Paint LINE = new Paint(0x00FF0000);

    private static float TOP = 0;
    private static float BOTTOM = 700;
    private static float LEFT = 0;
    private static float RIGHT = 700;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        SOLID.setStyle(Paint.Style.FILL);
        LINE.setStrokeWidth(4);
        LINE.setStyle(Paint.Style.STROKE);
    }

    private void drawTriangles(Canvas canvas, float left, float right, float top, float bottom, int iterations, boolean biggestAlreadyDrawn) {
        // Draw the background triangle
        if (!biggestAlreadyDrawn) {
            Path biggest = new Path();
            biggest.moveTo((left + right) / 2, top);
            biggest.lineTo(left, bottom);
            biggest.lineTo(right, bottom);
            biggest.moveTo(left, bottom);
            biggest.lineTo(right, bottom);
            canvas.drawPath(biggest, LINE);
        }

        // Take out the center
        Path center = new Path();
        // left middle
        center.moveTo(left + (right - left) / 4, (top + bottom) / 2);
        // --> right middle
        center.lineTo(right - (right - left) / 4, (top + bottom) / 2);
        // --> middle bottom
        center.lineTo((left + right) / 2, bottom);
        // middle bottom
        center.moveTo((left + right) / 2, bottom);
        // --> right middle
        center.lineTo(right - (right - left) / 4, (top + bottom) / 2);

        canvas.drawPath(center, SOLID);

        if (iterations > 1) {
//            Log.i("sierpinski", "Drawing another iteration");

            // Repeat on top, bottom-left, and bottom-right triangles
            float MID_Y = (top + bottom) / 2;
            float MID_X = (left + right) / 2;
            float MIDLEFT_X = left + (right - left) / 4;
            float MIDRIGHT_X = right - (right - left) / 4;

            // Repeat on top
            drawTriangles(canvas, MIDLEFT_X, MIDRIGHT_X, top, MID_Y, iterations - 1, true);

            // Repeat on bottom-left
            drawTriangles(canvas, left, MID_X, MID_Y, bottom, iterations - 1, true);

            // Repeat on bottom-right
            drawTriangles(canvas, MID_X, right, MID_Y, bottom, iterations - 1, true);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangles(canvas, LEFT, RIGHT, TOP, BOTTOM, 4, false);
        TOP--;
        BOTTOM--;
        if (TOP < 0) {
            TOP = 1920;
            BOTTOM = 1920 + 700;
        }
        invalidate();
    }
}
