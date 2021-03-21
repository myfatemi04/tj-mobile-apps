package com.myfatemi.lab05lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    Context ctx;
    HashMap<String, Integer> temporaryValues = new HashMap<String, Integer>();
    HashMap<String, TextView> temporaryValueLabels = new HashMap<String, TextView>();
    HashMap<String, TextView> sharedPreferencesValueLabels = new HashMap<String, TextView>();

    /*

    onCreate()
    onStart()`
    onResume()
    onPause()
    onStop()
    onRestart()
    onDestroy()

     */

    protected void findLabels() {
        temporaryValueLabels.put("onCreate", (TextView) findViewById(R.id.temporary_on_create));
        temporaryValueLabels.put("onDestroy", (TextView) findViewById(R.id.temporary_on_destroy));
        temporaryValueLabels.put("onPause", (TextView) findViewById(R.id.temporary_on_pause));
        temporaryValueLabels.put("onRestart", (TextView) findViewById(R.id.temporary_on_restart));
        temporaryValueLabels.put("onResume", (TextView) findViewById(R.id.temporary_on_resume));
        temporaryValueLabels.put("onStart", (TextView) findViewById(R.id.temporary_on_start));
        temporaryValueLabels.put("onStop", (TextView) findViewById(R.id.temporary_on_stop));

        sharedPreferencesValueLabels.put("onCreate", (TextView) findViewById(R.id.permanent_on_create));
        sharedPreferencesValueLabels.put("onDestroy", (TextView) findViewById(R.id.permanent_on_destroy));
        sharedPreferencesValueLabels.put("onPause", (TextView) findViewById(R.id.permanent_on_pause));
        sharedPreferencesValueLabels.put("onRestart", (TextView) findViewById(R.id.permanent_on_restart));
        sharedPreferencesValueLabels.put("onResume", (TextView) findViewById(R.id.permanent_on_resume));
        sharedPreferencesValueLabels.put("onStart", (TextView) findViewById(R.id.permanent_on_start));
        sharedPreferencesValueLabels.put("onStop", (TextView) findViewById(R.id.permanent_on_stop));
    }

    protected void insertTemporaryLabelValues() {
        for (Map.Entry<String, TextView> entry : temporaryValueLabels.entrySet()) {
            int currentValue = temporaryValues.containsKey(entry.getKey()) ? temporaryValues.get(entry.getKey()) : 0;
            entry.getValue().setText(entry.getKey() + ": " + currentValue);
        }
    }

    protected void insertPermanentLabelValues() {
        for (Map.Entry<String, TextView> entry : sharedPreferencesValueLabels.entrySet()) {
            int currentValue = this.prefs.getInt(entry.getKey(), 0);
            entry.getValue().setText(entry.getKey() + ": " + currentValue);
        }
    }

    protected void resetTemporaryValues() {
        for (String key : temporaryValues.keySet()) {
            temporaryValues.put(key, 0);
        }
        this.insertTemporaryLabelValues();
    }

    protected void resetPermanentValues() {
        SharedPreferences.Editor editor = prefs.edit();
        for (String key : sharedPreferencesValueLabels.keySet()) {
            editor.putInt(key, 0);
        }
        editor.apply();
        this.insertPermanentLabelValues();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        Button resetButton = findViewById(R.id.reset_values_button);
        resetButton.setOnClickListener((v) -> {
            this.resetPermanentValues();
            this.resetTemporaryValues();
        });

        this.findLabels();
        this.insertTemporaryLabelValues();
        this.insertPermanentLabelValues();

        this.incrementValue("onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.incrementValue("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.incrementValue("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.incrementValue("onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();

        this.incrementValue("onStop");
    }
    @Override
    protected void onRestart() {
        super.onRestart();

        this.incrementValue("onRestart");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.incrementValue("onDestroy");
    }

    protected void incrementValue(String key) {
        this.incrementSharedPreferencesValue(key);
        this.incrementTemporaryValue(key);
    }

    protected void incrementSharedPreferencesValue(String key) {
        SharedPreferences.Editor editor = prefs.edit();

        int current = prefs.getInt(key, 0);
        editor.putInt(key, current + 1);
        editor.apply();

        TextView label = this.sharedPreferencesValueLabels.get(key);
        if (label != null) {
            label.setText(key + ": " + (current + 1));
        }
    }

    protected void incrementTemporaryValue(String key) {
        if (!this.temporaryValues.containsKey(key)) {
            this.temporaryValues.put(key, 0);
        }
        this.temporaryValues.put(key, this.temporaryValues.get(key) + 1);
        TextView label = this.temporaryValueLabels.get(key);
        if (label != null) {
            label.setText(key + ": " + this.temporaryValues.get(key));
        }
    }
}