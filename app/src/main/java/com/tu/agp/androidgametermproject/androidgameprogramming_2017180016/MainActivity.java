package com.tu.agp.androidgametermproject.androidgameprogramming_2017180016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView subTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subTextView = findViewById(R.id.subText);
        subTextView.setText("i am a good programmer");


    }


    public void onBtnPushMe(View view) {
//        TextView tv = findViewById(R.id.subText);
        subTextView.setText("Clicked \\(OoO)/");
    }
}