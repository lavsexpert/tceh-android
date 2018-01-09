package com.tceh.lab13_sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "PREFS";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPrefs();
        final EditText userName = (EditText) findViewById(R.id.user_name);
        userName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String text = userName.getText().toString();
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                    savePrefs("Text", text);
                    return true;
                }
                savePrefs("Text", text);
                return false;
            }
        });
    }
    public void onButtonClicked(View v) {
        Toast.makeText(this, "Кнопка нажата", Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View v) {
        String text;
        if (((CheckBox) v).isChecked()) {
            text = "Отмечено";
        } else {
            text = "Не отмечено";
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        savePrefs("Checkbox", text);
    }

    public void onToggleClicked(View v) {
        String text;
        if (((ToggleButton) v).isChecked()) {
            text = "Включено";
        } else {
            text = "Выключено";
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        savePrefs("Toggle", text);
    }

    public void onRadioButtonClicked(View v) {
        RadioButton rb = (RadioButton) v;
        String text = (String) rb.getText();
        Toast.makeText(this, "Выбрано растение: " + text, Toast.LENGTH_SHORT).show();
        savePrefs("Radio", text);
    }

    public void onClearEditText(View v) {
        EditText userName = (EditText) findViewById(R.id.user_name);
        userName.setText("");
        Toast.makeText(this, "Текст очищен", Toast.LENGTH_SHORT).show();
        savePrefs("Text", "");
    }

    public void savePrefs(String station, String text){
        String editorString;
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(station, text);
        editor.commit();
    }

    public void loadPrefs(){
        final String NOTHING_SELECTED = "";
        String text;
        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        EditText userName = (EditText) findViewById(R.id.user_name);
        text = prefs.getString("Text", NOTHING_SELECTED);
        userName.setText(text);

        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        text = prefs.getString("Checkbox", NOTHING_SELECTED);
        checkBox.setChecked(text.equals("Отмечено"));

        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.togglebutton);
        text = prefs.getString("Toggle", NOTHING_SELECTED);
        toggleButton.setChecked(text.equals("Включено"));

        text = prefs.getString("Radio", NOTHING_SELECTED);
        if (text.equals("Фиалка")){
            RadioButton radioButton = (RadioButton) findViewById(R.id.radio_violet);
            radioButton.setChecked(true);
        };
        if (text.equals("Кактус")){
            RadioButton radioButton = (RadioButton) findViewById(R.id.radio_cactus);
            radioButton.setChecked(true);
        };
        if (text.equals("Тыква")){
            RadioButton radioButton = (RadioButton) findViewById(R.id.radio_pumkin);
            radioButton.setChecked(true);
        };
    }
}
