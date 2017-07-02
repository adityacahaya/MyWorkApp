package com.example.android.mywork.Feature;

import com.example.android.mywork.Base.UI.BasePresenter;

class HomePresenter extends BasePresenter<HomeView> {

    HomePresenter(HomeView view) {
        super.attachView(view);
    }

    public void onSignedIn(String username, String id){
        view.onSignedInInitialize(username,id);
    }

    public void signInSucces(String message){view.signInSuccess(message);}

    public void signInCancel(String message){view.signInCancel(message);}
}
