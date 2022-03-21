package kr.ac.tukorea.k2017180016.card;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String tag = MainActivity.class.getSimpleName();
    private static final int[] btnIds = new int[]{
            R.id.card01,R.id.card01,R.id.card02,R.id.card03,
            R.id.card10,R.id.card11,R.id.card12,R.id.card13,
            R.id.card20,R.id.card21,R.id.card22,R.id.card23,
            R.id.card30,R.id.card31,R.id.card32,R.id.card33
    };
    private int[] cardimage = new int[]{
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh,
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh
    };

    private ImageButton previousButton;
    private int flips = 0;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTextView = findViewById(R.id.scoreBoard);
        
        startGame();
    }

    private void startGame() {
        for(int i = 0; i < btnIds.length; ++i) {
            int resid = cardimage[i];
            ImageButton btn = findViewById(btnIds[i]);
            btn.setTag(resid);
        }
    }

    public void onBtnRestart(View view) {
        Log.d(tag, "Pressed Restart Button");
        flips = 0;
        askRetry();
    }

    private void askRetry() {
        new AlertDialog.Builder(this)
                .setTitle("Restart")
                .setMessage("Really restart game?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                })
                .setNegativeButton("no", null)
                .create().show();
    }

    public void onBtnCard(View view) {
        if(!(view instanceof ImageButton)){
            Log.d(tag, "view is not ImageView");
            return;
        }
        ImageButton imageButton = (ImageButton) view;
        if(previousButton != null) {
            if (imageButton == previousButton) {
                Log.d(tag, "Same Button Pressed");
                return;
            }
            Log.d(tag, "Pressed Card" + imageButton.getId());

            int resid = (Integer) imageButton.getTag();
            imageButton.setImageResource(resid);

            int prevResid;
            prevResid = (Integer) previousButton.getTag();
            if (resid != prevResid) {
                previousButton.setImageResource(R.mipmap.card_blue_back);
            } else {
                setScore(flips);
                imageButton.setVisibility(View.INVISIBLE);
                previousButton.setVisibility(View.INVISIBLE);
                previousButton = null;
            }
        }
        previousButton = imageButton;
    }

    private void setScore(int flips) {
        this.flips = flips;

        scoreTextView.setText("Score: " + flips);
    }

    private int findBtnIndex(int id) {
        for(int i = 0; i < btnIds.length; ++i){
            if(id == btnIds[i]) {
                return i;
            }
        }
        Log.d(tag, "Can't find " + id);
        return -1;
    }
}