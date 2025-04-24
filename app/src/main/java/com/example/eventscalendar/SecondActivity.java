package com.example.eventscalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private EditText etEventTheme;
    private Button btnSearchEvents;
    private Button btnNextPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        etEventTheme = findViewById(R.id.etEventTheme);
        btnSearchEvents = findViewById(R.id.btnSearchEvents);
        btnNextPage = findViewById(R.id.btnNextPage);

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

        btnNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });
    }

    private void searchEvents(String theme) {
        // Реализовать парсинг событий по теме с Timepad
        Toast.makeText(this, "Поиск событий по теме: " + theme, Toast.LENGTH_SHORT).show();
    }
}
