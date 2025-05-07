package com.example.eventscalendar;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView eventsTextView;
    private ArrayList<Event> savedEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_activity);

        calendarView = findViewById(R.id.calendarView);
        eventsTextView = findViewById(R.id.eventsTextView);

        savedEvents = (ArrayList<Event>) getIntent().getSerializableExtra("saved_events_list");

        showSavedEvents();
    }

    private void showSavedEvents() {
        StringBuilder eventsText = new StringBuilder();
        for (Event event : savedEvents) {
            eventsText.append(event.getStartsAt()).append(" - ").append(event.getName()).append("\n");
        }
        eventsTextView.setText(eventsText.toString());
    }
}

