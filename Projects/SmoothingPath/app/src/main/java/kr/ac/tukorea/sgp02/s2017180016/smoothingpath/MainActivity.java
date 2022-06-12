package kr.ac.tukorea.sgp02.s2017180016.smoothingpath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private PathView pathView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        pathView = findViewById(R.id.pathView);
        pathView.setListener(new PathView.Listener() {
            @Override
            public void onAdd() {
                showCount();
            }
        });
        showCount();
    }

    private void showCount() {
        int count = pathView.getPointCount();
        String text = "Count: " + count;
        textView.setText(text);
    }

    public void onBtnClear(View view){
        pathView.clear();
        showCount();
    }

    public void onBtnStart(View view) {
        pathView.start();
    }
}