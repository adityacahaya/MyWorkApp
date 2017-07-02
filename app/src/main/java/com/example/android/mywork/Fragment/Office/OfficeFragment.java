package com.example.android.mywork.Fragment.Office;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.android.mywork.Base.MVP.MvpFrgament;
import com.example.android.mywork.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfficeFragment extends MvpFrgament<OfficeFragmentPresenter> implements
        OfficeFragmentView{

    @BindView(R.id.web_view) WebView view;
    @BindView(R.id.btn_backward) Button btnBackward;
    @BindView(R.id.btn_forward) Button btnForward;
    @BindView(R.id.btn_refresh) Button btnRefresh;

    @Override
    protected OfficeFragmentPresenter createPresenter() {
        return new OfficeFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_office, container, false);
        ButterKnife.bind(this,rootView);

        String url = "https://twitter.com/";
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        view.getSettings().setBuiltInZoomControls(true);
        view.getSettings().setSupportZoom(true);
        view.setWebViewClient(new MyBrowser());

        return rootView;
    }

    @OnClick(R.id.btn_backward)
    public void onClickBackward() {
        presenter.onBackWeb(view);
    }

    @OnClick(R.id.btn_forward)
    public void onClicForward() {
        presenter.onForwardWeb(view);
    }

    @OnClick(R.id.btn_refresh)
    public void onClicRefresh() {
        presenter.onRefreshWeb(view);
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public  boolean shouldOverrideUrlLoading(WebView view, String url ){
            view.loadUrl(url);
            return true;
        }
    }


    // method dari MVP
    @Override
    public void onBackWeb(WebView view) {
        if (view.canGoBack()) {
            view.goBack();
        }
    }

    @Override
    public void onRefreshWeb(WebView view) {
        view.reload();
    }

    @Override
    public void onForwardWeb(WebView view) {
        if (view.canGoForward()){
            view.goForward();
        }
    }
}
