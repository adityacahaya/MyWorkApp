package com.example.android.mywork.Feature;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mywork.Adapter.WorkFragmentPagerAdapter;
import com.example.android.mywork.Base.MVP.MvpActivity;
import com.example.android.mywork.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends MvpActivity<HomePresenter> implements HomeView{

    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;
    @BindView(R.id.layout_with_internet_connection) LinearLayout layoutWithInternetConnection;
    @BindView(R.id.layout_without_internet_connection) TextView layoutWithoutInternetConnection;

    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Toast mToast;

    private String mUsername;
    private String mId;

    private WorkFragmentPagerAdapter mWorkFragmentPagerAdapter;

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_work_white_24dp);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mToast = new Toast(this);
        tabLayout.setupWithViewPager(viewPager);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    mUsername = user.getDisplayName();
                    mId = user.getUid();
                    presenter.onSignedIn(mUsername,mId);
                } else {
                    startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                        ))
                        .build(),
                        RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                presenter.signInSucces("Sign In Success");
            }else if (resultCode == RESULT_CANCELED){
                presenter.signInCancel("Sign In Cancel");
            }else{
                layoutWithoutInternetConnection.setVisibility(View.VISIBLE);
                layoutWithInternetConnection.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        viewPager.setAdapter(null);
    }

    // methdd using MVP
    @Override
    public void onSignedInInitialize(String username, String id) {
        layoutWithoutInternetConnection.setVisibility(View.INVISIBLE);
        layoutWithInternetConnection.setVisibility(View.VISIBLE);

        mWorkFragmentPagerAdapter = new WorkFragmentPagerAdapter(getSupportFragmentManager(), this, username, id);
        viewPager.setAdapter(mWorkFragmentPagerAdapter);
    }

    @Override
    public void signInSuccess(String message) {
        mToast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void signInCancel(String message) {
        mToast.makeText(this,message,Toast.LENGTH_SHORT).show();
        finish();
    }
}
