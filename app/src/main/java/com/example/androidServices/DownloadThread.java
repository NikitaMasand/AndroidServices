package com.example.androidServices;

import android.os.Looper;

public class DownloadThread extends Thread {
    private static final String TAG = "MyTag";
    public DownloadHandler mHandler;

    public DownloadThread(){

    }

    public void run(){
        Looper.prepare();
        mHandler = new DownloadHandler();
        Looper.loop();
    }
}
