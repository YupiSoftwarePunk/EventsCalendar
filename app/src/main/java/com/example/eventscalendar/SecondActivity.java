package com.example.eventscalendar;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private Spinner spinnerEventTheme;
    private Button btnSearchEvents;
    private String selectedTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        spinnerEventTheme = findViewById(R.id.spinnerEventTheme);
        btnSearchEvents = findViewById(R.id.btnSearchEvents);

        // Массив тем событий
        String[] eventThemes = {
                "Концерты", "Искусство и культура", "Экскурсии и путешествия", "Вечеринки",
                "Для детей", "Театры", "Бизнес", "Психология и самопознание", "Наука",
                "ИТ и интернет", "Другие события", "Спорт", "Выставки", "Интеллектуальные игры",
                "Хобби и творчество", "Кино", "Другие развлечения", "Красота и здоровье",
                "Еда", "Иностранные языки", "Гражданские проекты"
        };

        // Адаптер для Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventThemes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventTheme.setAdapter(adapter);

        // Слушатель выбора темы
        spinnerEventTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTheme = eventThemes[position]; // Сохраняем выбранную тему
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTheme = eventThemes[0];
            }
        });

        btnSearchEvents.setOnClickListener(v -> {
            Toast.makeText(SecondActivity.this, "Поиск событий по теме: " + selectedTheme, Toast.LENGTH_SHORT).show();
            // Здесь добавить вызов API с поиском по выбранной теме
        });
    }
}
