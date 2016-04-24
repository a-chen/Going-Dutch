package com.jaja.billsplitbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class evenResult extends AppCompatActivity {
    double perPersonAmount;
    double effectiveTipPercentage;
    int roundingFactor = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.even_result);

        final TextView perPerson = (TextView) findViewById(R.id.total);
        final TextView tipPercentField = (TextView) findViewById(R.id.tipPercentageView);
        final ToggleButton penny = (ToggleButton) findViewById(R.id.pennyToggle);
        final ToggleButton quarter = (ToggleButton) findViewById(R.id.quarterToggle);
        final ToggleButton dollar = (ToggleButton) findViewById(R.id.dollarToggle);
        final Button back = (Button) findViewById(R.id.back_button);
        final Button increasePay = (Button) findViewById(R.id.incrementTip);
        final Button decreasePay = (Button) findViewById(R.id.decrementTip);

        //Calculates default value for 15% tip
        perPersonAmount = evenInput.totalAmount * 1.15 / evenInput.numPeople;
        perPersonAmount = roundToNearest(perPersonAmount, 1);
        perPerson.setText(String.valueOf(perPersonAmount));

        //Sets labels for toggle labels
        penny.setTextOff("Penny");
        penny.setTextOn("Penny");
        quarter.setTextOff("Quarter");
        quarter.setTextOn("Quarter");
        dollar.setTextOff("Dollar");
        dollar.setTextOn("Dollar");
        penny.setChecked(true);
        quarter.setChecked(false);
        dollar.setChecked(false);

        penny.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    quarter.setChecked(false);
                    dollar.setChecked(false);
                    roundingFactor = 1;
                    perPersonAmount = roundToNearest(perPersonAmount, roundingFactor);
                    perPerson.setText(String.format("%.2f", perPersonAmount));
                    tipPercentField.setText(String.format("%.2f", getTipPercentage()));
                } else {
                    // The toggle is disabled
                    penny.setChecked(false);
                }
            }
        });

        quarter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    penny.setChecked(false);
                    dollar.setChecked(false);
                    roundingFactor = 25;
                    perPersonAmount = roundToNearest(perPersonAmount, roundingFactor);
                    perPerson.setText(String.format("%.2f", perPersonAmount));
                    tipPercentField.setText(String.format("%.2f", getTipPercentage()));
                } else {
                    // The toggle is disabled
                    quarter.setChecked(false);
                }
            }
        });

        dollar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    penny.setChecked(false);
                    quarter.setChecked(false);
                    roundingFactor = 100;
                    perPersonAmount = roundToNearest(perPersonAmount, roundingFactor);
                    perPerson.setText(String.format("%.2f", perPersonAmount));
                    tipPercentField.setText(String.format("%.2f", getTipPercentage()));
                } else {
                    // The toggle is disabled
                    dollar.setChecked(false);
                }
            }
        });


        increasePay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                perPersonAmount = roundToNearest(perPersonAmount, roundingFactor);
                perPersonAmount += ((double) (roundingFactor))/100;
                perPerson.setText(String.format("%.2f", perPersonAmount));
                tipPercentField.setText(String.format("%.2f", getTipPercentage()));
            }
        });

        decreasePay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                perPersonAmount = roundToNearest(perPersonAmount, roundingFactor);
                perPersonAmount -= ((double) (roundingFactor))/100;
                perPerson.setText(String.format("%.2f", perPersonAmount));
                tipPercentField.setText(String.format("%.2f", getTipPercentage()));
            }
        });



        back.setOnClickListener(new View.OnClickListener() { //go back to even result
            public void onClick(View v) {
                Intent transition = new Intent(evenResult.this, evenInput.class);
                startActivity(transition);
            }
        });
    }

    double roundToNearest(double number, int multiple){
        int temp = (int)(number * 100);
        while (temp % multiple > 0) {
            temp++;
        }
        double answer = temp;
        answer /= 100;
        return answer;
    }

    double getTipPercentage() {
        return ((perPersonAmount * evenInput.numPeople / evenInput.totalAmount) - 1) * 100;
    }
}
