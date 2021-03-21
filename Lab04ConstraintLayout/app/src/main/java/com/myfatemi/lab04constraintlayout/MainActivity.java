package com.myfatemi.lab04constraintlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button nextButton;
    Button prevButton;
    int[] layouts = new int[] {
            R.layout.layout_1,
            R.layout.layout_2,
            R.layout.layout_3,
            R.layout.layout_4,
            R.layout.layout_5,
            R.layout.layout_6,
            R.layout.layout_7,
    };
    int layoutIndex = 0;

    private void setLayoutIndex(int index) {
        setContentView(layouts[index]);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);

        nextButton.setOnClickListener((v) -> {
            this.layoutIndex++;
            if (this.layoutIndex == layouts.length) {
                this.layoutIndex = 0;
            }
            this.setLayoutIndex(layoutIndex);
            Log.i("navigation", "Setting content view index to " + this.layoutIndex);
        });

        prevButton.setOnClickListener((v) -> {
            this.layoutIndex--;
            if (this.layoutIndex == -1) {
                this.layoutIndex = layouts.length - 1;
            }
            this.setLayoutIndex(layoutIndex);
            Log.i("navigation", "Setting content view index to " + this.layoutIndex);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutIndex(layoutIndex);
    }
}