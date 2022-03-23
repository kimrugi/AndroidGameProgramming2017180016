package kr.ac.tukorea.k2017180016.card;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private static final String tag = MainActivity.class.getSimpleName();
    private static final int[] btnIds = new int[]{
            R.id.card00,R.id.card01,R.id.card02,R.id.card03,
            R.id.card10,R.id.card11,R.id.card12,R.id.card13,
            R.id.card20,R.id.card21,R.id.card22,R.id.card23,
            R.id.card30,R.id.card31,R.id.card32,R.id.card33
    };
    private int[] resIds = new int[]{
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh,
            R.mipmap.card_as,R.mipmap.card_2c,R.mipmap.card_3d,R.mipmap.card_4h,
            R.mipmap.card_5s,R.mipmap.card_jc,R.mipmap.card_kd,R.mipmap.card_qh
    };

    private ImageButton previousButton;
    private int flips = 16;
    private int openCardCount;
    private TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scoreTextView = findViewById(R.id.scoreBoard);
        
        startGame();
    }

    private void startGame() {
        Random random = new Random();
        for(int i = 0; i < resIds.length; ++i){
            int t = random.nextInt(resIds.length);
            int id = resIds[i];
            resIds[i] = resIds[t];
            resIds[t] = id;
        }
        for(int i = 0; i < btnIds.length; ++i) {
            int resid = resIds[i];
            ImageButton btn = findViewById(btnIds[i]);
            btn.setVisibility(View.VISIBLE);
            btn.setTag(resid);
            //btn.setImageResource(R.mipmap.card_blue_back);
            btn.setImageResource(resIds[i]);
        }
        openCardCount = resIds.length;
        setScore(0);
        previousButton = null;
    }

    public void onBtnRestart(View view) {
        Log.d(tag, "Pressed Restart Button");
        flips = 0;
        askRetry();
    }

    private void askRetry() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.restart)
                .setMessage(R.string.restart_alert_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startGame();
                    }
                })
                .setNegativeButton(R.string.no, null)
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
                Toast.makeText(this, R.string.same_button_pressed, Toast.LENGTH_SHORT).show();
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
                setScore(flips+1);
                openCardCount = openCardCount - 2;
                imageButton.setVisibility(View.INVISIBLE);
                previousButton.setVisibility(View.INVISIBLE);
                previousButton = null;
                if (openCardCount <= 0){
                    askRetry();
                }
            }
        }
        previousButton = imageButton;
    }

    private void setScore(int flips) {
        this.flips = flips;

        Resources res = getResources();
        String fmt = res.getString(R.string.flips_fmt);
        String text = String.format(fmt, flips);
        scoreTextView.setText(text);
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