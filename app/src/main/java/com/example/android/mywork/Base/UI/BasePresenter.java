package com.example.android.mywork.Base.UI;

import android.util.Log;

public class BasePresenter<V> {
    public V view;

    public void attachView(V view) {
        this.view = view;
    }

    public void dettachView() {
        this.view = null;
    }
}
