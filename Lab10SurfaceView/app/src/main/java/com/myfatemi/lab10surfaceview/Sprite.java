package com.myfatemi.lab10surfaceview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Sprite extends RectF {
    private int dx = 0, dy = 0;
    private int lastDx = 1, lastDy = 0;
    private Paint paint;
    private int frameSequenceNumber = 0;

    public static final int GAME_UNIT_TO_PIXELS = 10;
    public static final int BITMAP_GRID_SQUARE_WIDTH = 32;
    public static final int BITMAP_GRID_SQUARE_HEIGHT = 48;

    public static final byte[] WALKING_DOWN_FRAMES = {0, 1, 2, 3};
    public static final byte[] WALKING_LEFT_FRAMES = {4, 5, 6, 7};
    public static final byte[] WALKING_RIGHT_FRAMES = {8, 9, 10, 11};
    public static final byte[] WALKING_UP_FRAMES = {12, 13, 14, 15};

    public Sprite(int color, float x, float y, float width, float height) {
        super(x, y, x + width, y + height);

        Log.i("sprite-initialize", "Sprite initialized with color 0x" + Integer.toString(color, 16));

        this.paint = new Paint(color);
    }

    public Sprite(int color, float x, float y) {
        this(color, x, y, 10, 10);
    }

    public Sprite(int color) {
        this(color, 5, 5);
    }

    public void setColor(int color) {
        this.paint = new Paint(color);
    }

    public boolean step() {
        Log.i("sprite", "Stepping sprite by (" + this.dx + ", " + this.dy + ")");

        offset(dx, dy);

        boolean moved = this.dx != 0 || this.dy != 0;
        if (moved) {
            this.frameSequenceNumber++;
        }
        return moved;
    }

    public static Rect bitmapFrameIDToSourceRect(byte frameID, int squareWidth, int squareHeight) {
        int row = frameID / 4;
        int col = frameID % 4;
        return new Rect(
                squareWidth * col,
                squareHeight * row,
                squareWidth * (col + 1),
                squareHeight * (row + 1)
        );
    }

    public void setMovingDirection(int dx, int dy) {
        if (dx == 0 && dy == 0) {
            this.dx = 0;
            this.dy = 0;
        } else {
            this.dx = dx;
            this.dy = dy;
            this.lastDx = dx;
            this.lastDy = dx;
        }
        this.frameSequenceNumber = 0;
    }

    private byte getCurrentFrameID() {
        byte[] frames = null;
        if (lastDx == 0) {
            if (lastDy > 0) {
                frames = WALKING_UP_FRAMES;
            } else if (lastDy < 0) {
                frames = WALKING_DOWN_FRAMES;
            }
        } else {
            if (lastDx > 0) {
                frames = WALKING_RIGHT_FRAMES;
            } else {
                frames = WALKING_LEFT_FRAMES;
            }
        }
        if (frames == null) {
            return WALKING_LEFT_FRAMES[0];
        } else {
            return frames[frameSequenceNumber % 4];
        }
    }

    public void draw(Canvas canvas, Bitmap bitmap) {
        // Convert game units to screen units
        RectF spriteProjection = new RectF(
                this.left * GAME_UNIT_TO_PIXELS,
                this.top * GAME_UNIT_TO_PIXELS,
                this.right * GAME_UNIT_TO_PIXELS,
                this.bottom * GAME_UNIT_TO_PIXELS);

        Log.i("sprite", "Drawing sprite at " + spriteProjection.toShortString());

        Rect sourceRect = bitmapFrameIDToSourceRect(getCurrentFrameID(), bitmap.getWidth()/4, bitmap.getHeight()/4);

        Log.i("paint", this.paint.toString());

//        canvas.drawRect(spriteProjection, this.paint);

        canvas.drawBitmap(bitmap, sourceRect, spriteProjection, null);
    }
}
