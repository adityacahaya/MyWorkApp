package com.example.android.mywork.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class WorkContract {

    public static final String  AUTHORITY        = "com.example.android.mywork";
    public static final Uri     BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String  PATH_TASKS       = "worklist";

    public static final class WorkEntry implements BaseColumns {
        public static final Uri    CONTENT_URI      = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        public static final String TABLE_NAME       = "worklist";
        public static final String COLUMN_NAME_USER = "name_user";
        public static final String COLUMN_DATE      = "date";
        public static final String COLUMN_AKTIVITAS = "aktivitas";
        public static final String COLUMN_KATEGORI  = "kategori";
        public static final String COLUMN_NOTE      = "note";
        public static final String COLUMN_ID_USER   = "id_user";
        public static final String COLUMN_LATITUDE  = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
    }

}
