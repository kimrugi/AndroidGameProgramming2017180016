package com.tu.agp.androidgametermproject.androidgameprogramming_2017180016;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private View prevBtn;
    private View nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        
        prevBtn = findViewById(R.id.prevBtn);
        nextBtn = findViewById(R.id.nextBtn);

        catImage = findViewById(R.id.catImage);
        pageNumberText = findViewById(R.id.pageNumber);
        pageNumber = 1;
        setPage(pageNumber);
    }

    public void onPrevBtn(View view) {
        setPage(pageNumber-1);

    }

    public void onNextBtn(View view) {
        setPage(pageNumber+1);

    }

    private void setPage(int pageNum){
        if(pageNum <= 1){
            prevBtn.setEnabled(false);
        }else{
            prevBtn.setEnabled(true);
        }
        if(pageNum >= 5){
            nextBtn.setEnabled(false);
        }else{
            nextBtn.setEnabled(true);
        }
        if(pageNum < 1 || pageNum > 5) return;
        pageNumber = pageNum;
        pageNumberText.setText(pageNumber+"/5");
        catImage.setImageResource(imageIndex[pageNumber-1]);

    }


}