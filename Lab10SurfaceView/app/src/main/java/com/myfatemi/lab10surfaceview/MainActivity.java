package com.myfatemi.lab10surfaceview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    GameFragment gameFragment;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gameFragment = GameFragment.newInstance();
        this.mediaPlayer = MediaPlayer.create(this, R.raw.kahoot);
        this.mediaPlayer.start();

        Button moveLeftButton = findViewById(R.id.left_button);
        Button moveRightButton = findViewById(R.id.right_button);

        moveLeftButton.setOnTouchListener((View v, MotionEvent e) -> {
            int action = e.getActionMasked();

            if (action == MotionEvent.ACTION_DOWN) {
                Log.i("button", "Left button pressed");
                this.gameFragment.game.onButtonDown(Game.BUTTON_LEFT);
            } else if (action == MotionEvent.ACTION_UP) {
                Log.i("button", "Left button released");
                this.gameFragment.game.onButtonUp();
            }

            return false;
        });


        moveRightButton.setOnTouchListener((View v, MotionEvent e) -> {
            int action = e.getActionMasked();

            if (action == MotionEvent.ACTION_DOWN) {
                Log.i("button", "Right button pressed");
                this.gameFragment.game.onButtonDown(Game.BUTTON_RIGHT);
            } else if (action == MotionEvent.ACTION_UP) {
                Log.i("button", "Right button released");
                this.gameFragment.game.onButtonUp();
            }

            return false;
        });

        this.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_content, gameFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        this.gameFragment.game.start();
    }

    @Override
    protected void onPause() {
        this.gameFragment.game.stop();
    }

    @Override
    protected void onStop() {
        this.mediaPlayer.stop();
        super.onStop();
        this.gameFragment.game.stop();
    }
}