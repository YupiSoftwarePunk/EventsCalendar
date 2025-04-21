package com.example.eventscalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class FirstActivity extends AppCompatActivity {

    private EditText etLogin, etPassword;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);


        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etLogin.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(FirstActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    String enterLogin = "coolCode@gmail.com";
                    String enterPassword = "1234";
                    if (login == enterLogin && password == enterPassword) {

                    }
                    loginUser(login, password);
                }
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = etLogin.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(FirstActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(login, password);
                }
            }
        });
    }

    private void loginUser(String login, String password) {
        if (login.equals("test@example.com") && password.equals("123456")) {
            Toast.makeText(this, "Вход выполнен успешно", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(FirstActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser(String login, String password) {
        Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show();
        loginUser(login, password);
        startActivity(new Intent(FirstActivity.this, MainActivity.class));
        finish();
    }
}