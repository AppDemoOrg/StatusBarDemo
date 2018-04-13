package com.pi.pilot.support;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pi.pilot.support.swipback.SwipBackManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipBackManager.addSwipBackList(this);
    }
}
