package com.myfatemi.lab03sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1, b2, b3, b4;
    TextView tv;
    SeekBar sb;
    SharedPreferences prefs;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);

        sb = findViewById(R.id.seekBar);

        tv = findViewById(R.id.hello_world);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);

        this.loadButtonValues();
    }

    public void loadButtonValues() {
        Log.i("shared_preferences", "Loading SharedPreferences");

        b1.setText(String.format("Button %d: %d", 1, prefs.getInt("button_1", 0)));
        b2.setText(String.format("Button %d: %d", 2, prefs.getInt("button_2", 0)));
        b3.setText(String.format("Button %d: %d", 3, prefs.getInt("button_3", 0)));
        b4.setText(String.format("Button %d: %d", 4, prefs.getInt("button_4", 0)));
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String text = button.getText().toString();

        int clickCount = Integer.parseInt(text.substring("Button _: ".length())) + 1;
        int buttonID = Integer.parseInt(String.valueOf(text.charAt(7)));

        button.setText("Button " + buttonID + ": " + clickCount);

        Log.i("PUT","PUT button_" + buttonID + ": " + clickCount);

        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt("button_" + buttonID, clickCount);
        prefsEditor.apply();

        Log.i("button_click", "Button " + buttonID + " was clicked " + clickCount + " times");
        Log.i("shared_preferences", "Shared preferences for " + buttonID + " were saved");

        String toastText = "You clicked Button " + buttonID + " " + clickCount + " times!";
        Toast toast = Toast.makeText(ctx, toastText, Toast.LENGTH_SHORT);
        toast.show();
    }
}