package com.example.goldenblack.abs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.goldenblack.abs.drawer.navigation_drawer;

public class MainActivity extends navigation_drawer {
int x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
