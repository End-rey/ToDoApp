package com.example.todoapp.dto.token;

public class RefreshTokenForm {
    private String refreshToken;

    public RefreshTokenForm(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshTokenForm() {
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
