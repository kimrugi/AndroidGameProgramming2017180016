package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game.MainGame;

public class ChartMakingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_making);
        SeekBar musicBar = findViewById(R.id.musicSeekBar);
        //musicBar.setMax(100);
        musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MainGame.getInstance().changeMusicProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onStartButton(View view) {
        MainGame.getInstance().startMusic();

        EditText bpsEdit = findViewById(R.id.BPSEditText);
        if(bpsEdit.isEnabled()){
            bpsEdit.setEnabled(false);
            String str = bpsEdit.getText().toString();
            if(!str.isEmpty()){
                int bps = Integer.parseInt(str);
                MainGame.getInstance().setBps(bps);
            }
        }
    }

    public void onPauseButton(View view) {
        MainGame.getInstance().pauseMusic();
    }

    public void onArrowButton(View view) {
        MainGame.getInstance().changeToArrowMode();
    }

    public void onFinishButton(View view) {
        MainGame.getInstance().finishSaveJSON();
    }
}