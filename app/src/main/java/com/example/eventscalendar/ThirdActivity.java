package com.example.eventscalendar;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

public class ThirdActivity extends Fragment {

    private LinearLayout eventsContainer;
    private TextView nextPageTextView;
    private String theme = "Концерты"; // Пример темы для парсинга

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_fragment, container, false);

        eventsContainer = view.findViewById(R.id.eventsContainer);
        nextPageTextView = view.findViewById(R.id.nextPageTextView);

        // Запускаем парсинг
        fetchEventsFromTimepad(theme);

        nextPageTextView.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Переход на следующую страницу", Toast.LENGTH_SHORT).show();
            // Здесь можно реализовать переход на новую активность
        });

        return view;
    }

    private void fetchEventsFromTimepad(String query) {
        String apiUrl = "https://api.timepad.ru/v1/events?fields=name,description,starts_at,location&limit=10&skip=0&keywords=" + query;

        new FetchEventsTask().execute(apiUrl);
    }

    private class FetchEventsTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                connection.disconnect();

                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getJSONArray("values");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray events) {
            if (events == null) {
                Toast.makeText(getContext(), "Ошибка при загрузке событий", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < events.length(); i++) {
                try {
                    JSONObject event = events.getJSONObject(i);
                    String name = event.getString("name");
                    String url = event.getString("url");
                    String date = event.getString("starts_at");

                    addEventView(name, date, url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addEventView(String name, String date, String url) {
        View eventView = LayoutInflater.from(getContext())
                .inflate(R.layout.event_item_layout, eventsContainer, false);

        TextView eventNameTextView = eventView.findViewById(R.id.eventNameTextView);
//        TextView eventDateTextView = eventView.findViewById(R.id.eventDateTextView);

        eventNameTextView.setText(name);
//        eventDateTextView.setText("Дата: " + date);

        eventNameTextView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        eventsContainer.addView(eventView);
    }
}
