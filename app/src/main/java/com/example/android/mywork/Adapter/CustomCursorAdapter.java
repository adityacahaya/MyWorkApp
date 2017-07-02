package com.example.android.mywork.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mywork.Database.WorkContract;
import com.example.android.mywork.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomCursorAdapter extends
    RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_work_recycleview, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        int idIndex = mCursor.getColumnIndex(WorkContract.WorkEntry._ID);
        int nameUserIndex = mCursor.getColumnIndex(WorkContract.WorkEntry.COLUMN_NAME_USER);
        int dateIndex = mCursor.getColumnIndex(WorkContract.WorkEntry.COLUMN_DATE);
        int activityIndex = mCursor.getColumnIndex(WorkContract.WorkEntry.COLUMN_AKTIVITAS);
        int kategoriIndex = mCursor.getColumnIndex(WorkContract.WorkEntry.COLUMN_KATEGORI);
        int noteIndex = mCursor.getColumnIndex(WorkContract.WorkEntry.COLUMN_NOTE);

        mCursor.moveToPosition(position);

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String nameUser = mCursor.getString(nameUserIndex);
        String date = mCursor.getString(dateIndex);
        String activity = mCursor.getString(activityIndex);
        String kategori = mCursor.getString(kategoriIndex);
        String note = mCursor.getString(noteIndex);

        //Set values
        holder.itemView.setTag(id);
        holder.taskDescriptionView.setText(date + " : " + nameUser + " did " + kategori
            + " at " + activity + " with note " + note);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    public Cursor swapCursor(Cursor c) {
        if (mCursor == c) {
            return null;
        }
        Cursor temp = mCursor;
        this.mCursor = c;

        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_rv_time)TextView taskDescriptionView;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}