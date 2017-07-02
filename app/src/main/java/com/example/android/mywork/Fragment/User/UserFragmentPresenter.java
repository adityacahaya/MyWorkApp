package com.example.android.mywork.Fragment.User;

import android.app.Activity;

import com.example.android.mywork.Base.UI.BasePresenter;

class UserFragmentPresenter extends BasePresenter<UserFragmentView> {

    UserFragmentPresenter(UserFragmentView view) {
        super.attachView(view);
    }

    public void onSignOut(Activity activity){
        view.onSignOut(activity);
    }
}
