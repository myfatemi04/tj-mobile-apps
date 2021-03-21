package com.myfatemi.lab2betterbuttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    TextView txt;
    EditText edit;
    String name;
    int currentMessage = 0;
    String[] messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);
        txt = findViewById(R.id.activity_main_text);
        edit = findViewById(R.id.editTextTextPersonName);

        messages = getResources().getStringArray(R.array.lyrics);

        btn.setOnClickListener(this);
    }

    public void onClick(View v) {
        txt.setText(messages[currentMessage].replaceAll("\\byou\\b", name));
        System.out.println("messages[" + currentMessage + "]: " + messages[currentMessage]);
        name = edit.getText().toString();
        Log.i("button","The user clicked the button!");
        if (currentMessage + 1 < messages.length) {
            this.currentMessage += 1;
        } else {
            this.currentMessage = 0;
        }
    }

}