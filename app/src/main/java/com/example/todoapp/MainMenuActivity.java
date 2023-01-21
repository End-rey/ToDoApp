package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.adapter.ToDoAdapter;
import com.example.todoapp.dto.login.TokenResponse;
import com.example.todoapp.dto.token.RefreshTokenForm;
import com.example.todoapp.entity.ToDoItem;
import com.example.todoapp.sharedPreference.SaveSharedPreference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class MainMenuActivity extends AppCompatActivity {

    private TextView mainText;
    private Button logout;
    private RecyclerView toDoList;
    private Button loadToDoList;
    private Button refreshToken;
    private Button addToDoItem;
    private ProgressBar progressBar;

    private String accessToken;
    private ToDoAdapter toDoAdapter;

    private List<ToDoItem> allToDoItems = new ArrayList<>();

    private Context myContext;

    private void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgress(){
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showTextError(){
        mainText.setText("Check Internet connection!");
        mainText.setTextColor(Color.RED);
    }

    private void showTextFirst() {
        mainText.setTextColor(Color.DKGRAY);
        mainText.setText("Nice Face!");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        myContext = getApplicationContext();

        logout = findViewById(R.id.b_main_logout);
        toDoList = findViewById(R.id.rv_main_list);
        refreshToken = findViewById(R.id.b_main_refresh_token);
        loadToDoList = findViewById(R.id.b_main_load_todo);
        progressBar = findViewById(R.id.pb_main_menu_loading);
        addToDoItem = findViewById(R.id.b_main_add_todo);
        mainText = findViewById(R.id.tv_main_text);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        toDoList.setLayoutManager(layoutManager);
        toDoList.setHasFixedSize(true);

        if(SaveSharedPreference.getPrefUserName(myContext).length()==0) {
            Intent intent = new Intent(myContext, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

//        if start activity from login page
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            accessToken = intent.getStringExtra(Intent.EXTRA_TEXT);
        }

//        refresh token button
        refreshToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
                Toast.makeText(myContext, SaveSharedPreference.getPrefToken(myContext) + "\n" + accessToken, Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSharedPreference.clearData(myContext);
                startActivity(new Intent(myContext, LoginActivity.class));
            }
        });

        loadToDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllToDoItems();
            }
        });

        addToDoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDoItem toDoItem = new ToDoItem();
                toDoItem.setDescription("buy 1");

                addNewToDoItem(toDoItem);
            }
        });

        getAllToDoItems();

        toDoAdapter = new ToDoAdapter(allToDoItems.size(), myContext, allToDoItems);
        toDoList.setAdapter(toDoAdapter);


        Toast.makeText(myContext, SaveSharedPreference.getPrefUserName(myContext), Toast.LENGTH_SHORT).show();
    }

    private void refresh(){
        showProgress();

        String refreshToken = SaveSharedPreference.getPrefToken(myContext);

        RefreshTokenForm refreshTokenForm = new RefreshTokenForm();
        refreshTokenForm.setRefreshToken(refreshToken);

        App.getApi().refreshToken(refreshTokenForm).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    showTextFirst();
                    accessToken = response.body().getAccessToken();

                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                showTextError();
                hideProgress();
            }
        });




    }

    private void refreshAndGetAllToDoItems() {
        showProgress();

        String refreshToken = SaveSharedPreference.getPrefToken(myContext);

        RefreshTokenForm refreshTokenForm = new RefreshTokenForm();
        refreshTokenForm.setRefreshToken(refreshToken);

        App.getApi().refreshToken(refreshTokenForm).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    showTextFirst();

                    accessToken = response.body().getAccessToken();

                    getAllToDoItems();
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                showTextError();
                hideProgress();
            }
        });
    }

    private void getAllToDoItems(){
        App.getApi().getToDoList("Bearer " + accessToken).enqueue(new Callback<List<ToDoItem>>() {
            @Override
            public void onResponse(Call<List<ToDoItem>> call, Response<List<ToDoItem>> response) {
                if (response.isSuccessful()){
                    showTextFirst();
                    allToDoItems = response.body();
                    if(allToDoItems!=null) {
                        toDoAdapter = new ToDoAdapter(allToDoItems.size(), myContext, allToDoItems);
                        toDoList.setAdapter(toDoAdapter);
                    }
                } else {
                    refreshAndGetAllToDoItems();
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<ToDoItem>> call, Throwable t) {
                showTextError();
                hideProgress();
            }
        });
    }

//    @SuppressLint("CheckResult")
//    private void addNewToDoItem(ToDoItem toDoItem){
//        showProgress();
//
//        Observable<ToDoItem> itemObservable = App.getApi().addNewToDoItem("Bearer " + accessToken, toDoItem);
//
//        itemObservable.subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.single())
//                .subscribe(this::handleResult, this::handleError);
//
//    }
//
//    private void handleError(Throwable throwable) {
//        throwable.printStackTrace();
//
//        hideProgress();
//    }
//
//
//    private void handleResult(ToDoItem toDoItem) {
//        allToDoItems.add(toDoItem);
//
//        toDoAdapter.notifyItemChanged(allToDoItems.indexOf(toDoItem));
//
//        Toast.makeText(myContext, "Добавлен новый элемент в список", Toast.LENGTH_SHORT).show();
//
//        hideProgress();
//    }

    private void addNewToDoItem(ToDoItem toDoItem){
        showProgress();

        App.getApi()
                .addNewToDoItem("Bearer " + accessToken, toDoItem).enqueue(new Callback<ToDoItem>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<ToDoItem> call, Response<ToDoItem> response) {
                if (response.isSuccessful()) {
                    allToDoItems.add(response.body());

                    toDoAdapter.notifyDataSetChanged();

                    Toast.makeText(myContext, "Добавлен новый элемент в список", Toast.LENGTH_SHORT).show();
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<ToDoItem> call, Throwable t) {
                Toast.makeText(myContext, "Не работает ничего =(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}