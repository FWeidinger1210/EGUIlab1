package com.fweidinger.egui_lab1.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.fweidinger.egui_lab1.R;

public class TipCursorAdapter extends CursorAdapter {

    public TipCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.content_history, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ListView listView = view.findViewById(R.id.ListView_History);
        

    }
}
