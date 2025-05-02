package com.example.eventscalendar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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

public class SecondActivity extends AppCompatActivity {

    private Spinner spinnerEventTheme;
    private Button btnSearchEvents;
    private String selectedTheme;

    private static final String API_URL = "https://api.timepad.ru/v1/events";
    private static final String API_TOKEN = "cd82a3fd9055a688eff7bc85c87fdf0960fd646e";

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
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTheme = eventThemes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTheme = eventThemes[0];
            }
        });

        btnSearchEvents.setOnClickListener(v -> {
            Toast.makeText(SecondActivity.this, "Поиск событий по теме: " + selectedTheme, Toast.LENGTH_SHORT).show();
            new FetchEventsTask().execute(selectedTheme);
        });
    }

    private class FetchEventsTask extends AsyncTask<String, Void, ArrayList<Event>> {

        @Override
        protected ArrayList<Event> doInBackground(String... themes) {
            ArrayList<Event> events = new ArrayList<>();
            String query = themes[0];

            String urlString = API_URL + "?fields=name,url,starts_at,location&limit=10&keywords=" + query + "&city=Екатеринбург";
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + API_TOKEN);
                connection.setRequestProperty("Content-Type", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray values = jsonResponse.getJSONArray("values");

                for (int i = 0; i < values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    String name = obj.getString("name");
                    String urll = obj.getString("url");
                    String startsAt = obj.getString("starts_at");

                    // Проверка наличия location
                    JSONObject locationObj = obj.optJSONObject("location");
                    String city = "Город не указан";
                    String address = "Адрес не указан";

                    if (locationObj != null) {
                        city = locationObj.optString("city", "Город не указан");
                        address = locationObj.optString("address", "Адрес не указан");
                    }

                    events.add(new Event(name, urll, startsAt, city, address));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return events;
        }

        @Override
        protected void onPostExecute(ArrayList<Event> events) {
            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra("events_list", events);
            startActivity(intent);
        }
    }
}
