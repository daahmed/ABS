package com.example.goldenblack.abs.navigation_button_booking;

import android.content.Intent;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.goldenblack.abs.*;

import java.util.Calendar;

public class flightDate extends AppCompatActivity {

    TextView departDate,returnDate;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    int dateViewID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_date);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        departDate = (TextView)findViewById(R.id.fromDateDialog);
        returnDate = (TextView)findViewById(R.id.returnDateDialog);

// calender instance ..
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);



    }

    //calling for get and set th date
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    public void setDate(View view) {
        if(view.getId() == R.id.GetFromDateDialog)
            dateViewID = R.id.GetFromDateDialog;
        if(view.getId() == R.id.GetReturnDateDialog)
            dateViewID = R.id.GetReturnDateDialog;
        showDialog(999);
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };
    private void showDate(int year, int month, int day) {
        if( dateViewID == R.id.GetFromDateDialog)
            departDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        if( dateViewID == R.id.GetReturnDateDialog)
        {
            returnDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
            returnDatesToBookingFragment();
        }

    }

    private void returnDatesToBookingFragment(){

        Intent i = new Intent();
        i.putExtra("departDate",departDate.getText().toString());
        i.putExtra("returnDate",returnDate.getText().toString());
        setResult(RESULT_OK,i);

        finish();
    }
}
