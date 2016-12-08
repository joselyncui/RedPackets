package com.vicky.red.redpackets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vicky.red.redpackets.view.meteorshower.MeteorShowerSurface;

public class MeteorShowerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteor_shower);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final MeteorShowerSurface meteorShowerSurface = (MeteorShowerSurface) findViewById(R.id.meteor_surface);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/


//                meteorShowerSurface.addMeteor();
            }
        });

        meteorShowerSurface.post(new Runnable() {
            @Override
            public void run() {
                meteorShowerSurface.setDuration(10*1000).setRedCount(100).start();
            }
        });
    }

}
