package com.anwesome.ui.orbitviewlistdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.orbitviewlist.OrbitView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrbitView orbitView = new OrbitView(this);
        setContentView(orbitView);
    }
}
