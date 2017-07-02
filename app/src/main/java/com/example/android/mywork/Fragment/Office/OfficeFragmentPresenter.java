package com.example.android.mywork.Fragment.Office;

import android.webkit.WebView;

import com.example.android.mywork.Base.UI.BasePresenter;

class OfficeFragmentPresenter extends BasePresenter<OfficeFragmentView> {

    OfficeFragmentPresenter(OfficeFragmentView view) {
        super.attachView(view);
    }

    public void onBackWeb(WebView webView){
        view.onBackWeb(webView);
    }

    public void onRefreshWeb(WebView webView){
        view.onRefreshWeb(webView);
    }

    public void onForwardWeb(WebView webView){
        view.onForwardWeb(webView);
    }

}
