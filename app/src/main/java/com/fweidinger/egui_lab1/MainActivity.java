package com.fweidinger.egui_lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fweidinger.egui_lab1.helpers.TipDbHelper;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Member variable declarations
    /**
     * Name of the primary database.
     */
    final String databaseName = "historyDB.db";


    RadioGroup radioGroup;
    RadioButton radioButton;
    TipDbHelper tipDBHelper;

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
        tipDBHelper = new TipDbHelper(this);

        calcTipBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);


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
                tipDBHelper.insertEntry(tip, "Darmstadt", 1245151512);
                break;
            case R.id.historyButton:
                viewHistory();
                break;
            default:
                break;

        }
    }

    /**
     * This method is called when a RadioButton of buttonGroup is pressed. Distinguishes between specific RadioButton by ID and selects tip.
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
            default:
                Toast.makeText(this, "Invalid Action", Toast.LENGTH_SHORT).show();
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
     * viewHistory will switch to the HistoryActivity. Method is called when the use taps the history button.
     */
    public void viewHistory() {
        //TODO Go to secondary activity when button is pressed
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

}
