package com.example.goldenblack.abs.navigation_button_booking;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.example.goldenblack.abs.*;
//import com.airline.abs.abs.databinding.RowListviewBinding;

//tTDC2o18oBU44UYqEOtZ0IGEstCubEf3

public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    /*
        private final String[] deep_link;
        private final  String[] price;
        private final  String[] date;
        private final  String[] booking_info;
        private final  String[] duration;
    */
    ArrayList<String> listPrice;
    ArrayList<String> listDate;
    ArrayList<String> listBooking_info;
    ArrayList<String> listDuration;
    ArrayList<String> listDeepLink;

    public CustomAdapter(Activity context, ArrayList<String> listPrice,ArrayList<String> listDate, ArrayList<String> listBooking_info,ArrayList<String> listDuration,ArrayList<String> listDeepLink) {
        super(context, R.layout.row_listview, listPrice);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.listPrice =listPrice;
        this.listDate=listDate;
        this.listBooking_info=listBooking_info;
        this.listDuration =listDuration;
        this.listDeepLink=listDeepLink;

    }
/*
    public CustomAdapter(Activity context, String[] deep_link,String[] price, String[] date,String[] booking_info,String[] duration) {
        super(context, R.layout.row_listview, deep_link);
        // TODO Auto-generated constructor stub
        this.context=context;
        this.deep_link=deep_link;
        this.price=price;
        this.date=date;
        this.booking_info=booking_info;
        this.duration=duration;

    }
*/
   /* public CustomAdapter(Activity context, String[] maintitle,String[] subtitle, Integer[] imgid) {
        super(context, R.layout.row_listview, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;

    }*/

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row_listview, null,true);

        TextView total_priceView = (TextView) rowView.findViewById(R.id.total_price);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView dateView = (TextView) rowView.findViewById(R.id.date);
        TextView booking_infoView = (TextView) rowView.findViewById(R.id.booking_info);
        TextView durationView = (TextView) rowView.findViewById(R.id.duration);
        Button Booking  = (Button)rowView.findViewById(R.id.booking);

        total_priceView.setText(listPrice.get(position)+ " SDG");
        imageView.setImageResource(R.drawable.airplane);
        dateView.setText(listDate.get(position));
        booking_infoView.setText(listBooking_info.get(position));
        durationView.setText(listDuration.get(position));

        Booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,bookingSiteView.class);
                i.putExtra("url",listDeepLink.get(position));
                context.startActivity(i);
            }
        });
        return rowView;
    };
}
