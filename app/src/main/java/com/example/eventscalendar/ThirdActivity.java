package com.example.eventscalendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ThirdActivity extends Fragment {

    private LinearLayout eventsContainer;
    private Button btnShowCalendar;
    private ArrayList<Event> savedEvents = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_activity, container, false);
        eventsContainer = view.findViewById(R.id.eventsContainer);
        btnShowCalendar = view.findViewById(R.id.btnShowCalendar); // Кнопка для показа календаря

        if (eventsContainer == null || btnShowCalendar == null) {
            Toast.makeText(getContext(), "Ошибка: проверьте XML-файл event_list_fragment.xml", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Получаем данные через Bundle
        if (getArguments() != null && getArguments().containsKey("events_list")) {
            ArrayList<Event> events = (ArrayList<Event>) getArguments().getSerializable("events_list");

            if (events != null && !events.isEmpty()) {
                populateEvents(events);
            } else {
                showEmptyMessage();
            }
        }

        // Настраиваем кнопку для показа календаря
        btnShowCalendar.setOnClickListener(v -> goToSavedEvents());

        return view;
    }

    private void populateEvents(ArrayList<Event> events) {
        eventsContainer.removeAllViews();

        for (Event event : events) {
            View eventView = LayoutInflater.from(getContext()).inflate(R.layout.third_activity, eventsContainer, false);

            TextView eventNameTextView = eventView.findViewById(R.id.eventNameTextView);
            TextView eventDateTextView = eventView.findViewById(R.id.eventDateTextView);
            Button btnAddToCalendar = eventView.findViewById(R.id.btnAddToCalendar);

            if (eventNameTextView == null || eventDateTextView == null || btnAddToCalendar == null) {
                Toast.makeText(getContext(), "Ошибка: проверьте XML-файл event_item_layout.xml", Toast.LENGTH_SHORT).show();
                continue;
            }

            eventNameTextView.setText(event.getName());
            eventDateTextView.setText("Дата: " + event.getStartsAt());

            eventView.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getUrl()));
                startActivity(browserIntent);
            });

            btnAddToCalendar.setOnClickListener(v -> {
                if (!savedEvents.contains(event)) {
                    savedEvents.add(event);
                    Toast.makeText(getContext(), "Добавлено в календарь: " + event.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Событие уже добавлено!", Toast.LENGTH_SHORT).show();
                }
            });

            eventsContainer.addView(eventView);
        }
    }

    private void showEmptyMessage() {
        if (eventsContainer == null) return;

        TextView emptyMessage = new TextView(getContext());
        emptyMessage.setText("События не найдены");
        emptyMessage.setTextSize(16);
        emptyMessage.setPadding(16, 16, 16, 16);
        eventsContainer.addView(emptyMessage);
    }

    public void goToSavedEvents() {
        Intent intent = new Intent(getActivity(), FourthActivity.class);
        intent.putExtra("saved_events_list", savedEvents);

        // Собираем список дат событий
        ArrayList<String> eventDates = new ArrayList<>();
        ArrayList<String> eventThemes = new ArrayList<>();

        for (Event event : savedEvents) {
            eventDates.add(event.getStartsAt()); // Дата события
            //eventThemes.add(getEventColor(event.getTheme())); // Цвет по теме
        }

        intent.putStringArrayListExtra("event_dates", eventDates);
        intent.putStringArrayListExtra("event_colors", eventThemes);

        startActivity(intent);
    }

    // Метод для определения цвета по теме события
    private String getEventColor(String theme) {
        switch (theme) {
            case "Концерты": return "#FF0000"; // Красный
            case "Искусство и культура": return "#FFA500"; // Оранжевый
            case "Наука": return "#008000"; // Зелёный
            case "ИТ и интернет": return "#0000FF"; // Синий
            default: return "#808080"; // Серый для других тем
        }
    }

}
