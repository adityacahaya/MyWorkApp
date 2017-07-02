package com.example.android.mywork.Fragment.User;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.mywork.Adapter.CustomCursorAdapter;
import com.example.android.mywork.Base.MVP.MvpFrgament;
import com.example.android.mywork.Database.WorkContract;
import com.example.android.mywork.R;
import com.firebase.ui.auth.AuthUI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserFragment extends MvpFrgament<UserFragmentPresenter> implements
        LoaderManager.LoaderCallbacks<Cursor>,
        UserFragmentView{

    @BindView(R.id.btn_revoke_access_Web) Button mRevokeButton;
    @BindView(R.id.recyclerViewTasks) RecyclerView mRecyclerView;

    private static final int TASK_LOADER_ID = 0;
    private CustomCursorAdapter mAdapter;

    @Override
    protected UserFragmentPresenter createPresenter() {
        return new UserFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this,rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CustomCursorAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        getActivity().getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    @OnClick(R.id.btn_revoke_access_Web)
    public void onClickSignOut() {
        presenter.onSignOut(getActivity());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(getContext()) {

            Cursor mTaskData = null;

            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    deliverResult(mTaskData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try{
                    return getActivity().getContentResolver().query(
                            WorkContract.WorkEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);
                }catch(Exception e){
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


    // method dari MVP
    @Override
    public void onSignOut(Activity activity) {
        AuthUI.getInstance().signOut(activity);
        LinearLayout layoutWithInternetConnection = (LinearLayout) activity.findViewById(R.id.layout_with_internet_connection);
        TextView layoutWithoutInternetConnection = (TextView) activity.findViewById(R.id.layout_without_internet_connection);
        layoutWithoutInternetConnection.setVisibility(View.VISIBLE);
        layoutWithInternetConnection.setVisibility(View.INVISIBLE);
    }
}
