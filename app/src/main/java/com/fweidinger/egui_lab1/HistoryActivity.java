package com.fweidinger.egui_lab1;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fweidinger.egui_lab1.adapters.TipAdapter;
import com.fweidinger.egui_lab1.helpers.DatabaseHelper;

/**
 * This Activity will display the contents of the Tip database.
 */
public class HistoryActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private TipAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        databaseHelper = new DatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.tipList);
        mAdapter = new TipAdapter(getApplicationContext(),databaseHelper.getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(mAdapter);
    }

}