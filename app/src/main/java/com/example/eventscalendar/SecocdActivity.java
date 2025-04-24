package com.example.eventscalendar;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SecocdActivity {
            btnSearchEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theme = etEventTheme.getText().toString().trim();

                if (theme.isEmpty()) {
                    Toast.makeText(SecondActivity.this, "Введите тему события", Toast.LENGTH_SHORT).show();
                } else {
                    searchEvents(theme);
                }
            }
        });

    private void searchEvents(String theme) {
        //  Реализовать парсинг событий по теме с Timepad
        Toast.makeText(this, "Поиск событий по теме: " + theme, Toast.LENGTH_SHORT).show();
    }
}



