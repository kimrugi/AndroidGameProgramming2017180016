package kr.ac.tukorea.g2017180016.layouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String tag = MainActivity.class.getSimpleName();
    private CheckBox checkbox;
    private TextView outputTextView;
    private EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkbox = findViewById(R.id.checkbox);
        outputTextView = findViewById(R.id.textView);
        nameEdit = findViewById(R.id.nameEdit);

        nameEdit.addTextChangedListener(nameWatcher);
    }

    public void onCheckBox(View view) {
        CheckBox cb = (CheckBox) view;
        Log.d(tag, "checkbox state: " + cb.isChecked());
    }

    public void onBtnDoIt(View view) {
        Log.d(tag, "check state: " + checkbox.isChecked());
        String text = nameEdit.getText().toString();
        outputTextView.setText(text);
    }
    private final TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.v(tag, "before");
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            Log.d(tag, "textchange: "+ charSequence);
            outputTextView.setText("Text Length: " + charSequence.length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            Log.v(tag, "after");
        }
    };
}