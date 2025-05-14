package com.example.eventscalendar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
    private ThirdActivity thirdActivityInstance = new ThirdActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_activity);

        calendarView = findViewById(R.id.calendarView);
        eventsTextView = findViewById(R.id.eventsTextView);

        ArrayList<String> eventDates = getIntent().getStringArrayListExtra("event_dates");
        ArrayList<String> eventNames = getIntent().getStringArrayListExtra("event_names");

        Log.d("DEBUG", "Получены даты событий: " + (eventDates != null ? eventDates.size() : 0));
        Log.d("DEBUG", "Получены названия событий: " + (eventNames != null ? eventNames.size() : 0));

        if (eventDates != null && eventNames != null) {
            processSavedEvents(eventDates, eventNames);
        }

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            long selectedDate = getDateInMillis(year, month, dayOfMonth);

            selectedDate -= selectedDate % (24 * 60 * 60 * 1000);

            if (eventDatesMap.containsKey(selectedDate)) {
                eventsTextView.setText(eventDatesMap.get(selectedDate));
                eventsTextView.setTextColor(eventColorsMap.get(selectedDate));
            } else {
                eventsTextView.setText("Нет событий на выбранную дату");
                eventsTextView.setTextColor(Color.BLACK);
            }
        });




        Toast.makeText(this, "События добавлены в календарь!", Toast.LENGTH_SHORT).show();
    }

    private void processSavedEvents(ArrayList<String> eventDates, ArrayList<String> eventNames) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (int i = 0; i < eventDates.size(); i++) {
            try {
                Date eventDate = format.parse(eventDates.get(i).substring(0, 10));
                if (eventDate != null) {
                    long eventTimeMillis = eventDate.getTime();
                    String theme = thirdActivityInstance.getTheme(new Event(eventNames.get(i), eventDates.get(i)));
                    int color = Color.parseColor(thirdActivityInstance.getEventColor(theme)); 

                    eventDatesMap.put(eventTimeMillis, "Событие: " + eventNames.get(i));
                    eventColorsMap.put(eventTimeMillis, color);

                    Log.d("DEBUG", "Обработано событие: " + eventNames.get(i));
                    calendarView.setDate(eventTimeMillis, true, true);
                }
            } catch (ParseException e) {
                Log.e("DEBUG", "Ошибка при парсинге даты события", e);
            }
        }
    }

    private long getDateInMillis(int year, int month, int dayOfMonth) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month, dayOfMonth, 0, 0, 0);
        return calendar.getTimeInMillis();
    }
}
