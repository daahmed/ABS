package com.example.goldenblack.abs.drawer;

import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goldenblack.abs.*;

public class checkin_flight_fragment extends Fragment {
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view  =inflater.inflate(R.layout.fragment_checkin_flight, container, false);

        return view;
    }
}