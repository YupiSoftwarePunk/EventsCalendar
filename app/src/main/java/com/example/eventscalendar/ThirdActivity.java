package com.example.eventscalendar;

import android.annotation.SuppressLint;
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

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_fragment, container, false);
        eventsContainer = view.findViewById(R.id.eventsContainer);

        // Получаем список событий, переданный из SecondActivity
        ArrayList<Event> events = (ArrayList<Event>) getActivity().getIntent().getSerializableExtra("events_list");

        if (events != null && !events.isEmpty()) {
            populateEvents(events);
        } else {
            showEmptyMessage();
        }

        return view;
    }

    private void populateEvents(ArrayList<Event> events) {
        eventsContainer.removeAllViews();

        for (Event event : events) {
            View eventView = LayoutInflater.from(getContext()).inflate(R.layout.event_item_layout, eventsContainer, false);

            TextView eventNameTextView = eventView.findViewById(R.id.eventNameTextView);
            TextView eventDateTextView = eventView.findViewById(R.id.eventDateTextView);
            Button btnAddToCalendar = eventView.findViewById(R.id.btnAddToCalendar);

            eventNameTextView.setText(event.getName());
            eventDateTextView.setText("Дата: " + event.getStartsAt());

            // Открытие ссылки на событие
            eventView.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getUrl()));
                startActivity(browserIntent);
            });

            // Добавление события в сохраненный список
            btnAddToCalendar.setOnClickListener(v -> {
                events.add(event);
                Toast.makeText(getContext(), "Добавлено в календарь: " + event.getName(), Toast.LENGTH_SHORT).show();
            });

            eventsContainer.addView(eventView);
        }
    }

    private void showEmptyMessage() {
        TextView emptyMessage = new TextView(getContext());
        emptyMessage.setText("События не найдены");
        emptyMessage.setTextSize(16);
        emptyMessage.setPadding(16, 16, 16, 16);
        eventsContainer.addView(emptyMessage);
    }
}
