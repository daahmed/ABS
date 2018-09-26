package com.example.goldenblack.abs.drawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.goldenblack.abs.R;

public class information_fragment extends Fragment {
    public View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view  =inflater.inflate(R.layout.fragment_information, container, false);

        return view;
    }
}
