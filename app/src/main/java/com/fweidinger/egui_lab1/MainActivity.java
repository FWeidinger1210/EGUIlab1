package com.fweidinger.egui_lab1;

import android.content.Intent;
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

import com.fweidinger.egui_lab1.data.DatabaseHelper;
import com.fweidinger.egui_lab1.helpers.Formatter;

import java.time.Instant;
import java.util.Locale;

/**
 * The main activity serves as entry point to the application.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    RadioGroup radioGroupQOS;
    RadioButton radioButtonQOS;
    RadioGroup radioGroupGEN;
    RadioButton radioButtonGEN;
    /**
     * Reference to the DatabaseHelper Class that handles all operation between the UI and the database.
     */
    DatabaseHelper databaseHelper;

    /**
     * The Quality of Service represented as a floating point number.
     * Can be 0.5, 1 or 2.
     * qosValue is used to calculate the tip. Its value is set in the checkButton() method.
     */
    private float qosValue;
    /**
     * The generosity level represented as a floating point number.
     * Can be 1, 5 or 10.
     * qosValue is used to calculate the tip. Its value is set in the checkButton() method.
     */
    private float generosityValue;

    /**
     *  Returns the Quality of Service for calculations.
     * @return qosValue  - the Quality of Service value
     */
    public float getQosValue() {
        return qosValue;
    }

    /**
     * Sets the Quality of Service value. (Used in checkButton)
     * @param qosValue qosValue  - the Quality of Service value
     */
    public void setQosValue(float qosValue) {
        this.qosValue = qosValue;
    }

    /**
     * Getter for the generosity level
     * @return generosityValue
     */
    public float getGenerosityValue() {
        return generosityValue;
    }

    /**
     * Setter for the generosity level. (Used in checkButtonGen() method)
     * @param generosityValue
     */
    public void setGenerosityValue(float generosityValue) {
        this.generosityValue = generosityValue;
    }


    /**
     * The onCreate Method initialises the basic setup of the MainAcivity.
     * It finds the relevant views by calling the findViewById method and also sets on ClickListeners for the two Buttons in this layout.
     * In this method a new instance of the DatabaseHelper class is created. This way the connection to the database is permanent
     * for the lifetime of the activity. Otherwise a new connection would have to be opened for every insertion, which is not ressource
     * efficient.
     * @param savedInstanceState
     */
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
     * This method is called when a RadioButton of buttonGroupQOS is pressed.
     * Distinguishes between specific RadioButton by ID and sets qosValue.
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

    /**
     * This method is called when a RadioButton of buttonGroupGEN is pressed.
     * Distinguishes between specific RadioButton by ID and sets generosityValue.
     * @param view the view
     */
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
     * viewHistory will switch to the HistoryActivity. The Method is called when the user touches HistoryButton.
     * A new Intent is created that takes the HistoryAcivity as a parameter. The startActivity() method then knows precisely what
     * activity is to be displayed.
     */
    public void viewHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    /**
     * Checks a given inputString for 'emptiness'. If String is empty, will return "unknown". Else returns the inputString unaltered.
     * @param inputString the input string to be checked for 'emptiness'
     * @return true: "unknown" false: inputString
     */
    private String checkForEmptyInput(String inputString) {
        if (inputString.matches(""))
            return "unknown";
        else
            return inputString;
    }

}
