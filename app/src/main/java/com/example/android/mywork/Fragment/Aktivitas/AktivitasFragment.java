package com.example.android.mywork.Fragment.Aktivitas;

import android.Manifest;
import android.content.ContentValues;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.pm.PackageManager;
import android.location.Location;

import com.example.android.mywork.Base.MVP.MvpFrgament;
import com.google.android.gms.location.LocationListener;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mywork.Model.DataAktivitas;
import com.example.android.mywork.Database.WorkContract;
import com.example.android.mywork.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

public class AktivitasFragment extends MvpFrgament<AktivitasFragmentPresenter> implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        AktivitasFragmentView{

    @BindView(R.id.tv_nama) TextView textViewUsername;
    @BindView(R.id.tv_waktu) TextView textViewWaktu;
    @BindView(R.id.ed_aktivitas) EditText editTextAktivitas;
    @BindView(R.id.sp_kategori) Spinner spinnerKategori;
    @BindView(R.id.ed_note) EditText editTextNote;
    @BindView(R.id.btn_submit) Button buttonSubmit;

    private String mNama;
    private String mWaktu;
    private String mAktivitas;
    private String mKategori;
    private String mNote;
    private String mIdUser;
    private String mLatitude;
    private String mLongitude;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private Calendar mCalendar;
    private SimpleDateFormat mSimpleDateFormat;

    private Uri mUri;

    // untuk special character handling
    private String blockCharacterSet = "~!@#$%^&*_-+={[}]|:;'?/><";
    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

    public AktivitasFragment(String username, String id) {
        mNama = username;
        mIdUser = id;
    }

    @Override
    protected AktivitasFragmentPresenter createPresenter() {
        return new AktivitasFragmentPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);
        ButterKnife.bind(this,rootView);

        textViewUsername.setText(mNama);
        getRealTime();
        setupSpinner();

        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        editTextAktivitas.setFilters(new InputFilter[]{filter});
        editTextNote.setFilters(new InputFilter[]{filter});

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("aktivitas");

        return rootView;
    }

    private void setupSpinner() {
        ArrayAdapter supplierSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.array_kategori, android.R.layout.simple_spinner_item);
        supplierSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerKategori.setAdapter(supplierSpinnerAdapter);
    }

    @OnItemSelected(R.id.sp_kategori)
    void onItemSelected(int position){
        String selection = (String) spinnerKategori.getItemAtPosition(position);
        if (!TextUtils.isEmpty(selection)) {
            if (selection.equals(getString(R.string.work))) {
                mKategori = getString(R.string.work);
            } else if (selection.equals(getString(R.string.play))) {
                mKategori = getString(R.string.play);
            } else {
                mKategori = getString(R.string.learn);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        mAktivitas = editTextAktivitas.getText().toString();
        mNote = editTextNote.getText().toString();
        if (!mAktivitas.equals("")) {
            if (mNote.equals("")) {
                mNote = "-";
            }
            getRealTime();
            presenter.submitData(mNama,mWaktu,mAktivitas,mKategori,mNote,mIdUser,mLatitude,
                mLongitude);
            presenter.checkSubmit(mUri);
        } else {
            Toast.makeText(getContext(), "Aktivitas Harus di Isi", Toast.LENGTH_SHORT).show();
        }
        resetUI();
    }

    public void getRealTime(){
        mCalendar = Calendar.getInstance();
        mSimpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        mWaktu = mSimpleDateFormat.format(mCalendar.getTime());
        textViewWaktu.setText(mWaktu);
    }

    public void resetUI(){
        getRealTime();
        setupSpinner();
        editTextAktivitas.setText("");
        editTextNote.setText("");
    }

    // Google API Method
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {    }

    @Override
    public void onLocationChanged(Location location) {
        mLongitude = Double.toString(location.getLongitude());
        mLatitude = Double.toString(location.getLatitude());
    }


    // method dari MVP
    @Override
    public void onSubmitData(DataAktivitas dataAktivitas, ContentValues contentValues) {
        mDatabaseReference.push().setValue(dataAktivitas);
        mUri = getActivity().getContentResolver().insert(WorkContract.WorkEntry.CONTENT_URI, contentValues);
    }

    @Override
    public void onSuccessSubmit(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedSubmit(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
