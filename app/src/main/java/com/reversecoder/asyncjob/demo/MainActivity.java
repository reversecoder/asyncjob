package com.reversecoder.asyncjob.demo;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.reversecoder.asyncjob.JavaCoroutine;
import com.reversecoder.asyncjob.wrapper.JobWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("CheckThread", "CheckThread>>onCreate>>UI thread: " + Looper.getMainLooper().getThread().getId() + "");
        boolean isOnUiThread = Thread.currentThread() == Looper.getMainLooper().getThread();
        Log.d("CheckThread", "CheckThread>>onCreate>>isOnUiThread: " + isOnUiThread);

        JavaCoroutine crouton = new JavaCoroutine();
        JobWrapper jobWrapper = crouton.async(new Runnable() {
            @Override
            public void run() {
                Log.d("CheckThread", "CheckThread>>onCreate>>jobWrapper>>BG thread: " + Thread.currentThread().getId() + "");
                boolean isOnUiThread = Thread.currentThread() == Looper.getMainLooper().getThread();
                Log.d("CheckThread", "CheckThread>>onCreate>>jobWrapper>>isOnUiThread: " + isOnUiThread);

                ((TextView) findViewById(R.id.tv_text)).setText("Rashed");
            }
        });
//        jobWrapper.stop();
    }
}