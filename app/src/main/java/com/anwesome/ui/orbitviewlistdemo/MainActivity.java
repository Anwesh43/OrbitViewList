package com.anwesome.ui.orbitviewlistdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.anwesome.ui.orbitviewlist.OnFillChangeLister;
import com.anwesome.ui.orbitviewlist.OrbitList;
import com.anwesome.ui.orbitviewlist.OrbitView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrbitList orbitList = new OrbitList(this);
        for(int i=0;i<24;i++) {
            final int index = i;
            orbitList.addOrbit(new OnFillChangeLister() {
                @Override
                public void onFill() {
                    Toast.makeText(MainActivity.this, String.format("%d is filled",index+1), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUnFill() {
                    Toast.makeText(MainActivity.this, String.format("%d is unfilled",index+1), Toast.LENGTH_SHORT).show();
                }
            });
        }
        orbitList.show();
    }
}
