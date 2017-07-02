package com.example.android.mywork.Base.MVP;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.android.mywork.Base.UI.BaseActivity;
import com.example.android.mywork.Base.UI.BasePresenter;

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dettachView();
        }
    }
}
