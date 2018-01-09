package com.tceh.lab14_file;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    boolean mStorageAvailable = false;
    boolean mStorageWriteable = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStorage(mStorageAvailable, mStorageWriteable);
        if (mStorageAvailable) {
            Toast.makeText(this, "Внешний носитель недоступен", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mStorageWriteable) {
            Toast.makeText(this, "Не хватает прав на запись на внешний носитель", Toast.LENGTH_SHORT).show();
            return;
        }

        File f = new File(getExternalCacheDir(), "samplefile.txt" );
        OutputStream os;
        try {
            os = new FileOutputStream(f);
            byte[] buff = "Успешно".getBytes();
            os.write(buff);
            Toast.makeText(this, "Запись прошла успешно", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Файл не найден: "
                    + getExternalCacheDir()+"samplefile.txt", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Не известная ошибка с файлом: "
                    + getExternalCacheDir()+"samplefile.txt", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickButton(View v){
        if (mStorageAvailable) {
            Toast.makeText(this, "Внешний носитель недоступен", Toast.LENGTH_SHORT).show();
            return;
        }
        File f = new File(getExternalCacheDir(), "samplefile.txt");
        InputStream is;
        try {
            is = new FileInputStream(f);
            byte[] buff = new byte[is.available()];
            is.read(buff);
            is.close();
            String text = new String(buff);
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Файл не найден: "
                    + getExternalCacheDir()+"samplefile.txt", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Не известная ошибка с файлом: "
                    + getExternalCacheDir()+"samplefile.txt", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkStorage(boolean mStorageAvailable, boolean mStorageWriteable) {
        String storageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(storageState)) {
            mStorageAvailable = mStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(
                storageState)) {
            mStorageAvailable = true;
            mStorageWriteable = false;
        } else { mStorageAvailable = mStorageWriteable = false; }
    }
}
