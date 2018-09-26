package com.example.goldenblack.abs.navigation_button_booking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenblack.abs.*;

public class flightPassengerAndClass extends AppCompatActivity {

    TextView classValueView,passengerCountManView,passengerCountBoyView,passengerCountChildView;
    RadioGroup flightClassGroup;
    RadioButton flightClassRadioButton;
    int manCount=1,boyCount=0,childCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_passenger_and_class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        classValueView = (TextView)findViewById(R.id.classValueView);
        flightClassGroup = (RadioGroup)findViewById(R.id.classRadioGroup);
        //For man Linear
        passengerCountManView=(TextView)findViewById(R.id.passengerCountManView);
        ImageButton passengerCountManUP =(ImageButton)findViewById(R.id.passengerCountManUP);
        ImageButton passengerCountManDown =(ImageButton)findViewById(R.id.passengerCountManDown);

        //For Boy Linear
        passengerCountBoyView=(TextView)findViewById(R.id.passengerCountBoyView);
        ImageButton passengerCountBoyUP =(ImageButton)findViewById(R.id.passengerCountBoyUP);
        ImageButton passengerCountBoyDown =(ImageButton)findViewById(R.id.passengerCountBoyDown);

        //For Child Linear
        passengerCountChildView=(TextView)findViewById(R.id.passengerCountChildView);
        ImageButton passengerCountChildUP =(ImageButton)findViewById(R.id.passengerCountChildUP);
        ImageButton passengerCountChildDown =(ImageButton)findViewById(R.id.passengerCountChildDown);

        // Set value for Passenger View
        passengerCountManView.setText(manCount+"");
        passengerCountBoyView.setText(boyCount+"");
        passengerCountChildView.setText(childCount+"");
        // setPassengerValueInView();

        passengerCountManUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manCount<9 && manCount+boyCount<9) {
                    manCount++;
                    passengerCountManView.setText(""+manCount);
                }
                else
                    Toast.makeText(flightPassengerAndClass.this,"the max value is 9",Toast.LENGTH_SHORT).show();

            }
        });

        passengerCountManDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manCount>1)
                {    manCount--;
                if(childCount>manCount)
                { childCount=manCount;
                    passengerCountChildView.setText(""+childCount);

                }

                passengerCountManView.setText(""+manCount);}
                else
                      Toast.makeText(flightPassengerAndClass.this,"the value must to be 1 at least",Toast.LENGTH_SHORT).show();
            }
        });
        // For Boy button in custom Dialog
        passengerCountBoyUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boyCount<9 && manCount+boyCount<9) {
                    boyCount++;
                    passengerCountBoyView.setText(""+boyCount);
                }
                else {
                    Toast.makeText(flightPassengerAndClass.this,"the max value is 9",Toast.LENGTH_SHORT).show();
                }

            }
        });

        passengerCountBoyDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(boyCount>0) {
                    boyCount--;
                    passengerCountBoyView.setText("" + boyCount);
                }
                else
                       Toast.makeText(flightPassengerAndClass.this,"the value must to be 1 at least",Toast.LENGTH_SHORT).show();


            }
        });
        // For Child button in custom Dialog
        passengerCountChildUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childCount<manCount) {
                    childCount++;
                    passengerCountChildView.setText("" + childCount);
                }
                else
                    Toast.makeText(flightPassengerAndClass.this,"the value of child must to be least than adult",Toast.LENGTH_SHORT).show();
            }
        });

        passengerCountChildDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childCount>0){
                    childCount--;
                    passengerCountChildView.setText(""+childCount);
                }
                else
                       Toast.makeText(flightPassengerAndClass.this,"the value must to be 0 at least",Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void setFlightClass(String className){
        classValueView.setText(className);
    }

    // Return to button navigation and set passenger and class value
    public void returnDatesToBookingFragment(View view){

        //get checked radio button.
        int radioButtonId = flightClassGroup.getCheckedRadioButtonId();
        flightClassRadioButton = (RadioButton) findViewById(radioButtonId);


        Intent i = new Intent();
        i.putExtra("flightClass",flightClassRadioButton.getText().toString());
        i.putExtra("manCount",passengerCountManView.getText().toString());
        i.putExtra("boyCount",passengerCountBoyView.getText().toString());
        i.putExtra("childCount",passengerCountChildView.getText().toString());
        setResult(RESULT_OK,i);

        finish();
    }
}
