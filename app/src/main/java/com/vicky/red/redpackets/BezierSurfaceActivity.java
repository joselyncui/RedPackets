package com.vicky.red.redpackets;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vicky.red.redpackets.view.RedPacketsSurfaceVew;

public class BezierSurfaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier_surface);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RedPacketsSurfaceVew redPacketsSurfaceVew = (RedPacketsSurfaceVew) findViewById(R.id.bezier_surface);
        redPacketsSurfaceVew.post(new Runnable() {
            @Override
            public void run() {
                redPacketsSurfaceVew.startRain();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

}
