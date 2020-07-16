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
import com.fweidinger.egui_lab1.data.models.ModelTipHistory;

import java.util.Locale;

/**
 * The TipAdapter manages a cursor object to read data from the SQLiteDatabase
 * and serves as a  adapter to a RecyclerView to provide child views.
 * This class inherits from the Recycler.Adapter abstract class.
 *
 */
public class TipAdapter extends RecyclerView.Adapter<TipAdapter.TipViewHolder> {

    /**
     * The context of the application.
     */
    private Context context;
    /**
     * The cursor that will allow the TipAdapter to get information from the SQLiteDatabase.
     */
    private Cursor cursor;

    /**
     * Basic constructor
     * @param context the application context
     * @param cursor  the cursor that will be used to extract data from the database
     */
    public TipAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    /**
     * This class extends the RecyclerView.ViewHolder it holds the CHILD views, that will then be displayed in the RecyclerView.
     * Each ChildView can be understood as a single list item in the RecyclerView.
     */
    class TipViewHolder extends RecyclerView.ViewHolder {
        TextView textTip;
        TextView textDate;
        TextView textLocation;

        /**
         * Basic constructor.
         * @param itemView the ChildView
         */
        TipViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }

    /**
     * This method will be called on the Creation of the ViewHolder. It inflates the content_history layout that represents a displays a single
     * item inside the RecyclerView
     * @param parent The RecyclerView
     * @return  TipViewHolder
     */
    @NonNull
    @Override
    public TipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_history, parent, false);
        return new TipViewHolder(view);
    }


    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the RecyclerView.
     *
     * @param holder tipViewHolder as described above
     * @param position The position of the item within the adapter's data set.
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

    /**
     * Returns the total number of items in the data set held by the adapter. (from official Javadoc of super class)
     * @return the item count.
     */
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

}
