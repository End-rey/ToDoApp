package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.todoapp.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText firstName;
    private Button register;
    private ProgressBar loading;

    private void showLoading(){
        loading.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.et_register_username);
        password = findViewById(R.id.et_register_password);
        register = findViewById(R.id.b_register_register);
        firstName = findViewById(R.id.et_register_first_name);
        loading = findViewById(R.id.pb_register_loading);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();

                User registerUser = new User();
                registerUser.setUsername(username.getText().toString());
                registerUser.setFirstName(firstName.getText().toString());
                registerUser.setPassword(password.getText().toString());

                Call<User> call = App.getApi().registerUser(registerUser);

                call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    String info = "";
                                    User user = response.body();

                                    info += user.getUsername() + "\n";
                                    info += user.getFirstName() + "\n";

                                    hideLoading();

                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    intent.putExtra(Intent.EXTRA_TEXT, info);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "User with this username already exist", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                            }
                        });
            }
        });
    }

}