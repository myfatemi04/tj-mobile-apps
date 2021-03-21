package com.myfatemi.lab01buttons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button incrementButton;
    Button decrementButton;
    Button holdButton;
    TextView cashCounter;
    TextView gamestopCounter;
    TextView gamestopPrice;
    View baseView;
    double cash = 500;
    int gamestopCount = 0;
    int currentDay = 0;

    // This CSV data is sourced from Yahoo Finance
    // https://finance.yahoo.com/quote/GME/history
    // The columns are Date,Open,High,Low,Close,Adj Close,Volume
    // We are only concerned about Date (0) and Close (4)
    ArrayList<String[]> gamestopData = new ArrayList<String[]>();

    private String getCurrentStockTimeframe() {
        return gamestopData.get(currentDay)[0];
    }

    private double getCurrentStockClosePrice() {
        return Double.parseDouble(gamestopData.get(currentDay)[4]);
    }

    private void readGamestopData() {
        if (gamestopData.size() > 0) {
            Log.wtf("readGamestopData", "Gamestop data is already loaded, not adding it again!");
            return;
        }
        
        try {
            InputStreamReader is = new InputStreamReader(getAssets().open("GME.csv"));
            BufferedReader reader = new BufferedReader(is);
            reader.readLine(); // Skip the labels
            String line;
            while ((line = reader.readLine()) != null) {
                gamestopData.add(line.split(","));
            }
        } catch (IOException e) {
            Log.e("readGamestopData", e.getMessage(), e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readGamestopData();

        baseView = findViewById(R.id.base_view);
        incrementButton = findViewById(R.id.main_activity_increment_button);
        decrementButton = findViewById(R.id.main_activity_decrement_button);
        holdButton = findViewById(R.id.main_activity_hold_button);
        cashCounter = findViewById(R.id.cash_counter);
        gamestopCounter = findViewById(R.id.gamestop_counter);
        gamestopPrice = findViewById(R.id.gamestop_price_label);

        incrementButton.setOnClickListener((view) -> {
            if (currentDay + 1 < gamestopData.size()) {
                System.out.println("Hi Mr Tra!!");
                Log.i("investButton", "Invested in GameStop, made some cash");
                if (cash >= getCurrentStockClosePrice()) {
                    gamestopCount++;
                    cash -= getCurrentStockClosePrice();
                    currentDay++;
                }
                render();
            }
        });

        holdButton.setOnClickListener((view) -> {
            if (currentDay + 1 < gamestopData.size()) {
                System.out.println("Holding");
                Log.i("holdButton", "User clicked HOLD");
                currentDay++;
                render();
            }
        });

        render();

        decrementButton.setOnClickListener((view) -> {
            if (currentDay + 1 < gamestopData.size()) {
                System.out.println("Hello!!");
                Log.i("sellButton", "Selling the GME");
                if (gamestopCount > 0) {
                    cash += getCurrentStockClosePrice() * gamestopCount;
                    gamestopCount = 0;
                    currentDay++;
                }
                render();
            }
        });
    }

    public void render() {
        gamestopPrice.setText("GME: $" + getCurrentStockClosePrice());
        gamestopCounter.setText(gamestopCount + " GME");
        cashCounter.setText("$" + (int)cash + " + $" + (int)(getCurrentStockClosePrice() * gamestopCount) + " in GME");
    }
}