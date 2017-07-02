package com.example.android.mywork.Base.MVP;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.android.mywork.Base.UI.BaseFragment;
import com.example.android.mywork.Base.UI.BasePresenter;

public abstract class MvpFrgament<P extends BasePresenter> extends BaseFragment {
    protected P presenter;

    protected abstract P createPresenter();

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.dettachView();
        }
    }
}