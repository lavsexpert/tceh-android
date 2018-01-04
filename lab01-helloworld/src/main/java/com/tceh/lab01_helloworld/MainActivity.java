package com.tceh.lab01_helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "in onCreate()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause(){
        super.onPause();
        Toast.makeText(this, "in onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(){
        super.onStart();
        Toast.makeText(this, "in onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop(){
        super.onStop();
        Toast.makeText(this, "in onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        Toast.makeText(this, "in onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish(){
        super.finish();
        Toast.makeText(this, "in finish()", Toast.LENGTH_SHORT).show();
    }

    /* Запрешено переопределять этот метод
    @Override
    public void OnDestroy(){
        super.onDestroy();
        Toast.makeText(this, "in OnDestroy()", Toast.LENGTH_SHORT).show();
    }
    */
}
