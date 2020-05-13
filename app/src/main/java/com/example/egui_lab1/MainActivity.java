package com.example.egui_lab1;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Member variable declarations
    /**
     * Name of the primary database.
     */
    final String databaseName = "historyDB.db";


    RadioGroup radioGroup;
    RadioButton radioButton;

    private float selectedTip;

    public float getSelectedTip() {
        return selectedTip;
    }

    public void setSelectedTip(float selectedTip) {
        this.selectedTip = selectedTip;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calcTipBtn = findViewById(R.id.calcTipBtn);
        Button historyBtn = findViewById(R.id.historyButton);
        radioGroup = findViewById(R.id.radioGroup);

        calcTipBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);

        if (isFirstAppStart()) {
            createDatabase();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.calcTipBtn:
                EditText billingAmountView = findViewById(R.id.inputAmount);
                TextView totalResultView = findViewById(R.id.SumAmountView);
                TextView tipResultView = findViewById(R.id.TipAmountView);

                float bllngAmnt = Float.parseFloat(billingAmountView.getText().toString());
                float tip = bllngAmnt / getSelectedTip();
                float totalSumResult = tip + bllngAmnt;
                totalResultView.setText(String.format(Locale.US, "%.2f", totalSumResult));
                tipResultView.setText(String.format(Locale.US, "%.2f", tip));
                break;
            case R.id.historyButton:
                // TODO: Waiting for method implementation viewHistory()
                break;

        }
    }

    /**
     * This method is called when a radioButton of ButtonGroup is pressed. Distinguishes specific RadioButton by ID and selects tip.
     *
     * @param v The view
     */
    public void checkButton(View v) {

        int radioID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioID);
        switch (radioButton.getId()) {
            case R.id.radio_one:
                Toast.makeText(this, "Selected Tip: cheap - 5%", Toast.LENGTH_SHORT).show();
                this.setSelectedTip(20);
                break;
            case R.id.radio_two:
                Toast.makeText(this, "Selected Tip: normal - 10%", Toast.LENGTH_SHORT).show();
                this.setSelectedTip(10);
                break;
            case R.id.radio_three:
                Toast.makeText(this, "Selected Tip: generous 20%", Toast.LENGTH_SHORT).show();
                this.setSelectedTip(5);
                break;
        }
    }

    /**
     * This method determines if the app is run for the first time.
     *
     * @return true: first start; false : recurring start
     */
    public boolean isFirstAppStart() {
        boolean first = false;
        SharedPreferences sharedPreferences = getSharedPreferences("firstStart", MODE_PRIVATE);
        SharedPreferences.Editor shrdPrfEditor = sharedPreferences.edit();
        if (!sharedPreferences.getBoolean("firstStart", false)) {
            first = true;
            shrdPrfEditor.putBoolean("firstStart", true);
            shrdPrfEditor.commit();
        }
        return first;
    }

    /**
     * Creates the 'History' database Columns: date, location, tip.
     */
    public void createDatabase() {
        SQLiteDatabase databaseUser = getBaseContext().openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
        databaseUser.execSQL("CREATE TABLE history(date TEXT,location TEXT, tip FLOAT)");
        databaseUser.close();
    }



    public void viewHistory(View view) {
        //TODO Go to secondary activity when button is pressed
        //Intent intent = new Intent(this,DisplayHistoryActivity.class);
    }

}
