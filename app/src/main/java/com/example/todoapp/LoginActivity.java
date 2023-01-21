package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.todoapp.dto.login.LoginForm;
import com.example.todoapp.dto.login.TokenResponse;
import com.example.todoapp.sharedPreference.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button signIn;
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
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        signIn = findViewById(R.id.b_login);
        register = findViewById(R.id.b_register);
        loading = findViewById(R.id.pb_login_loading);

        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)) {
            Toast.makeText(getApplicationContext(), intent.getStringExtra(Intent.EXTRA_TEXT), Toast.LENGTH_SHORT).show();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().equals("") && !password.getText().toString().equals("")){
                    showLoading();

                    LoginForm loginForm = new LoginForm();
                    loginForm.setUsername(username.getText().toString());
                    loginForm.setPassword(password.getText().toString());

                    App.getApi()
                            .loginUser(loginForm)
                            .enqueue(new Callback<TokenResponse>() {
                                @Override
                                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                    if(response.isSuccessful()){
                                        TokenResponse body = response.body();

                                        String accessToken = body.getAccessToken();
                                        String refreshToken = body.getRefreshToken();

                                        SaveSharedPreference.setPrefUserName(getApplicationContext(), body.getUsername());
                                        SaveSharedPreference.setPrefToken(getApplicationContext(), refreshToken);

                                        Intent mainIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                                        mainIntent.putExtra(Intent.EXTRA_TEXT, accessToken);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(mainIntent);
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                                    }
                                    hideLoading();
                                }

                                @Override
                                public void onFailure(Call<TokenResponse> call, Throwable t) {

                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Write username and password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}