package com.example.android.mywork.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android.mywork.Fragment.Aktivitas.AktivitasFragment;
import com.example.android.mywork.Fragment.Office.OfficeFragment;
import com.example.android.mywork.Fragment.User.UserFragment;
import com.example.android.mywork.R;

public class WorkFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private String mTabTitles[];

    private String mUsername;
    private String mId;

    public WorkFragmentPagerAdapter(FragmentManager fm, Context context,
        String username, String id) {
        super(fm);
        mContext = context;
        mTabTitles = new String[] {
            mContext.getString(R.string.activity),
            mContext.getString(R.string.office),
            mContext.getString(R.string.user)};
        mUsername = username;
        mId = id;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AktivitasFragment(mUsername,mId);
        } else if (position == 1){
            return new OfficeFragment();
        } else {
            return new UserFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
