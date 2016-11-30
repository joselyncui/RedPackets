package com.vicky.red.redpackets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vicky.red.redpackets.view.RedPacketsSurfaceVew;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnBezier = (Button) findViewById(R.id.btn_bezier);
        Button btnSurface = (Button) findViewById(R.id.btn_surfaceview);
        Button btnBezierSurface = (Button) findViewById(R.id.btn_bezier_surfaceview);

        btnBezier.setOnClickListener(this);
        btnSurface.setOnClickListener(this);
        btnBezierSurface.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_bezier:
                Intent bezierIntent = new Intent(this,BezierActivity.class);
                startActivity(bezierIntent);
                break;
            case R.id.btn_surfaceview:
                Intent surfaceIntent = new Intent(this,SurfaceActivity.class);
                startActivity(surfaceIntent);
                break;
            case R.id.btn_bezier_surfaceview:
                Intent bezierSurfaceIntent = new Intent(this,BezierSurfaceActivity.class);
                startActivity(bezierSurfaceIntent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
