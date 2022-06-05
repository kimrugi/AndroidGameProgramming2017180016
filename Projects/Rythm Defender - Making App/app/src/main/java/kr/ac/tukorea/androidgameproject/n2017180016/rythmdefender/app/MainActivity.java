package kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import kr.ac.tukorea.androidgameproject.n2017180016.rythmdefender.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //퍼미션 요청
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                MODE_PRIVATE);

        setContentView(R.layout.activity_main);
        if(!checkExternalStorage()){
            finishActivity(0);
        }

        Intent intent = new Intent(this, ChartMakingActivity.class);
        startActivity(intent);
    }

    public void onStartButton(View view) {

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public boolean checkExternalStorage() {
        String state = Environment.getExternalStorageState();
        // 외부메모리 상태
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 읽기 쓰기 모두 가능
            Log.d("STATE", "외부메모리 읽기 쓰기 모두 가능");
            Toast.makeText(getApplicationContext(),"외부메모리 읽기 쓰기 모두 가능",Toast.LENGTH_SHORT).show();
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            //읽기전용
            Log.d("STATE", "외부메모리 읽기만 가능");
            Toast.makeText(getApplicationContext(),"외부메모리 읽기만 가능",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            // 읽기쓰기 모두 안됨
            Log.d("STATE", "외부메모리 읽기쓰기 모두 안됨 : "+ state);
            Toast.makeText(getApplicationContext(),"외부메모리 읽기쓰기 모두 안됨 : "+ state,Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}