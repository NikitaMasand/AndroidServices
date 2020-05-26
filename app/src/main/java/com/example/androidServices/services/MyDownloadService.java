package com.example.androidServices.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.androidServices.DownloadThread;
import com.example.androidServices.MainActivity;

public class MyDownloadService extends Service {

    private static final String TAG = "MyTag";
    private DownloadThread mDownloadThread;

    //this is started service
    public MyDownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate: called");
        mDownloadThread = new DownloadThread();
        mDownloadThread.start();

        while (mDownloadThread.mHandler==null){

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.d(TAG,"onStartCommand: called");

        final String songName = intent.getStringExtra(MainActivity.MESSAGE_KEY);
        Message msg = Message.obtain();
        msg.obj = songName;
        mDownloadThread.mHandler.sendMessage(msg);
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind: called");
        return null; //implies it's a started service
    }

    private void downloadSong(final String songName) {
        Log.d(TAG,"run: starting download");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"downloadSong: "+songName+" Downloaded......");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }
}
