package view.edit.input.englishphonetictobangla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textInfo, liveText;
    private LinearLayout linearLayout;
    private Button demo, demo1, demo2, demo3, demo4, submit;
    private EditText editText;

    private Translator translator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textInfo = findViewById(R.id.info);
        liveText = findViewById(R.id.livetext);

        linearLayout = findViewById(R.id.show_bangla_text_layout);

        demo = findViewById(R.id.demo);
        demo1 = findViewById(R.id.demo1);
        demo2 = findViewById(R.id.demo2);
        demo3 = findViewById(R.id.demo3);
        demo4 = findViewById(R.id.demo4);
        submit = findViewById(R.id.btn_submit);

        editText = findViewById(R.id.edit_text);

        translator = new Translator(this, editText, liveText,
                linearLayout, demo, demo1, demo2, demo3, demo4);

        translator.ShoworHideKeyboardAutoSuggestion();
        translator.switchKeyboard();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                translator.callGoogleTranslate("en", "bn", ""); // dummy request
            }
        });
        t.start();

        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String text = editText.getText().toString();
                if (!TextUtils.isEmpty(text)) {
                    textInfo.setText("" + text);
                }
                break;
        }
    }
}
