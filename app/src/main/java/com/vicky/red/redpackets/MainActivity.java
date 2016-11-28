package com.vicky.red.redpackets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vicky.red.redpackets.view.PeriscopeLayout;
import com.vicky.red.redpackets.view.RedPacketsLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PeriscopeLayout periscopeLayout = (PeriscopeLayout) findViewById(R.id.periscope);
        periscopeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                periscopeLayout.addHeart();
            }
        });

        final RedPacketsLayout redPacketsLayout = (RedPacketsLayout) findViewById(R.id.packets_layout);
        redPacketsLayout.post(new Runnable() {
            @Override
            public void run() {
                redPacketsLayout.startRain();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        redPacket.post(new Runnable() {
//            @Override
//            public void run() {
//                redPacket.startRain();
//            }
//        });


    }
}
