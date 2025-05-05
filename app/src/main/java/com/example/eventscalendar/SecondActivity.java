package com.example.eventscalendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondActivity extends AppCompatActivity {

    private Spinner spinnerEventTheme;
    private Button btnSearchEvents;
    private String selectedTheme;

    private static final String API_URL = "https://api.timepad.ru/v1/events";
    private static final String API_TOKEN = "Bearer cd82a3fd9055a688eff7bc85c87fdf0960fd646e";

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        spinnerEventTheme = findViewById(R.id.spinnerEventTheme);
        btnSearchEvents = findViewById(R.id.btnSearchEvents);

        String[] eventThemes = {
                "Концерты", "Искусство и культура", "Экскурсии и путешествия", "Вечеринки",
                "Для детей", "Театры", "Бизнес", "Психология и самопознание", "Наука",
                "ИТ и интернет", "Другие события", "Спорт", "Выставки", "Интеллектуальные игры",
                "Хобби и творчество", "Кино", "Другие развлечения", "Красота и здоровье",
                "Еда", "Иностранные языки", "Гражданские проекты"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, eventThemes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEventTheme.setAdapter(adapter);

        spinnerEventTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedTheme = eventThemes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTheme = eventThemes[0];
            }
        });

        btnSearchEvents.setOnClickListener(v -> {
            Toast.makeText(SecondActivity.this, "Поиск событий по теме: " + selectedTheme, Toast.LENGTH_SHORT).show();
            fetchEvents(selectedTheme);
        });
    }

    private void fetchEvents(String theme) {
        executorService.execute(() -> {
            ArrayList<Event> events = new ArrayList<>();
            String query = theme;

            String urlString = API_URL + "?fields=name,url,starts_at,location&limit=10&keywords=" + query + "&city=Екатеринбург";
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", API_TOKEN);
                connection.setRequestProperty("Content-Type", "application/json");

                // Проверка ответа сервера
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("API_ERROR", "Ошибка запроса: " + responseCode);
                    handler.post(() -> Toast.makeText(SecondActivity.this, "Ошибка сети: " + responseCode, Toast.LENGTH_SHORT).show());
                    return;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                JSONObject jsonResponse = new JSONObject(response.toString());

                // Проверка на наличие "values"
                if (!jsonResponse.has("values")) {
                    Log.e("API_ERROR", "Ответ не содержит 'values'");
                    handler.post(() -> Toast.makeText(SecondActivity.this, "Ошибка: данные не получены", Toast.LENGTH_SHORT).show());
                    return;
                }

                JSONArray values = jsonResponse.getJSONArray("values");

                for (int i = 0; i < values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    String name = obj.optString("name", "Без названия");
                    String urll = obj.optString("url", "");
                    String startsAt = obj.optString("starts_at", "Нет даты");

                    JSONObject locationObj = obj.optJSONObject("location");
                    String city = locationObj != null ? locationObj.optString("city", "Город не указан") : "Город не указан";
                    String address = locationObj != null ? locationObj.optString("address", "Адрес не указан") : "Адрес не указан";

                    events.add(new Event(name, urll, startsAt, city, address));
                }

                handler.post(() -> handleEventsResult(events));

            } catch (Exception e) {
                Log.e("API_ERROR", "Ошибка при запросе данных", e);
                handler.post(() -> Toast.makeText(SecondActivity.this, "Ошибка сети", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void handleEventsResult(ArrayList<Event> events) {
        if (events != null && !events.isEmpty()) {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra("events_list", events);
            startActivity(intent);
        } else {
            Toast.makeText(SecondActivity.this, "События не найдены", Toast.LENGTH_SHORT).show();
        }
    }
}
