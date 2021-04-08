package com.myfatemi.lab07fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    Button firstViewButton, secondViewButton;
    EditText usernameInput;
    FrameLayout fragmentContainer;
    int selectedFragment = 0;
    String username = "No username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstViewButton = findViewById(R.id.view_1_button);
        secondViewButton = findViewById(R.id.view_2_button);
        fragmentContainer = findViewById(R.id.fragment_container);
        usernameInput = findViewById(R.id.username_input);
        Button submitUsernameButton = findViewById(R.id.submit_username);
        submitUsernameButton.setOnClickListener((v) -> {
            username = usernameInput.getText().toString();
        });

        firstViewButton.setOnClickListener((v) -> {
            selectedFragment = 0;
            LeftFragment fragment = LeftFragment.newInstance(username);

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_right, R.anim.slide_right)
                    .replace(R.id.fragment_container, fragment, "MainFragment")
                    .commit();
        });

        secondViewButton.setOnClickListener((v) -> {
            selectedFragment = 1;
            RightFragment fragment = RightFragment.newInstance("b", "c");

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_left)
                    .replace(R.id.fragment_container, fragment, "MainFragment")
                    .commit();
        });
    }
}