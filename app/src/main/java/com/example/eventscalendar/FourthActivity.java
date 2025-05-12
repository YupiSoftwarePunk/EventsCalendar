package com.example.eventscalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FourthActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView eventsTextView;
    private HashMap<Long, String> eventDatesMap = new HashMap<>();
    private HashMap<Long, Integer> eventColorsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_activity);

        calendarView = findViewById(R.id.calendarView);
        eventsTextView = findViewById(R.id.eventsTextView);

        ArrayList<String> eventDates = getIntent().getStringArrayListExtra("event_dates");
        ArrayList<String> eventColors = getIntent().getStringArrayListExtra("event_colors");

        if (eventDates != null && eventColors != null) {
            processSavedEvents(eventDates, eventColors);
        }

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);
            if (eventDatesMap.containsKey(selectedDate)) {
                eventsTextView.setText(eventDatesMap.get(selectedDate));
                eventsTextView.setTextColor(eventColorsMap.get(selectedDate));
            } else {
                eventsTextView.setText("Нет событий на выбранную дату");
                eventsTextView.setTextColor(Color.BLACK);
            }
        });
    }

    private void processSavedEvents(ArrayList<String> eventDates, ArrayList<String> eventColors) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (int i = 0; i < eventDates.size(); i++) {
            try {
                Date eventDate = format.parse(eventDates.get(i).substring(0, 10));
                if (eventDate != null) {
                    long eventTimeMillis = eventDate.getTime();
                    eventDatesMap.put(eventTimeMillis, "Событие на эту дату");


                    int color = Color.parseColor(eventColors.get(i));
                    eventColorsMap.put(eventTimeMillis, color);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private long getDateInMillis(int year, int month, int dayOfMonth) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0, 0);
        return calendar.getTimeInMillis();
    }
}
