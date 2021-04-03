package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
int seconds = 30;
boolean isTimerRunning = false;
CountDownTimer timer;
TextView timerView ;
Button btn;
SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerView = (TextView)  findViewById(R.id.timerView);
        btn =  (Button) findViewById(R.id.button2);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMin(1);
        seekBar.setMax(540);
        seekBar.setProgress(30);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                   if (isTimerRunning) {
                       seekBar.setOnTouchListener(new View.OnTouchListener() {

                           @Override
                           public boolean onTouch(View v, MotionEvent event) {
                               return true;
                           }
                       });
                   }

                   seconds = progress;
                   int mins = progress / 60;
                   int secs = progress % 60;
                  timerView.setText(secs > 9 ? "0" + mins + ":" + secs : "0" + mins + ":0" + secs);
               }

               //
               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });
       }

    public void stopTimer() {
        timer.cancel();
        timerView.setText("00:30");
        btn.setText("Go");
        isTimerRunning = false;
        seekBar.setProgress(30);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    public  void handleClick(View view) {

        if (!isTimerRunning) {

           timer =  new CountDownTimer(seconds * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int counter = (int) millisUntilFinished / 1000;
                int mins = counter / 60;
                int secs = counter % 60;
                timerView.setText(secs > 9 ? "0" + mins + ":" + secs : "0" + mins + ":0" + secs);
                btn.setText("Stop");
                isTimerRunning = true;

            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alarm);
                mediaPlayer.start();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mediaPlayer.pause();
                btn.setText("Go");
                timerView.setText("00:30");
                seekBar.setProgress(30);
                isTimerRunning = false;
                seekBar.setOnTouchListener(new View.OnTouchListener(){

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });

            }
        } ;

            isTimerRunning = true;
            timer.start();
        } else {

            stopTimer();
        }

    }

}