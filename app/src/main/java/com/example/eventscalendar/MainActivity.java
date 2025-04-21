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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEventTheme = findViewById(R.id.etEventTheme);
        btnSearchEvents = findViewById(R.id.btnSearchEvents);

        btnSearchEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theme = etEventTheme.getText().toString().trim();

                if (theme.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Введите тему события", Toast.LENGTH_SHORT).show();
                } else {
                    searchEvents(theme);
                }
            }
        });


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, new EventListFragment());
        transaction.commit();
    }

    private void searchEvents(String theme) {
        //  Реализовать парсинг событий по теме с различных сайтов
        Toast.makeText(this, "Поиск событий по теме: " + theme, Toast.LENGTH_SHORT).show();
    }
}