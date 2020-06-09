package com.fweidinger.egui_lab1.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fweidinger.egui_lab1.R;
import com.fweidinger.egui_lab1.models.ModelTipHistory;

import java.util.Locale;

/**
 *
 */
public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {

    private Context context;
    private Cursor cursor;

    /**
     * @param context
     * @param cursor  the cursor that will be used to extract data from the database
     */
    public TipAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    class TipViewHolder extends RecyclerView.ViewHolder {
        TextView textTip;
        TextView textDate;
        TextView textLocation;

        TipViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_history, parent, false);
        return new TipViewHolder(view);
    }

    /**
     *
     * @param holder the viewHolder that holds the views of a single item inside the RecyclerView
     * @param position the position of the cursor to determine the position of the item. Used to check if the cursor position is valid.
     */
    @Override
    public void onBindViewHolder(@NonNull TipViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String location = cursor.getString(cursor.getColumnIndex(ModelTipHistory.TipEntry.COLUMN_NAME_LOCATION));
        String date = cursor.getString(cursor.getColumnIndex(ModelTipHistory.TipEntry.COLUMN_NAME_DATE));
        float tip = cursor.getFloat(cursor.getColumnIndex(ModelTipHistory.TipEntry.COLUMN_NAME_TIP));

        holder.textTip=holder.itemView.findViewById(R.id.column_tip);
        holder.textTip.setText(String.format(Locale.GERMANY, "%.2f", tip));
        holder.textDate=holder.itemView.findViewById(R.id.column_date);
        holder.textDate.setText(date);
        holder.textLocation=holder.itemView.findViewById(R.id.column_location);
        holder.textLocation.setText(location);

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
