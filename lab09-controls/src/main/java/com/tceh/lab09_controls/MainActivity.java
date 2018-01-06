package com.tceh.lab09_controls;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText userName = (EditText) findViewById(R.id.user_name);
        userName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(getApplicationContext(), userName.getText(),
                            Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    public void onButtonClicked(View v) {
        Toast.makeText(this, "Кнопка нажата", Toast.LENGTH_SHORT).show();
    }

    public void onCheckboxClicked(View v) {
        if (((CheckBox) v).isChecked()) {
            Toast.makeText(this, "Отмечено", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Не отмечено", Toast.LENGTH_SHORT).show();
        }
    }

    public void onToggleClicked(View v) {
        if (((ToggleButton) v).isChecked()) {
            Toast.makeText(this, "Включено", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Выключено", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClicked(View v) {
        RadioButton rb = (RadioButton) v;
        Toast.makeText(this, "Выбрано растение: " + rb.getText(),
                Toast.LENGTH_SHORT).show();
    }

    public void onClearEditText(View v) {
        final EditText userName = (EditText) findViewById(R.id.user_name);
        userName.setText("");
        Toast.makeText(this, "Текст очищен", Toast.LENGTH_SHORT).show();
    }


}
