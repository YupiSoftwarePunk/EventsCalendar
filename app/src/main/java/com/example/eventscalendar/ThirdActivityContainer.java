package com.example.eventscalendar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ThirdActivityContainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_container);

        if (savedInstanceState == null) {
            ThirdActivity fragment = new ThirdActivity();

            Bundle bundle = new Bundle();
            bundle.putSerializable("events_list", getIntent().getSerializableExtra("events_list"));
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
        }
    }
}
