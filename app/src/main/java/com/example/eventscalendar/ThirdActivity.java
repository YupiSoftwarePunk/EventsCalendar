package com.example.eventscalendar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
    private ArrayList<Event> savedEvents = new ArrayList<>();
    private Button btnShowCalendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_activity, container, false);
        eventsContainer = view.findViewById(R.id.eventsContainer);
        btnShowCalendar = view.findViewById(R.id.btnShowCalendar);


        if (eventsContainer == null || btnShowCalendar == null) {
            Toast.makeText(requireActivity(), "Ошибка: проверьте XML-файл third_activity.xml", Toast.LENGTH_SHORT).show();
            Log.e("DEBUG", "Ошибка: btnShowCalendar или eventsContainer не найдены!");
            return view;
        }


        btnShowCalendar.setOnClickListener(v -> {
            Toast.makeText(requireActivity(), "Кнопка 'Показать события в календаре' нажата!", Toast.LENGTH_SHORT).show();
            Log.d("DEBUG", "Кнопка 'Показать события в календаре' нажата.");
            goToSavedEvents();
        });

        // Проверяем, получены ли аргументы с событиями
        if (getArguments() != null && getArguments().containsKey("events_list")) {
            ArrayList<Event> events = (ArrayList<Event>) getArguments().getSerializable("events_list");

            if (events != null && !events.isEmpty()) {
                Log.d("DEBUG", "Получены события: " + events.size());
                populateEvents(events);
            } else {
                Log.d("DEBUG", "Событий нет, показываем сообщение.");
                showEmptyMessage();
            }
        } else {
            Log.d("DEBUG", "getArguments() == null или нет 'events_list'.");
        }

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
                Toast.makeText(getContext(), "Ошибка: проверьте XML-файл third_activity.xml", Toast.LENGTH_SHORT).show();
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

    // Метод для показа событий в календаре
    public void goToSavedEvents() {
        Log.d("DEBUG", "Метод goToSavedEvents() вызван!");
        if (savedEvents.isEmpty()) {
            Toast.makeText(getContext(), "Нет событий для отображения", Toast.LENGTH_SHORT).show();
            Log.d("DEBUG", "savedEvents пустой, не переходим в FourthActivity");
            return;
        }

        Intent intent = new Intent(getActivity(), FourthActivity.class);
        ArrayList<String> eventDates = new ArrayList<>();
        ArrayList<String> eventColors = new ArrayList<>();

        for (Event event : savedEvents) {
            eventDates.add(event.getStartsAt());
            eventColors.add(getEventColor(getTheme(event)));
        }


        intent.putStringArrayListExtra("event_dates", eventDates);
        intent.putStringArrayListExtra("event_colors", eventColors);

        Log.d("DEBUG", "Перед запуском FourthActivity...");
        startActivity(intent);
        Log.d("DEBUG", "FourthActivity должен был запуститься!");
    }

    private String getEventColor(String theme) {
        switch (theme) {
            case "Концерты": return "#FF0000";
            case "Искусство и культура": return "#FFA500";
            case "Наука": return "#008000";
            case "ИТ и интернет": return "#0000FF";
            default: return "#808080"; // для остальных тем
        }
    }

    private String getTheme(Event event) {
        if (event == null || event.getName() == null) {
            return "Другое";
        }

        String name = event.getName().toLowerCase();

        if (name.contains("концерт") || name.contains("музыка")) {
            return "Концерты";
        } else if (name.contains("театр") || name.contains("выставка") || name.contains("искусство")) {
            return "Искусство и культура";
        } else if (name.contains("наука") || name.contains("лекция") || name.contains("образование")) {
            return "Наука";
        } else if (name.contains("спорт") || name.contains("турнир") || name.contains("фитнес")) {
            return "Спорт";
        } else if (name.contains("айти") || name.contains("технологии") || name.contains("программирование")) {
            return "ИТ и интернет";
        } else {
            return "Другое";
        }
    }
}
