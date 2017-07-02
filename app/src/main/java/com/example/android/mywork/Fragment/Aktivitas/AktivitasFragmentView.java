package com.example.android.mywork.Fragment.Aktivitas;

import android.content.ContentValues;

import com.example.android.mywork.Model.DataAktivitas;

interface AktivitasFragmentView {

    void onSubmitData(DataAktivitas dataAktivitas, ContentValues contentValues);

    void onSuccessSubmit(String message);

    void onFailedSubmit(String message);

}
