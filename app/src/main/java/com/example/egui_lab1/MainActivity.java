package com.example.egui_lab1;

import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

        import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calcTipBtn = (Button) findViewById(R.id.calcTipBtn);

        calcTipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText billingAmountView = (EditText) findViewById(R.id.inputAmount);
                TextView totalResultView = (TextView) findViewById(R.id.SumAmountView);
                TextView tipResultView = (TextView) findViewById(R.id.TipAmountView);

                float bllngAmnt = Float.parseFloat(billingAmountView.getText().toString());
                float tip = bllngAmnt / 10;
                float totalSumResult = tip + bllngAmnt;
                totalResultView.setText(totalSumResult + "");
                tipResultView.setText(tip + "");
            }
        });
    }
}
