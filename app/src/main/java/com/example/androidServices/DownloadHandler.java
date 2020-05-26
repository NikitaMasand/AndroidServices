package com.example.androidServices;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownloadHandler extends Handler {
    private static final String TAG = "MyTag";

    public DownloadHandler() {
    }

    public void handleMessage(Message message){
        downloadSong(message.obj.toString());
    }

    private void downloadSong(String songName) {
        Log.d(TAG,"run: starting download");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "download song: "+songName+" Downloaded");

    }
}
