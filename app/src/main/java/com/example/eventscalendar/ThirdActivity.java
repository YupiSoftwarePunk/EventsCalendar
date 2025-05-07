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
    private ArrayList<Event> savedEvents = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_fragment, container, false);

        eventsContainer = view.findViewById(R.id.eventsContainer);

        if (eventsContainer == null) {
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

        return view;
    }

    private void populateEvents(ArrayList<Event> events) {
        eventsContainer.removeAllViews();

        for (Event event : events) {
            View eventView = LayoutInflater.from(getContext()).inflate(R.layout.event_list_fragment, eventsContainer, false);

            TextView eventNameTextView = eventView.findViewById(R.id.eventNameTextView);
            TextView eventDateTextView = eventView.findViewById(R.id.eventDateTextView);
            Button btnAddToCalendar = eventView.findViewById(R.id.btnAddToCalendar);

            if (eventNameTextView == null || eventDateTextView == null || btnAddToCalendar == null) {
                Toast.makeText(getContext(), "Ошибка: элементы представления не найдены", Toast.LENGTH_SHORT).show();
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
        startActivity(intent);
    }
}
