package com.example.todoapp;

import android.app.Application;

import com.example.todoapp.api.ToDoApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ToDoApi toDoApi;
    private Retrofit retrofit;

    private static final String TODO_API_BASE_URL = "https://todo-list-app-andrey.herokuapp.com";

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(TODO_API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        toDoApi = retrofit.create(ToDoApi.class);
    }

    public static ToDoApi getApi(){
        return toDoApi;
    }
}
