package com.example.eventscalendar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin, btnRegister;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(login, password);
            }
        });

        btnRegister.setOnClickListener(v -> {
            String login = etLogin.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(login, password);
            }
        });
    }

    private void loginUser(String login, String password) {
        String savedLogin = sharedPreferences.getString("login", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (login.equals(savedLogin) && password.equals(savedPassword)) {
            Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(String login, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", login);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }
}
