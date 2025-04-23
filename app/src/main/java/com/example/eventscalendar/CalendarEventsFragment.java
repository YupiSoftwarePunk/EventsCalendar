package com.example.eventscalendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eventscalendar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarEventsFragment extends Fragment {

    private LinearLayout eventsContainer;
    private List<CalendarEvent> events = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_fragment, container, false);
        eventsContainer = view.findViewById(R.id.eventsContainer);

        loadEvents();

        return view;
    }

    private void loadEvents() {
        // Здесь должна быть загрузка из API
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

        events.add(new CalendarEvent(
                "Концерт группы 'Кино'",
                sdf.format(new Date(System.currentTimeMillis() + 86400000)), // Завтра
                "Москва, стадион Лужники",
                "https://example.com/event1"
        ));

        events.add(new CalendarEvent(
                "Выставка 'Импрессионисты'",
                sdf.format(new Date(System.currentTimeMillis() + 172800000)), // Послезавтра
                "Санкт-Петербург, Эрмитаж",
                "https://example.com/event2"
        ));

        updateUI();
    }

    private void updateUI() {
        eventsContainer.removeAllViews();

        for (CalendarEvent event : events) {
            addEventToView(event);
        }
    }

    private void addEventToView(CalendarEvent event) {
        View eventView = LayoutInflater.from(getContext())
                .inflate(R.layout.event_item_layout, eventsContainer, false);
//
//        TextView title = eventView.findViewById(R.id.eventTitle);
//        TextView date = eventView.findViewById(R.id.eventDate);
//        TextView location = eventView.findViewById(R.id.eventLocation);
//        Button removeButton = eventView.findViewById(R.id.removeButton);
//
//        title.setText(event.getTitle());
//        date.setText("Дата: " + event.getDate());
//        location.setText("Место: " + event.getLocation());
//
//
//        title.setOnClickListener(v -> {
//            if (getContext() != null) {
//                android.content.Intent browserIntent =
//                        new android.content.Intent(android.content.Intent.ACTION_VIEW,
//                                android.net.Uri.parse(event.getUrl()));
//                startActivity(browserIntent);
//            }
//        });
//
//
//        removeButton.setOnClickListener(v -> {
//            events.remove(event);
//            updateUI();
//        });
//
        eventsContainer.addView(eventView);
    }

    public static class CalendarEvent {
        private String title;
        private String date;
        private String location;
        private String url;

        public CalendarEvent(String title, String date, String location, String url) {
            this.title = title;
            this.date = date;
            this.location = location;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getLocation() {
            return location;
        }

        public String getUrl() {
            return url;
        }
    }
}
