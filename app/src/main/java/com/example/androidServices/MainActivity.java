package com.example.androidServices;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.androidServices.R;
import com.example.androidServices.services.MyDownloadService;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;
    public static final String MESSAGE_KEY = "message_key";

    private void initViews() {
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }
    public void runCode (View view) {
        log("Running code");
        displayProgressBar(true);

        //send intent to download service
        for(String song : Playlist.songs) {
            Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
            intent.putExtra(MESSAGE_KEY, song);
            startService(intent);
        }
        }



    public void clearOutput(View v) {
        Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
        stopService(intent);
        mLog.setText("");
        scrollTextToEnd();
    }

    public void log (String message) {
        Log.i(TAG, message);
        mLog.append(message + "\n");
        scrollTextToEnd();
    }

    private void scrollTextToEnd() {
        mScroll.post(new Runnable() {
            @Override
            public void run() {
                mScroll.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void displayProgressBar(boolean display) {
        if(display){
            mProgressBar.setVisibility(View.VISIBLE);
        }
        else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }


}
