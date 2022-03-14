package com.tu.agp.androidgametermproject.androidgameprogramming_2017180016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.subText);
        tv.setText("i am a good programmer");


    }


    public void onBtnPushMe(View view) {
        TextView tv = findViewById(R.id.subText);
        tv.setText("Clicked \\(OoO)/");
    }
}