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
    public SeekBar musicBar;
    static private ChartMakingActivity activity;

    static public ChartMakingActivity getActivity() {return activity;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_making);
        musicBar = findViewById(R.id.musicSeekBar);
        //musicBar.setMax(100);
        musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                MainGame.getInstance().pauseMusic();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int time = seekBar.getProgress();
                MainGame.getInstance().changeMusicProgress(time);
                MainGame.getInstance().startMusic();
            }
        });
        ChartMakingActivity.activity = this;
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

    public void setMusicTime(int time){
        musicBar.setProgress(time);
    }
}