package com.tu.agp.androidgametermproject.androidgameprogramming_2017180016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView pageNumberText;
    private int pageNumber;
    private ImageView catImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        catImage = findViewById(R.id.catImage);
        pageNumberText = findViewById(R.id.pageNumber);
        pageNumber = 1;

    }

    public void prevBtn(View view) {

    }

    public void nextBtn(View view) {

    }


}