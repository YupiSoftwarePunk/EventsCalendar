package com.example.eventscalendar;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FourthActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView eventsTextView;
    private ArrayList<Event> savedEvents;
    private HashMap<Long, String> eventDatesMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_activity);

        calendarView = findViewById(R.id.calendarView);
        eventsTextView = findViewById(R.id.eventsTextView);

        savedEvents = (ArrayList<Event>) getIntent().getSerializableExtra("saved_events_list");

        if (savedEvents != null) {
            processSavedEvents();
        }

        // Показываем события при клике на дату
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);
            if (eventDatesMap.containsKey(selectedDate)) {
                eventsTextView.setText(eventDatesMap.get(selectedDate));
            } else {
                eventsTextView.setText("Нет событий на выбранную дату");
            }
        });
    }

    private void processSavedEvents() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        StringBuilder allEventsText = new StringBuilder();

        for (Event event : savedEvents) {
            try {
                Date eventDate = format.parse(event.getStartsAt().substring(0, 10));
                if (eventDate != null) {
                    long eventTimeMillis = eventDate.getTime();
                    eventDatesMap.put(eventTimeMillis, event.getName());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            allEventsText.append(event.getStartsAt()).append(" - ").append(event.getName()).append("\n");
        }

        eventsTextView.setText(allEventsText.toString());
    }

    private long getDateInMillis(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0, 0);
        return calendar.getTimeInMillis();
    }
}
