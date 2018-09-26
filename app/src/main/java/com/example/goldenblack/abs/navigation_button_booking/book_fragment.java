package com.example.goldenblack.abs.navigation_button_booking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.goldenblack.abs.R;
import com.example.goldenblack.abs.home_fragment;
import com.example.goldenblack.abs.navigation_button_booking.flightSearchFragment;

public class book_fragment extends Fragment  {
   public View view;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadFragment(new flightSearchFragment());
                    return true;
                case R.id.navigation_dashboard:
                    loadFragment(new home_fragment());
                    return true;
                case R.id.navigation_notifications:
                    loadFragment(new home_fragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        view  =inflater.inflate(R.layout.activity_home_navigation, container, false);
        mTextMessage = (TextView) view.findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // set first fragment active
        loadDefultFragment();
        return view;
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.navigationBottomBookingFrameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
    private void loadDefultFragment() {
        Fragment fragment = new flightSearchFragment();
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.navigationBottomBookingFrameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}