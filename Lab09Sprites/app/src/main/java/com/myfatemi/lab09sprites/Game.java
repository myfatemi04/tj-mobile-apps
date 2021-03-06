package com.myfatemi.lab09sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static com.myfatemi.lab09sprites.Sprite.GAME_UNIT_TO_PIXELS;

public class Game extends View {
    private final ArrayList<Sprite> sprites = new ArrayList<>();
    private Bitmap spriteBitmap;
    private Bitmap bombBitmap;
    private static final int CLICKED_COLOR = Color.GREEN;
    public static final int BUTTON_LEFT = 0;
    public static final int BUTTON_RIGHT = 1;

    private void addSprite(Sprite sprite) {
        this.sprites.add(sprite);
        this.invalidate();
    }

    private void moveSprites() {
        for (Sprite s : this.sprites) {
            s.step();
        }
    }

    public Game(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.spriteBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite);
        this.bombBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);

        this.addSprite(new Sprite(Color.RED, 5, 25));
        this.addSprite(new Sprite(Color.RED, 10, 20));
        this.addSprite(new Sprite(Color.RED, 25, 10));
        this.addSprite(new Sprite(Color.RED, 20, 15));
        this.addSprite(new Sprite(Color.RED, 15, 5));
    }

    public void onButtonDown(int direction) {
        this.invalidate();
        for (Sprite sprite : this.sprites) {
            if (direction == BUTTON_LEFT) {
                sprite.setMovingDirection(-1, 0);
            } else {
                sprite.setMovingDirection(1, 0);
            }
        }
    }

    public void onButtonUp() {
        this.invalidate();
        for (Sprite sprite : this.sprites) {
            sprite.setMovingDirection(0, 0);
        }
    }

    private void drawBombs(Canvas canvas) {
        canvas.drawBitmap(this.bombBitmap, null, new Rect(300, 300, 350, 350), null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.moveSprites();

        for (Sprite sprite : this.sprites) {
            sprite.draw(canvas, this.spriteBitmap);
        }

        this.drawBombs(canvas);

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX(), y = event.getY();
        Log.i("touch", "Received a touch event at {x=" + x + ", y=" + y + "}");

        boolean anyChanged = false;

        for (Sprite sprite : this.sprites) {
            if (sprite.contains(x / GAME_UNIT_TO_PIXELS, y / GAME_UNIT_TO_PIXELS)) {
                Log.i("touch", "Touch event interacted with a sprite");
                sprite.setColor(CLICKED_COLOR);
                anyChanged = true;
            }
        }

        if (anyChanged) {
            this.invalidate();
        }

        return super.onTouchEvent(event);
    }
}
