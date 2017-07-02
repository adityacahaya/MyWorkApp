package com.example.android.mywork.Fragment.Aktivitas;

import android.content.ContentValues;
import android.net.Uri;

import com.example.android.mywork.Base.UI.BasePresenter;
import com.example.android.mywork.Database.WorkContract;
import com.example.android.mywork.Model.DataAktivitas;

class AktivitasFragmentPresenter extends BasePresenter<AktivitasFragmentView> {

    AktivitasFragmentPresenter(AktivitasFragmentView view) {
        super.attachView(view);
    }

    public void submitData(String nama, String waktu, String aktivitas, String kategori,
                           String note, String idUser, String latitude, String longitude){

        DataAktivitas mDataAktivitas = new DataAktivitas();
        mDataAktivitas.setNameUser(nama);
        mDataAktivitas.setDate(waktu);
        mDataAktivitas.setAktivitas(aktivitas);
        mDataAktivitas.setKategori(kategori);
        mDataAktivitas.setNote(note);
        mDataAktivitas.setIdUser(idUser);
        mDataAktivitas.setLatitude(latitude);
        mDataAktivitas.setLongitude(longitude);

        ContentValues cv = new ContentValues();
        cv.put(WorkContract.WorkEntry.COLUMN_NAME_USER, nama);
        cv.put(WorkContract.WorkEntry.COLUMN_DATE, waktu);
        cv.put(WorkContract.WorkEntry.COLUMN_AKTIVITAS, aktivitas);
        cv.put(WorkContract.WorkEntry.COLUMN_KATEGORI, kategori);
        cv.put(WorkContract.WorkEntry.COLUMN_NOTE, note);
        cv.put(WorkContract.WorkEntry.COLUMN_ID_USER, idUser);
        cv.put(WorkContract.WorkEntry.COLUMN_LATITUDE, latitude);
        cv.put(WorkContract.WorkEntry.COLUMN_LONGITUDE, longitude);

        view.onSubmitData(mDataAktivitas,cv);
    }

    public void checkSubmit(Uri uri){
        if (uri != null){
            String message = "Insert Aktivitas Sukses";
            view.onSuccessSubmit(message);
        }else{
            String message = "Insert Aktivitas Gagal";
            view.onFailedSubmit(message);
        }
    }

}
