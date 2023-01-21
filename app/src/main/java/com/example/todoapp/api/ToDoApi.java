package com.example.todoapp.api;

import com.example.todoapp.dto.login.LoginForm;
import com.example.todoapp.dto.login.TokenResponse;
import com.example.todoapp.dto.token.RefreshTokenForm;
import com.example.todoapp.entity.ToDoItem;
import com.example.todoapp.entity.User;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ToDoApi {
    @POST("/api/auth/register")
    Call<User> registerUser(@Body User user);

    @POST("/api/auth/signin")
    Call<TokenResponse> loginUser(@Body LoginForm loginForm);

    @POST("/api/auth/refreshtoken")
    Call<TokenResponse> refreshToken(@Body RefreshTokenForm refreshTokenForm);

    @GET("/todos")
    Call<List<ToDoItem>> getToDoList(@Header("Authorization") String token);

    @POST("/todos")
    Call<ToDoItem> addNewToDoItem(@Header("Authorization") String token, @Body ToDoItem toDoItem);
}
