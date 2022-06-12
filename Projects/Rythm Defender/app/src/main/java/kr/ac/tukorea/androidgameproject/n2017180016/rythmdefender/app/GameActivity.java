package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;
import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.game.MainGame;

public class GameActivity extends AppCompatActivity {
    public static final String MUSIC = "MUSIC_FILE_NAME";
    public static final String CHART = "CHART_FILE_NAME";
    public static final String IMAGE = "IMAGE_FILE_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            finish();
            return;
        }
        String musicFile = extras.getString(GameActivity.MUSIC);
        String chartFile = extras.getString(GameActivity.CHART);
        String imageFile = extras.getString(GameActivity.IMAGE);
        MainGame.getInstance().setMusic(musicFile);
        MainGame.getInstance().setChart(chartFile);
        MainGame.getInstance().setBackGround(imageFile);

        setContentView(R.layout.activity_game);
    }

    @Override
    public void onBackPressed() {
        MainGame.getInstance().onBackPressed();
    }
}