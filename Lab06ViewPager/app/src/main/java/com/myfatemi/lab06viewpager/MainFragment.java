package com.myfatemi.lab06viewpager;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    ViewPager2 viewPager;
    int position;
    SharedPreferences preferences;

    public MainFragment(ViewPager2 viewPager, int position, SharedPreferences preferences) {
        this.viewPager = viewPager;
        this.position = position;
        this.preferences = preferences;
    }

    public static MainFragment newInstance(ViewPager2 viewPager, int position, SharedPreferences preferences) {
        return new MainFragment(viewPager, position, preferences);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();

        Button button = view.findViewById(R.id.button);

        int value = preferences.getInt("button_" + this.position, 0);

        button.setText(String.format("Button #%d: %d", this.position, value));
        button.setOnClickListener((v) -> {
            Log.i("click", "Button " + this.position + " was clicked.");

            int newValue = this.preferences.getInt("button_" + this.position, 0);
            SharedPreferences.Editor editor = this.preferences.edit();
            editor.putInt("button_" + this.position, newValue + 1);
            editor.apply();
            button.setText(String.format("Button #%d: %d", this.position, newValue + 1));
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}