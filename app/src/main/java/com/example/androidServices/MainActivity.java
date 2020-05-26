package com.example.androidServices;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.androidServices.services.MusicPlayerService;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyTag";
    private ScrollView mScroll;
    private TextView mLog;
    private ProgressBar mProgressBar;
    private Button mPlayButton;
    public static final String MESSAGE_KEY = "message_key";
    private MusicPlayerService mMusicPlayerService;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MusicPlayerService.MyServiceBinder myServiceBinder = (MusicPlayerService.MyServiceBinder) iBinder;
            mMusicPlayerService = myServiceBinder.getService();
            mBound = true;
            Log.d(TAG, "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
        bindService(intent,mServiceConn, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(getApplicationContext())
                                .registerReceiver(mReceiver, new IntentFilter(MusicPlayerService.MUSIC_COMPLETE));
    }

    private boolean mBound = false;

    private void initViews() {
        mScroll = (ScrollView) findViewById(R.id.scrollLog);
        mLog = (TextView) findViewById(R.id.tvLog);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mPlayButton = findViewById(R.id.btnPlayMusic);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String res = intent.getStringExtra(MESSAGE_KEY);
            if(res=="done") {
                mPlayButton.setText("Play");
            }
            Log.d(TAG, "onReceive: ThreadName: "+Thread.currentThread().getName());
        }
    };

    public void onBtnMusicClicked(View view) {
        if(mBound) {
            if(mMusicPlayerService.isPlaying()) {
                mMusicPlayerService.pause();
                mPlayButton.setText("Play");
            }
            else {
                Intent intent = new Intent(MainActivity.this, MusicPlayerService.class);
                startService(intent);
                mMusicPlayerService.play();
                mPlayButton.setText("Pause");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound) {
            unbindService(mServiceConn);
            mBound = false;
        }

        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(mReceiver);
    }

    public void runCode (View view) {
//        log(mMusicPlayerService.getValue());
        log("Playing music!!");
        displayProgressBar(true);

        //send intent to download service
        //startservice:
//
//        for(String song : Playlist.songs) {
//            Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
//            intent.putExtra(MESSAGE_KEY, song);
//            startService(intent);
//        }
        }



    public void clearOutput(View v) {
//        Intent intent = new Intent(MainActivity.this, MyDownloadService.class);
//        stopService(intent);
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
