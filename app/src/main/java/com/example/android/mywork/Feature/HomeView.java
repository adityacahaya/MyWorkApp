package com.example.android.mywork.Feature;

interface HomeView {

    void onSignedInInitialize(String username, String id);

    void signInSuccess(String message);

    void signInCancel(String message);

}