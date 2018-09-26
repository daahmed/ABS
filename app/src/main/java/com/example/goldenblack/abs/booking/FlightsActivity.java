package com.example.goldenblack.abs.booking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.goldenblack.abs.*;
import com.example.goldenblack.abs.navigation_button_booking.CustomAdapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlightsActivity extends AppCompatActivity {
    ListView listView;
    ListView list;
    CustomAdapter adapter;
    TextView viewOrigin,viewDestination;
    private ProgressBar spinner;
    OkHttpClient client = new OkHttpClient();
    ArrayList<String> listPrice = new ArrayList<>();;
    ArrayList<String> listDate = new ArrayList<>();
    ArrayList<String> listBooking_info = new ArrayList<>();
    ArrayList<String> listDuration = new ArrayList<>();
    ArrayList<String> listDeepLink = new ArrayList<>();
    String strOrigin,strDistination,strFromDate,strReturnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flights);
        viewOrigin = (TextView)findViewById(R.id.flights_view_origin) ;
        viewDestination = (TextView)findViewById(R.id.flights_view_destination) ;
        spinner=(ProgressBar)findViewById(R.id.progressBar);




        Intent i = this.getIntent();
        strOrigin=i.getExtras().getString("origin");
        strDistination=i.getExtras().getString("distination");
        strFromDate=i.getExtras().getString("fromDate");
        strReturnDate=i.getExtras().getString("returnDate");

        //Set origin and destination city on top list view
        viewOrigin.setText(strOrigin);
        viewDestination.setText(strDistination);
        Toast.makeText(this,strOrigin + " " +strDistination+ " "+strFromDate+" "+strReturnDate ,Toast.LENGTH_LONG).show();

        try {
            doPost();
        } catch (IOException e) {
            e.printStackTrace();
        }

        adapter=new CustomAdapter(this, listPrice, listDate, listBooking_info, listDuration, listDeepLink);
        // CustomAdapter adapter=new CustomAdapter(this, deep_link, price,date,booking_info,duration);
        // CustomAdapter adapter=new CustomAdapter(this, maintitle, subtitle,imgid);
        list=(ListView)findViewById(R.id.list);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if(position == 0) {
                    //code specific to first listDate item
                    Toast.makeText(getApplicationContext(),"Place Your First Option Code",Toast.LENGTH_SHORT).show();
                }

                else if(position == 1) {
                    //code specific to 2nd listDate item
                    Toast.makeText(getApplicationContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
    public void doPost() throws IOException{
        // String json = bowlingJson("Jesse", "Jake");
        //  RequestBody body = RequestBody.create(JSON,json);
        //http://www.roundsapp.com/post
        //https://reqres.in/api/users
        spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        Request request = new Request.Builder()
                //
                //.url("http://10.0.2.2:8080/ABS/affiliate-search.json")
                //  .url(R.d)
                // .url("https://api.sandbox.amadeus.com/v1.2/flights/affiliate-search?apikey=tTDC2o18oBU44UYqEOtZ0IGEstCubEf3&origin="+strOrigin+"&destination="+strDistination+"&departure_date=2018-12-26")
                .url("https://api.sandbox.amadeus.com/v1.2/flights/affiliate-search?apikey=tTDC2o18oBU44UYqEOtZ0IGEstCubEf3&origin=LON&destination=DUB&departure_date=2018-09-25")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                FlightsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        spinner.setVisibility(View.INVISIBLE);
                        //  viewJson.setText(" call.cancel ");
                        System.out.println(" listPrice.add( call.cancel );");
                        listPrice.add(" call.cancel ");
                        listDate.add(" call.cancel ");
                        listBooking_info.add(" call.cancel ");
                        listDuration.add(" call.cancel ");
                        listDeepLink.add(" call.cancel ");
                        list.setAdapter(adapter);
                    }
                });

                call.cancel();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                // final String myResponse = response.body().string();
                final String myResponse ="{ \"request_id\":\"9414cb57-2677-43fd-9031-0e782a9a29e5\",\"meta\":{\"carriers\":{\"WX\":{\"logos\":{\"small\":\"/static/img/airlines/small/WX.png\",\"medium\":\"/static/img/airlines/medium/WX.png\"},\"name\":\"CityJet\"}}},\"results\":[{\"outbound\":{\"duration\":\"01:25\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"125\",\"departs_at\":\"2018-12-28T18:45\",\"arrives_at\":\"2018-12-28T20:10\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"X\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"VALUE\",\"fare_basis\":\"X9PRWGB5\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"49.02\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"30.02\",\"total_fare\":\"49.02\"}},\"cabin_code\":\"Y\",\"fare_family\":\"VALUE\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpVc1ZrRk1WVVY4TkRrdU1ESjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.x8I2QwbzomK2d7CX8thOxD2W8O8aubEkb3QlWf7YJdA\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"103\",\"departs_at\":\"2018-12-28T06:35\",\"arrives_at\":\"2018-12-28T08:05\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"X\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"VALUE\",\"fare_basis\":\"X9PRWGB5\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"49.02\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"30.02\",\"total_fare\":\"49.02\"}},\"cabin_code\":\"Y\",\"fare_family\":\"VALUE\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TURNc1ZrRk1WVVY4TkRrdU1ESjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.MPNF3XeaNvry2F6Tu3oBL2CTHviIVerc_0rtuNFj7YY\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"127\",\"departs_at\":\"2018-12-28T20:05\",\"arrives_at\":\"2018-12-28T21:35\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"X\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"VALUE\",\"fare_basis\":\"X9PRWGB5\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"49.02\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"30.02\",\"total_fare\":\"49.02\"}},\"cabin_code\":\"Y\",\"fare_family\":\"VALUE\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpjc1ZrRk1WVVY4TkRrdU1ESjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.yJFiXGi3-mwqs0xJo9gPEwYnhSayQNUmpEMonWefiKI\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"113\",\"departs_at\":\"2018-12-28T09:40\",\"arrives_at\":\"2018-12-28T11:10\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"V\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"VALUE\",\"fare_basis\":\"V9PRWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"80.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"80.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"VALUE\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TVRNc1ZrRk1WVVY4T0RBdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.a3T24TWEZDvZU5YS1-k7upGyn8Zw7TbAz9nToAOQRck\"},{\"outbound\":{\"duration\":\"01:35\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"119\",\"departs_at\":\"2018-12-28T11:45\",\"arrives_at\":\"2018-12-28T13:20\",\"booking_info\":{\"seats_remaining\":4,\"booking_code\":\"V\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"VALUE\",\"fare_basis\":\"V9PRWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"80.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"80.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"VALUE\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TVRrc1ZrRk1WVVY4T0RBdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.AYdEUDgFUt9yYRHuGWqgx9yU0Wg11j8Kp4_0XYX8L9k\"},{\"outbound\":{\"duration\":\"01:35\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"123\",\"departs_at\":\"2018-12-28T17:30\",\"arrives_at\":\"2018-12-28T19:05\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"V\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"VALUE\",\"fare_basis\":\"V9PRWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"80.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"80.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"VALUE\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpNc1ZrRk1WVVY4T0RBdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.3w1JfdlFVLlLZRcJUYMfZeC8Ma5ymjeTbk2cL5aOUUA\"},{\"outbound\":{\"duration\":\"01:25\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"125\",\"departs_at\":\"2018-12-28T18:45\",\"arrives_at\":\"2018-12-28T20:10\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"X\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"FLEX\",\"fare_basis\":\"X9PFWGB5\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"84.02\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"30.02\",\"total_fare\":\"84.02\"}},\"cabin_code\":\"Y\",\"fare_family\":\"FLEX\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpVc1JreEZXSHc0TkM0d01ueEZWVko4T1RReE5HTmlOVGN0TWpZM055MDBNMlprTFRrd016RXRNR1UzT0RKaE9XRXlPV1UxZkZkWSIsIm9mZmVyX2lkIjoiMzAifQ.6dF6wfllglqWmf6jqqVYk5NtfOcjCKK9oP9RyNwTPTA\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"103\",\"departs_at\":\"2018-12-28T06:35\",\"arrives_at\":\"2018-12-28T08:05\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"X\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"FLEX\",\"fare_basis\":\"X9PFWGB5\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"84.02\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"30.02\",\"total_fare\":\"84.02\"}},\"cabin_code\":\"Y\",\"fare_family\":\"FLEX\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TURNc1JreEZXSHc0TkM0d01ueEZWVko4T1RReE5HTmlOVGN0TWpZM055MDBNMlprTFRrd016RXRNR1UzT0RKaE9XRXlPV1UxZkZkWSIsIm9mZmVyX2lkIjoiMzAifQ.XfF4ZMTraZ6bi_z3L51O_1HtQPKb2vg10IgJs6f8V0c\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"127\",\"departs_at\":\"2018-12-28T20:05\",\"arrives_at\":\"2018-12-28T21:35\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"X\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"FLEX\",\"fare_basis\":\"X9PFWGB5\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"84.02\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"30.02\",\"total_fare\":\"84.02\"}},\"cabin_code\":\"Y\",\"fare_family\":\"FLEX\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpjc1JreEZXSHc0TkM0d01ueEZWVko4T1RReE5HTmlOVGN0TWpZM055MDBNMlprTFRrd016RXRNR1UzT0RKaE9XRXlPV1UxZkZkWSIsIm9mZmVyX2lkIjoiMzAifQ.-0Eoc8oa2chIcaoHD7H0itqcF54V7CRfpI_XWpXiccU\"},{\"outbound\":{\"duration\":\"01:25\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"121\",\"departs_at\":\"2018-12-28T15:30\",\"arrives_at\":\"2018-12-28T16:55\",\"booking_info\":{\"seats_remaining\":8,\"booking_code\":\"R\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"VALUE\",\"fare_basis\":\"R9PRWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"90.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"90.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"VALUE\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpFc1ZrRk1WVVY4T1RBdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.4I-TYv5Dq0S_jp3ynNfPoE3piRydzmt2ZzrPb-HfYNM\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"113\",\"departs_at\":\"2018-12-28T09:40\",\"arrives_at\":\"2018-12-28T11:10\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"V\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"FLEX\",\"fare_basis\":\"V9PFWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"111.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"111.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"FLEX\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TVRNc1JreEZXSHd4TVRFdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.pNPMqLtbJehgXCow1zu2GLzepmUCqutZxC2dyw6IlN4\"},{\"outbound\":{\"duration\":\"01:35\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"119\",\"departs_at\":\"2018-12-28T11:45\",\"arrives_at\":\"2018-12-28T13:20\",\"booking_info\":{\"seats_remaining\":4,\"booking_code\":\"V\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"FLEX\",\"fare_basis\":\"V9PFWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"111.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"111.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"FLEX\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TVRrc1JreEZXSHd4TVRFdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.HzMqddwKcVmLzXzwYSsEcHcbxU0Tsd8_nIXG5aSGtbM\"},{\"outbound\":{\"duration\":\"01:35\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"123\",\"departs_at\":\"2018-12-28T17:30\",\"arrives_at\":\"2018-12-28T19:05\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"V\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"FLEX\",\"fare_basis\":\"V9PFWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"111.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"111.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"FLEX\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpNc1JreEZXSHd4TVRFdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.xu0OcakFMW--Vz4cETYORLH3pcR2t9WWZMcX5N8ihMw\"},{\"outbound\":{\"duration\":\"01:25\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"121\",\"departs_at\":\"2018-12-28T15:30\",\"arrives_at\":\"2018-12-28T16:55\",\"booking_info\":{\"seats_remaining\":8,\"booking_code\":\"R\",\"travel_class\":\"ECONOMY\",\"cabin_code\":\"M\",\"fare_family\":\"FLEX\",\"fare_basis\":\"R9PFWGB3\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"121.65\",\"restrictions\":{\"refundable\":false,\"change_penalties\":true},\"price_per_adult\":{\"tax\":\"56.65\",\"total_fare\":\"121.65\"}},\"cabin_code\":\"Y\",\"fare_family\":\"FLEX\",\"travel_class\":\"ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpFc1JreEZXSHd4TWpFdU5qVjhSVlZTZkRrME1UUmpZalUzTFRJMk56Y3RORE5tWkMwNU1ETXhMVEJsTnpneVlUbGhNamxsTlh4WFdBJTI1M0QlMjUzRCIsIm9mZmVyX2lkIjoiMzAifQ.Wm8lCuamIfP3_Ost2iNutTpbS7JX_Tpewo_MX_G4GzI\"},{\"outbound\":{\"duration\":\"01:25\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"121\",\"departs_at\":\"2018-12-28T15:30\",\"arrives_at\":\"2018-12-28T16:55\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"A\",\"travel_class\":\"PREMIUM_ECONOMY\",\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"fare_basis\":\"A9FFWGB1\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"208.11\",\"restrictions\":{\"refundable\":true,\"change_penalties\":false},\"price_per_adult\":{\"tax\":\"71.11\",\"total_fare\":\"208.11\"}},\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"travel_class\":\"PREMIUM_ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpFc1VGSkZUVWxWVFh3eU1EZ3VNVEY4UlZWU2ZEazBNVFJqWWpVM0xUSTJOemN0TkRObVpDMDVNRE14TFRCbE56Z3lZVGxoTWpsbE5YeFhXQSUyNTNEJTI1M0QiLCJvZmZlcl9pZCI6IjMwIn0.DH9xtd8IuSE4zl5Ovyi89E1E83zHEdLOd9lPTL9hMf0\"},{\"outbound\":{\"duration\":\"01:25\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"125\",\"departs_at\":\"2018-12-28T18:45\",\"arrives_at\":\"2018-12-28T20:10\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"A\",\"travel_class\":\"PREMIUM_ECONOMY\",\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"fare_basis\":\"A9FFWGB1\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"208.11\",\"restrictions\":{\"refundable\":true,\"change_penalties\":false},\"price_per_adult\":{\"tax\":\"71.11\",\"total_fare\":\"208.11\"}},\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"travel_class\":\"PREMIUM_ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpVc1VGSkZUVWxWVFh3eU1EZ3VNVEY4UlZWU2ZEazBNVFJqWWpVM0xUSTJOemN0TkRObVpDMDVNRE14TFRCbE56Z3lZVGxoTWpsbE5YeFhXQSUyNTNEJTI1M0QiLCJvZmZlcl9pZCI6IjMwIn0.3I9lEqb4wyr7djCZnjc8A-X7TYv_D-ElBiLgmkEkVjA\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"103\",\"departs_at\":\"2018-12-28T06:35\",\"arrives_at\":\"2018-12-28T08:05\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"A\",\"travel_class\":\"PREMIUM_ECONOMY\",\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"fare_basis\":\"A9FFWGB1\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"208.11\",\"restrictions\":{\"refundable\":true,\"change_penalties\":false},\"price_per_adult\":{\"tax\":\"71.11\",\"total_fare\":\"208.11\"}},\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"travel_class\":\"PREMIUM_ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TURNc1VGSkZUVWxWVFh3eU1EZ3VNVEY4UlZWU2ZEazBNVFJqWWpVM0xUSTJOemN0TkRObVpDMDVNRE14TFRCbE56Z3lZVGxoTWpsbE5YeFhXQSUyNTNEJTI1M0QiLCJvZmZlcl9pZCI6IjMwIn0.YTcRyyPbYfowD08hK-XJ1BaT4vuJ2V8bHMZlbITnkWw\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"113\",\"departs_at\":\"2018-12-28T09:40\",\"arrives_at\":\"2018-12-28T11:10\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"A\",\"travel_class\":\"PREMIUM_ECONOMY\",\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"fare_basis\":\"A9FFWGB1\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"208.11\",\"restrictions\":{\"refundable\":true,\"change_penalties\":false},\"price_per_adult\":{\"tax\":\"71.11\",\"total_fare\":\"208.11\"}},\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"travel_class\":\"PREMIUM_ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TVRNc1VGSkZUVWxWVFh3eU1EZ3VNVEY4UlZWU2ZEazBNVFJqWWpVM0xUSTJOemN0TkRObVpDMDVNRE14TFRCbE56Z3lZVGxoTWpsbE5YeFhXQSUyNTNEJTI1M0QiLCJvZmZlcl9pZCI6IjMwIn0._yiGbwlVAsLSm-zeon5fw-B4VNQhe3w3aF7UpBUjiKs\"},{\"outbound\":{\"duration\":\"01:30\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"127\",\"departs_at\":\"2018-12-28T20:05\",\"arrives_at\":\"2018-12-28T21:35\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"A\",\"travel_class\":\"PREMIUM_ECONOMY\",\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"fare_basis\":\"A9FFWGB1\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"208.11\",\"restrictions\":{\"refundable\":true,\"change_penalties\":false},\"price_per_adult\":{\"tax\":\"71.11\",\"total_fare\":\"208.11\"}},\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"travel_class\":\"PREMIUM_ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpjc1VGSkZUVWxWVFh3eU1EZ3VNVEY4UlZWU2ZEazBNVFJqWWpVM0xUSTJOemN0TkRObVpDMDVNRE14TFRCbE56Z3lZVGxoTWpsbE5YeFhXQSUyNTNEJTI1M0QiLCJvZmZlcl9pZCI6IjMwIn0.AjKx294kzNtA5fG3P3oCiOc-0VzpZfbtgzoLPiZm7QE\"},{\"outbound\":{\"duration\":\"01:35\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"119\",\"departs_at\":\"2018-12-28T11:45\",\"arrives_at\":\"2018-12-28T13:20\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"A\",\"travel_class\":\"PREMIUM_ECONOMY\",\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"fare_basis\":\"A9FFWGB1\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"208.11\",\"restrictions\":{\"refundable\":true,\"change_penalties\":false},\"price_per_adult\":{\"tax\":\"71.11\",\"total_fare\":\"208.11\"}},\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"travel_class\":\"PREMIUM_ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TVRrc1VGSkZUVWxWVFh3eU1EZ3VNVEY4UlZWU2ZEazBNVFJqWWpVM0xUSTJOemN0TkRObVpDMDVNRE14TFRCbE56Z3lZVGxoTWpsbE5YeFhXQSUyNTNEJTI1M0QiLCJvZmZlcl9pZCI6IjMwIn0.ReOb208HnFy4jXJKX4raOI5QhgWak1ZRDQPSogzwBms\"},{\"outbound\":{\"duration\":\"01:35\",\"flights\":[{\"aircraft\":\"AR8\",\"operating_airline\":\"WX\",\"marketing_airline\":\"WX\",\"flight_number\":\"123\",\"departs_at\":\"2018-12-28T17:30\",\"arrives_at\":\"2018-12-28T19:05\",\"booking_info\":{\"seats_remaining\":9,\"booking_code\":\"A\",\"travel_class\":\"PREMIUM_ECONOMY\",\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"fare_basis\":\"A9FFWGB1\"},\"origin\":{\"airport\":\"LCY\"},\"destination\":{\"airport\":\"DUB\",\"terminal\":\"1\"}}]},\"fare\":{\"fees\":{\"creditcard_fees\":{\"FDA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FCA\":{\"currency\":\"GBP\",\"fee_amount\":\"3.95\",\"fee_percentage\":\"0.0\"},\"FPP\":{\"currency\":\"GBP\",\"fee_amount\":\"0.00\",\"fee_percentage\":\"0.0\"}}},\"currency\":\"EUR\",\"total_price\":\"208.11\",\"restrictions\":{\"refundable\":true,\"change_penalties\":false},\"price_per_adult\":{\"tax\":\"71.11\",\"total_fare\":\"208.11\"}},\"cabin_code\":\"W\",\"fare_family\":\"PREMIUM\",\"travel_class\":\"PREMIUM_ECONOMY\",\"merchant\":\"WX\",\"airline\":\"WX\",\"deep_link\":\"https://track.connect.travelaudience.com/dlv/verify_jwt/?params=eyJhbGciOiJIUzI1NiJ9.eyJhZmZfaWQiOjEwMTYsInVybCI6Imh0dHBzOi8vd3d3LmNpdHlqZXQuY29tL2Jvb2svTENZL0RVQi8xLWFkdWx0cy8wLWNoaWxkcmVuLzAtaW5mYW50LzIwMTgtMTItMjgvb25lLXdheS8_dGFuc2VsPVZqSjhWMWd4TWpNc1VGSkZUVWxWVFh3eU1EZ3VNVEY4UlZWU2ZEazBNVFJqWWpVM0xUSTJOemN0TkRObVpDMDVNRE14TFRCbE56Z3lZVGxoTWpsbE5YeFhXQSUyNTNEJTI1M0QiLCJvZmZlcl9pZCI6IjMwIn0.aDuUKrlc94CYvVEwL76g0z9mk90fYybM73QVHV11tDk\"}]}";
                String strParsedValue="";

                FlightsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(myResponse);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            Log.d("tag",jsonObject.toString() );
                            if (jsonArray != null){
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    System.out.println(" Don ");
                                    //Root object
                                    JSONObject rootObj = jsonArray.getJSONObject(i);


                                    //deeb Link object
                                    listDeepLink.add(rootObj.getString("deep_link").toString());
                                    //outbound object
                                    JSONObject fareObj = rootObj.getJSONObject("fare");
                                    listPrice.add(fareObj.getString("total_price").toString());

                                    JSONObject outboundObj = rootObj.getJSONObject("outbound");
                                    //   viewJson.append("\n"+outboundObj.getString("duration").toString());


                                    listBooking_info.add(outboundObj.getString("duration").toString());
                                    listDuration.add(outboundObj.getString("duration").toString());

                                    //get flight array
                                    JSONArray flightsArrayObj = outboundObj.getJSONArray("flights");
                                    for (int x = 0; x < flightsArrayObj.length(); x++) {

                                        JSONObject flightObj = flightsArrayObj.getJSONObject(x);
                                        String date = flightObj.getString("departs_at") + "  -  " + flightObj.getString("arrives_at");
                                        listDate.add(date);
                                    }


                                    //  JSONObject zeroObj=flightsObj.getJSONObject("0");


                                    list.setAdapter(adapter);
                                    //flights

                                    //0

                                    //aircraft

                                    //flight_number
                                    //departs_at
                                    //arrives_at

                                    //deep_link
                                } // end of For loop
                            }else{
                                Toast.makeText(FlightsActivity.this,"Empty try agine",Toast.LENGTH_LONG).show();
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
}
