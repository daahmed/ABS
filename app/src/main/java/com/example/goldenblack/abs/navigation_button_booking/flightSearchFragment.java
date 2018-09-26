package com.example.goldenblack.abs.navigation_button_booking;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldenblack.abs.R;
import com.example.goldenblack.abs.booking.FlightsActivity;
import com.example.goldenblack.abs.model.flight;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

public class flightSearchFragment extends Fragment  {
    public View view;
    TextView viewJson,GetFromDate,fromDate,returnDate,classView,classValueView,passengerView,passengerValueManView,passengerValueBoyView, passengerValueChildView
            ,passengerCountManView,passengerCountBoyView,passengerCountChildView;
    ImageButton passengerUp,passengerDown;
    LinearLayout second_container,date_container;
    EditText editOrgin,editDestination;
    RadioGroup classRadioGroup;
    private DatePicker datePicker;
    private Calendar calendar;
    Button submit;
    private int year, month, day;
    String departDateStr="",returnDateStr="",flightClassStr="",manCountStr="",boyCountStr="",childCountStr="";
    int dateViewID=0;
    //final Context c = this;
    AlertDialog alertDialogAndroid;
    CharSequence[] values = {"Economy","Business"};
    int manCount=1,boyCount=0,childCount=0;
    private static final int REQUEST_CODE_GET_DATE = 1;
    private static final int REQUEST_CODE_GET_PASSENGER_AND_CLASS = 2;
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    flight flightDetails = new flight();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        view  =inflater.inflate(R.layout.activity_main_booking, container, false);
       // mTextMessage = (TextView) view.findViewById(R.id.message);
       // BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewJson =(TextView)view.findViewById(R.id.hello);
        GetFromDate =(TextView)view.findViewById(R.id.GetFromDate);
        fromDate =(TextView)view.findViewById(R.id.fromDate);
        returnDate = (TextView)view.findViewById(R.id.returnDate);
        classView = (TextView)view.findViewById(R.id.classView);
        classValueView = (TextView)view.findViewById(R.id.classValueView);
        passengerView=(TextView)view.findViewById(R.id.passengerView);
        passengerValueManView = (TextView)view.findViewById(R.id.passengerManCountView);
        passengerValueBoyView = (TextView)view.findViewById(R.id.passengerBoyCountView);
        passengerValueChildView = (TextView)view.findViewById(R.id.passengerchildCountView);
        editOrgin = (EditText)view.findViewById(R.id.edit_origin);
        editDestination = (EditText)view.findViewById(R.id.edit_destination);
        submit=(Button)view.findViewById(R.id.searching);
        calendar = Calendar.getInstance();
        classRadioGroup = (RadioGroup)view.findViewById(R.id.classRadioGroup);
        second_container = (LinearLayout)view.findViewById(R.id.second_container);
        date_container = (LinearLayout)view.findViewById(R.id.container_date_section);

        year = calendar.get(Calendar.YEAR);

        //set filter for edit text to use apppear character
        editOrgin.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editDestination.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //ADD Listener


        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);




        date_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate();
            }
        });
       // String json = bowlingJson("Jesse", "Jake");
        editDestination.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    //  submit.performClick();
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editDestination.getWindowToken(), 0);

                }
                return false;
            }
        });

        second_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),flightPassengerAndClass.class);
                startActivityForResult(i,REQUEST_CODE_GET_PASSENGER_AND_CLASS);
            }
        });

        //dialog for class
       /* classView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(view.getContext());
                View mView = layoutInflaterAndroid.inflate(R.layout.flight_class_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(view.getContext());
                alertDialogBuilderUserInput.setTitle("Select Your Choice");
                alertDialogBuilderUserInput.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                setFlightClass("Economy");
                          //      Toast.makeText(mainBooking.this, "Economy", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                setFlightClass("Business");
                            //    Toast.makeText(mainBooking.this, "Business", Toast.LENGTH_LONG).show();
                                break;
                        }
                        alertDialogAndroid.dismiss();
                    }
                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }

                                });
                alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });*/

        //custom dialog for flight passengers
    /*    passengerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater layoutInflaterAndroid = LayoutInflater.from(view.getContext());

                View mView = layoutInflaterAndroid.inflate(R.layout.flight_passengers_count, null);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(view.getContext());

                alertDialogBuilderUserInput.setView(mView);

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Don", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here


                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

                //For man Linear
                passengerCountManView=(TextView)alertDialogAndroid.findViewById(R.id.passengerCountManView);
                ImageButton passengerCountManUP =(ImageButton)alertDialogAndroid.findViewById(R.id.passengerCountManUP);
                ImageButton passengerCountManDown =(ImageButton)alertDialogAndroid.findViewById(R.id.passengerCountManDown);

                //For Boy Linear
                passengerCountBoyView=(TextView)alertDialogAndroid.findViewById(R.id.passengerCountBoyView);
                ImageButton passengerCountBoyUP =(ImageButton)alertDialogAndroid.findViewById(R.id.passengerCountBoyUP);
                ImageButton passengerCountBoyDown =(ImageButton)alertDialogAndroid.findViewById(R.id.passengerCountBoyDown);

                //For Child Linear
                passengerCountChildView=(TextView)alertDialogAndroid.findViewById(R.id.passengerCountChildView);
                ImageButton passengerCountChildUP =(ImageButton)alertDialogAndroid.findViewById(R.id.passengerCountChildUP);
                ImageButton passengerCountChildDown =(ImageButton)alertDialogAndroid.findViewById(R.id.passengerCountChildDown);

                // Set value for Passenger View
                passengerCountManView.setText(manCount+"");
                passengerCountBoyView.setText(boyCount+"");
                passengerCountChildView.setText(childCount+"");
                // setPassengerValueInView();

                passengerCountManUP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(manCount<9 && manCount+boyCount<9)
                            manCount++;
                        else
                        //    Toast.makeText(c,"the max value is 9",Toast.LENGTH_SHORT).show();

                        passengerCountManView.setText(""+manCount);
                        passengerValueManView.setText(""+manCount);

                    }
                });

                passengerCountManDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(manCount>1)
                            manCount--;
                        else
                         //   Toast.makeText(c,"the value must to be 1 at least",Toast.LENGTH_SHORT).show();

                        if(childCount>manCount)
                        { childCount=manCount;
                            passengerCountChildView.setText(""+childCount);
                            passengerValueChildView.setText(""+childCount);
                        }

                        passengerCountManView.setText(""+manCount);
                        passengerValueManView.setText(""+manCount);

                    }
                });
                // For Boy button in custom Dialog
                passengerCountBoyUP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(boyCount<9 && manCount+boyCount<9)
                            boyCount++;
                        else
                         //   Toast.makeText(c,"the max value is 9",Toast.LENGTH_SHORT).show();

                        passengerCountBoyView.setText(""+boyCount);
                        passengerValueBoyView.setText(""+boyCount);

                    }
                });

                passengerCountBoyDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(boyCount>0)
                            boyCount--;
                        else
                         //   Toast.makeText(c,"the value must to be 1 at least",Toast.LENGTH_SHORT).show();
                        passengerCountBoyView.setText(""+boyCount);
                        passengerValueBoyView.setText(""+boyCount);

                    }
                });
                // For Child button in custom Dialog
                passengerCountChildUP.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childCount<manCount)
                            childCount++;
                        else
                         //   Toast.makeText(c,"the value of child must to be least than adult",Toast.LENGTH_SHORT).show();
                        passengerCountChildView.setText(""+childCount);
                        passengerValueChildView.setText(""+childCount);

                    }
                });

                passengerCountChildDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childCount>0)
            childCount--;
        else
            //   Toast.makeText(c,"the value must to be 0 at least",Toast.LENGTH_SHORT).show();
            passengerCountChildView.setText(""+childCount);
        passengerValueChildView.setText(""+childCount);

    }
});
        }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlight();
            }
        });


        return view;
    }
//calling for get and set th date
    public void setDate() {
        Intent i = new Intent(getActivity(),flightDate.class);
        startActivityForResult(i,REQUEST_CODE_GET_DATE);

        // old work
//        if(editOrgin.getText().toString().length()==0){
//            editOrgin.setError("Fill The Filed");
//        }
//        if(editDestination.getText().toString().length()==0){
//            editOrgin.setError("Fill The Filed");
//        }else{
//            if(view.getId() == R.id.GetFromDate)
//                dateViewID = R.id.GetFromDate;
//            if(view.getId() == R.id.GetReturnDate)
//                dateViewID = R.id.GetReturnDate;
//
//            getActivity().showDialog(999);}

    }
    /*Set the filegt class*/
    public void setFlightClass(String className){
        classValueView.setText(className);
    }
//
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        // TODO Auto-generated method stub
//        if (id == 999) {
//            return new DatePickerDialog(view.getContext(),
//                    myDateListener, year, month, day);
//        }
//        return null;
//    }

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
        if( dateViewID == R.id.GetFromDate)
            fromDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        if( dateViewID == R.id.GetReturnDate)
            returnDate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUEST_CODE_GET_DATE == requestCode){
            if(Activity.RESULT_OK == resultCode) {
                departDateStr = data.getExtras().getString("departDate");
                returnDateStr = data.getExtras().getString("returnDate");
                getDateFromReturnedDateActivityIntent(requestCode);
                Toast.makeText(getActivity(), "yes", Toast.LENGTH_LONG).show();
            }
        }
        if(REQUEST_CODE_GET_PASSENGER_AND_CLASS == requestCode)
        {
            if(Activity.RESULT_OK == resultCode) {
                flightClassStr=data.getExtras().getString("flightClass");
                manCountStr = data.getExtras().getString("manCount");
                boyCountStr= data.getExtras().getString("boyCount");
                childCountStr= data.getExtras().getString("childCount");
                getDateFromReturnedDateActivityIntent(requestCode);
            }


        }
        else
            Toast.makeText(getActivity(),"no data found",Toast.LENGTH_LONG).show();
    }

    public void getFlight(){
        Intent i =  new Intent(getActivity(),FlightsActivity.class);
        i.putExtra("origin",editOrgin.getText().toString());
        i.putExtra("distination",editDestination.getText().toString());
        i.putExtra("fromDate",fromDate.getText().toString());
        i.putExtra("returnDate",returnDate.getText().toString());
        startActivity(i);
    }

    public void getDateFromReturnedDateActivityIntent(int requestCode){
        Toast.makeText(getActivity(),"requestCode : "+requestCode,Toast.LENGTH_LONG);
        if(REQUEST_CODE_GET_DATE == requestCode)
        {
            if (departDateStr.length()>1)
                fromDate.setText(departDateStr);
            if (returnDateStr.length()>1)
                returnDate.setText(returnDateStr);
        }
        if(REQUEST_CODE_GET_PASSENGER_AND_CLASS == requestCode)
        {

            if (flightClassStr.length()>1)
                classValueView.setText(flightClassStr);
            if (manCountStr.length()>=1)
                passengerValueManView.setText(manCountStr);
            if (boyCountStr.length()>=1)
                passengerValueBoyView.setText(boyCountStr);
            if (childCountStr.length()>=1)
                passengerValueChildView.setText(childCountStr);
        }


    }

    public void setPassenger(View view){


    }

}
