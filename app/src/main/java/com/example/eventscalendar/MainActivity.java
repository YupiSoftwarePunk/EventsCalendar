package com.example.eventscalendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventscalendar.R;

public class MainActivity extends AppCompatActivity {

    private EditText etEventTheme;
    private Button btnSearchEvents;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEventTheme = findViewById(R.id.etEventTheme);
        btnSearchEvents = findViewById(R.id.btnSearchEvents);

    }


    public void addToCalendar(CalendarEventsFragment.CalendarEvent calendarEvent) {

    }
}

