package com.tu.agp.androidgametermproject.androidgameprogramming_2017180016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final int I = 0;
    private TextView pageNumberText;
    private int pageNumber;
    private ImageView catImage;
    private static final int[] imageIndex = {
            R.mipmap.cat1,
            R.mipmap.cat2,
            R.mipmap.cat3,
            R.mipmap.cat4,
            R.mipmap.cat5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImage = findViewById(R.id.catImage);
        pageNumberText = findViewById(R.id.pageNumber);
        pageNumber = 1;
        setPage(pageNumber);
    }

    public void prevBtn(View view) {
        setPage(pageNumber-1);
    }

    public void nextBtn(View view) {
        setPage(pageNumber+1);
    }

    private void setPage(int pageNum){
        if(pageNum < 1 || pageNum > 5) return;
        pageNumber = pageNum;
        pageNumberText.setText(pageNumber+"/5");
        catImage.setImageResource(imageIndex[pageNumber-1]);

    }


}