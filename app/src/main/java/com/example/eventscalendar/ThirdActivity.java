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

import com.example.eventscalendar.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ThirdActivity extends Fragment {

    private LinearLayout eventsContainer;
    private TextView nextPageTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_list_fragment, container, false);

        eventsContainer = view.findViewById(R.id.eventsContainer);
        nextPageTextView = view.findViewById(R.id.nextPageTextView);

        // Заполняем список событий из API
        populateEvents();

        // Обработка клика на "Следующая страница"
        nextPageTextView.setOnClickListener(v -> {
            //Реализовать переход на следующую страницу
        });

        return view;
    }

    private void populateEvents() {
        List<Event> events = new ArrayList<>();
        events.add(new Event("Концерт группы 'Кино'", "https://example.com/event1"));
        events.add(new Event("Выставка 'Импрессионисты'", "https://example.com/event2"));
        events.add(new Event("Матч 'Спартак' - 'Зенит'", "https://example.com/event3"));
        events.add(new Event("Фестиваль 'Круг света'", "https://example.com/event4"));
        events.add(new Event("Спектакль 'Чайка'", "https://example.com/event5"));
        events.add(new Event("Конференция 'Mobile Dev'", "https://example.com/event6"));

        eventsContainer.removeAllViews();

        for (Event event : events) {
            addEventView(event, eventsContainer);
        }
    }

    private void addEventView(Event event, LinearLayout container) {
        // Создаем разметку для одного события
        View eventView = LayoutInflater.from(getContext())
                .inflate(R.layout.event_item_layout, container, false);

        TextView eventNameTextView = eventView.findViewById(R.id.eventNameTextView);
        Button addToCalendarButton = eventView.findViewById(R.id.addToCalendarButton);

        eventNameTextView.setText(event.getName());
        eventNameTextView.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));

        eventNameTextView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(event.getUrl()));
            startActivity(browserIntent);
        });

        addToCalendarButton.setOnClickListener(v -> {
            addEventToCalendar(event);
        });

        container.addView(eventView);
    }

    private void addEventToCalendar(Event event) {
        // Создаем новое событие для календаря
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        String eventDate = sdf.format(new Date(System.currentTimeMillis() + 86400000)); // Дата через день

        CalendarEventsFragment.CalendarEvent calendarEvent =
                new CalendarEventsFragment.CalendarEvent(
                        event.getName(),
                        eventDate,
                        "Место не указано",
                        event.getUrl()
                );

        // Здесь должна быть логика сохранения в базу данных
        // Временно сохраняем в списке
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).addToCalendar(calendarEvent);
        }

        Toast.makeText(getContext(), "Добавлено в календарь: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    private static class Event {
        private String name;
        private String url;

        public Event(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}
