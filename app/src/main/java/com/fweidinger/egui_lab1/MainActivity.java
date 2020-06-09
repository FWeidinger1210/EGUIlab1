package com.fweidinger.egui_lab1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fweidinger.egui_lab1.helpers.DatabaseHelper;
import com.fweidinger.egui_lab1.helpers.Formatter;

import java.time.Instant;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Name of the primary database.
     */
    final String databaseName = "historyDB.db";


    RadioGroup radioGroupQOS;
    RadioButton radioButtonQOS;
    RadioGroup radioGroupGEN;
    RadioButton radioButtonGEN;
    DatabaseHelper databaseHelper;

    private float qosValue;

    public float getQosValue() {
        return qosValue;
    }

    public void setQosValue(float qosValue) {
        this.qosValue = qosValue;
    }

    public float getGenerosityValue() {
        return generosityValue;
    }

    public void setGenerosityValue(float generosityValue) {
        this.generosityValue = generosityValue;
    }

    private float generosityValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calcTipBtn = findViewById(R.id.calcTipBtn);
        Button historyBtn = findViewById(R.id.historyButton);
        radioGroupQOS = findViewById(R.id.radioGroupQOS);
        radioGroupGEN = findViewById(R.id.radioGroupGenerosity);
        databaseHelper = new DatabaseHelper(this);

        calcTipBtn.setOnClickListener(this);
        historyBtn.setOnClickListener(this);
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.calcTipBtn:
                try {
                    EditText billingAmountView = findViewById(R.id.inputAmount);
                    TextView totalResultView = findViewById(R.id.SumAmountView);
                    TextView tipResultView = findViewById(R.id.TipAmountView);
                    EditText location = findViewById(R.id.location);
                    Instant instant = Instant.now();


                    float bllngAmnt = Float.parseFloat(billingAmountView.getText().toString());
                    float tip = bllngAmnt * (getGenerosityValue() * getQosValue()) / 100;
                    float totalSumResult = tip + bllngAmnt;
                    totalResultView.setText(String.format(Locale.GERMANY, "%.2f", totalSumResult));
                    tipResultView.setText(String.format(Locale.GERMANY, "%.2f", tip));
                    databaseHelper.insertEntry(tip, checkForEmptyInput(location.getText().toString()), Formatter.formatDate(instant));
                } catch (NumberFormatException ex) {
                    Toast.makeText(this, "Please enter the Billing Amount!", Toast.LENGTH_SHORT).show();
                }
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

        int radioID = radioGroupQOS.getCheckedRadioButtonId();
        radioButtonQOS = findViewById(radioID);
        switch (radioButtonQOS.getId()) {
            case R.id.radioQOS_ONE:
                Toast.makeText(this, "Selected Quality of Service: very poor", Toast.LENGTH_SHORT).show();
                this.setQosValue((float) 0.5);
                break;
            case R.id.radioQOS_TWO:
                Toast.makeText(this, "Selected Quality of Service: moderate", Toast.LENGTH_SHORT).show();
                this.setQosValue(1);
                break;
            case R.id.radioQOS_THREE:
                Toast.makeText(this, "Selected Quality of Service: excellent", Toast.LENGTH_SHORT).show();
                this.setQosValue(2);
                break;
            default:
                Toast.makeText(this, "Invalid Action", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void checkButtonGen(View view) {
        int radioID = radioGroupGEN.getCheckedRadioButtonId();
        radioButtonGEN = findViewById(radioID);
        switch (radioButtonGEN.getId()) {
            case R.id.radioGEN_ONE:
                Toast.makeText(this, "Selected generosity: cheap", Toast.LENGTH_SHORT).show();
                this.setGenerosityValue(1);
                break;
            case R.id.radioGEN_TWO:
                Toast.makeText(this, "Selected generosity: normal", Toast.LENGTH_SHORT).show();
                this.setGenerosityValue(5);
                break;
            case R.id.radioGEN_THREE:
                Toast.makeText(this, "Selected generosity: generous", Toast.LENGTH_SHORT).show();
                this.setGenerosityValue(10);
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
     * viewHistory will switch to the HistoryActivity. Method is called when the user taps HistoryButton.
     */
    public void viewHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    /**
     * Checks a given inputString for emptiness. If String is empty, will return "unknown". Else returns the inputString unaltered.
     * @param inputString the input string to be checked for emptiness
     * @return true: "unknown" false: inputString
     */
    private String checkForEmptyInput(String inputString) {
        if (inputString.matches(""))
            return "unknown";
        else
            return inputString;
    }

}
