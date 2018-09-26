package com.example.goldenblack.abs.booking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;
import com.example.goldenblack.abs.*;
public class mainBooking extends AppCompatActivity {
    TextView viewJson,GetFromDate,fromDate,returnDate,classView,classValueView,passengerView,passengerValueManView,passengerValueBoyView,passengerValueClildView
            ,passengerCountManView,passengerCountBoyView,passengerCountChildView;
    ImageButton passengerUp,passengerDown;
    EditText editOrgin,editDestination;
    RadioGroup classRadioGroup;
    private DatePicker datePicker;
    private Calendar calendar;
    Button submit;
    private int year, month, day;
    int dateViewID=0;
    final Context c = this;
    AlertDialog alertDialogAndroid;
    CharSequence[] values = {"Economy","Business"};
    int manCount=1,boyCount=0,childCount=0;

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_booking);
        viewJson =(TextView)findViewById(R.id.hello);
        GetFromDate =(TextView)findViewById(R.id.GetFromDate);
        fromDate =(TextView)findViewById(R.id.fromDate);
        returnDate = (TextView)findViewById(R.id.returnDate);
        classView = (TextView)findViewById(R.id.classView);
        classValueView = (TextView)findViewById(R.id.classValueView);
        passengerView=(TextView)findViewById(R.id.passengerView);
        passengerValueManView = (TextView)findViewById(R.id.passengerManCountView);
        passengerValueBoyView = (TextView)findViewById(R.id.passengerBoyCountView);
        passengerValueClildView = (TextView)findViewById(R.id.passengerchildCountView);
        editOrgin = (EditText)findViewById(R.id.edit_origin);
        editDestination = (EditText)findViewById(R.id.edit_destination);
        submit=(Button)findViewById(R.id.searching);
        calendar = Calendar.getInstance();
        classRadioGroup = (RadioGroup)findViewById(R.id.classRadioGroup);


        year = calendar.get(Calendar.YEAR);

        //set filter for edit text to use apppear character
        editOrgin.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        editDestination.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //ADD Listener


        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);



        String json = bowlingJson("Jesse", "Jake");
        editDestination.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    //  submit.performClick();
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editDestination.getWindowToken(), 0);

                }
                return false;
            }
        });

        //dialog for class
        classView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.flight_class_dialog, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setTitle("Select Your Choice");
                alertDialogBuilderUserInput.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        switch(item)
                        {
                            case 0:
                                setFlightClass("Economy");
                                Toast.makeText(mainBooking.this, "Economy", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                setFlightClass("Business");
                                Toast.makeText(mainBooking.this, "Business", Toast.LENGTH_LONG).show();
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
        });

        //custom dialog for flight passengers
        passengerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);

                View mView = layoutInflaterAndroid.inflate(R.layout.flight_passengers_count, null);

                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);

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
                            Toast.makeText(c,"the max value is 9",Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(c,"the value must to be 1 at least",Toast.LENGTH_SHORT).show();

                        if(childCount>manCount)
                        { childCount=manCount;
                            passengerCountChildView.setText(""+childCount);
                            passengerValueClildView.setText(""+childCount);
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
                            Toast.makeText(c,"the max value is 9",Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(c,"the value must to be 1 at least",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(c,"the value of child must to be least than adult",Toast.LENGTH_SHORT).show();
                        passengerCountChildView.setText(""+childCount);
                        passengerValueClildView.setText(""+childCount);

                    }
                });

                passengerCountChildDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(childCount>0)
                            childCount--;
                        else
                            Toast.makeText(c,"the value must to be 0 at least",Toast.LENGTH_SHORT).show();
                        passengerCountChildView.setText(""+childCount);
                        passengerValueClildView.setText(""+childCount);

                    }
                });
            }
        });
    }

    /*
     *
     * */
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        if(editOrgin.getText().toString().length()==0){
            editOrgin.setError("Fill The Filed");
        }
        if(editDestination.getText().toString().length()==0){
            editOrgin.setError("Fill The Filed");
        }else{
            if(view.getId() == R.id.GetFromDate)
                dateViewID = R.id.GetFromDate;
            if(view.getId() == R.id.GetReturnDate)
                dateViewID = R.id.GetReturnDate;

            showDialog(999);}
    }


    /*Set the filegt class*/
    public void setFlightClass(String className){
        classValueView.setText(className);
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


    public void getFlight(View view){
        Intent i =  new Intent(mainBooking.this,FlightsActivity.class);
        i.putExtra("origin",editOrgin.getText().toString());
        i.putExtra("distination",editDestination.getText().toString());
        i.putExtra("fromDate",fromDate.getText().toString());
        i.putExtra("returnDate",returnDate.getText().toString());
        startActivity(i);
    }
    public void doGet() throws IOException{
        Request request = new Request.Builder()
                .url("http://www.vogella.com")
                .build();

        Response response = client.newCall(request).execute();
        viewJson.setText(response+" ");
        // return response.body().string();
    }
    public void doPost() throws IOException{
        String json = bowlingJson("Jesse", "Jake");
        RequestBody body = RequestBody.create(JSON,json);
        //http://www.roundsapp.com/post
        //https://reqres.in/api/users
        Request request = new Request.Builder()
                .url("http://10.0.2.2:8080/ABS/affiliate-search.json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mainBooking.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        viewJson.setText(" call.cancel ");
                    }
                });

                call.cancel();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String myResponse = response.body().string();
                String strParsedValue="";

                mainBooking.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            JSONArray jsonArray =jsonObject.getJSONArray("results");
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                //Root object
                                JSONObject rootObj = jsonArray.getJSONObject(i);
                                //outbound object
                                JSONObject outboundObj=rootObj.optJSONObject("outbound");
                                viewJson.append("\n"+outboundObj.getString("duration").toString());

                                //flights

                                //0

                                //aircraft

                                //flight_number
                                //departs_at
                                //arrives_at

                                //deep_link
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        // viewJson.setText(myResponse+" ");
                    }
                });

            }
        });

        //  return response.body().string();

    }

    String bowlingJson(String player1, String player2) {
        return "{'winCondition':'HIGH_SCORE',"
                + "'name':'Bowling',"
                + "'round':4,"
                + "'lastSaved':1367702411696,"
                + "'dateStarted':1367702378785,"
                + "'players':["
                + "{'name':'" + player1 + "','history':[10,8,6,7,8],'color':-13388315,'total':39},"
                + "{'name':'" + player2 + "','history':[6,10,5,10,10],'color':-48060,'total':41}"
                + "]}";
    }

}
