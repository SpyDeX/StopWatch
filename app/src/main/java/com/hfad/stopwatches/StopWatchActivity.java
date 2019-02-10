package com.hfad.stopwatches;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Locale;

public class StopWatchActivity extends Activity {

    private final String BND_TOTAL = "TOTAL";
    private final String BND_TIMER = "TIMER";
    private final String BND_RUNNING = "RUNNING";
    private final String BND_WASRUNNING = "WASRUNNING";

    private long CurrentCycle = 0;
    private long beginTime = 0;
    private boolean running = false;
    private boolean wasRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        if (savedInstanceState != null) {
            CurrentCycle = savedInstanceState.getLong(BND_TOTAL);
            beginTime = savedInstanceState.getLong(BND_TIMER);
            running = savedInstanceState.getBoolean(BND_RUNNING);
            wasRunning = savedInstanceState.getBoolean(BND_WASRUNNING);
        }

        //beginTime = new Date().getTime();
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(BND_TOTAL, CurrentCycle);
        outState.putLong(BND_TIMER, beginTime);
        outState.putBoolean(BND_RUNNING, running);
        outState.putBoolean(BND_WASRUNNING, wasRunning);
    }

    @Override
    protected void onStop() {
        super.onStop();

        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (wasRunning)
            running = true;
    }

    public void onClickStart(View view){
        if (!running)
            beginTime = new Date().getTime();
        running = true;
    }

    public void onClickStop(View view){
        if (running)
            CurrentCycle = CurrentCycle + new Date().getTime() - beginTime;
        running = false;
    }

    public void onClickReset(View view){
        // running = false;
        beginTime = beginTime = new Date().getTime();
        CurrentCycle = 0;
    }

    public void runTimer() {
        final TextView timeView = findViewById(R.id.textView);

        final Handler handler = new Handler();

        handler.post(new Runnable() {
                         @Override
                         public void run() {

                             long fDt = new Date().getTime();

                             long seconds = CurrentCycle;
                             if (running)
                                 seconds = seconds + (fDt - beginTime);

                             long fMSec = (seconds % 1000);

                             seconds = seconds / 1000;

                             long fHrs = (seconds /3600);
                             long fMns = (seconds % 3600) / 60;
                             long fSec = (seconds % 60);

                             timeView.setText( String.format("%d:%02d:%02d:%03d", fHrs, fMns, fSec, fMSec) );

                             handler.postDelayed(this, 55);
                         }
                     }

        );
    }

}
