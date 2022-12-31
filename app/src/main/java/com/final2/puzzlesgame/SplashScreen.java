package com.final2.puzzlesgame;

import static com.final2.puzzlesgame.Commons.Common.FirstOpen;
import static com.final2.puzzlesgame.Commons.Common.MyPoints;
import static com.final2.puzzlesgame.Commons.Common.NotificationStatus;
import static com.final2.puzzlesgame.Commons.Common.SoundStatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.DatabaseSection.GameRoomDatabase;
import com.final2.puzzlesgame.Models.Levels;
import com.final2.puzzlesgame.Models.QuestionStats;
import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.Services.SendNotification;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SplashScreen extends AppCompatActivity {

    ArrayList<Levels> levels = new ArrayList<>();
    CommonFuncs commonFuncs = new CommonFuncs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);


        if (commonFuncs.GetFromSP(this,FirstOpen).equals("Yes")){
            if(commonFuncs.GetFromSP(this,SoundStatus).equals("On")){
                playBackgroundSound();

                Log.e("soundShit", SoundStatus);

            } else if (commonFuncs.GetFromSP(this , SoundStatus).equals("Off")){
                stopService(new Intent(getApplicationContext(), BackgroundMusicService.class));
            }
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                    startActivity(intent);
                    finish();

                }
            }.start();
        }else {
            GameRoomDatabase gameRoomDatabase = GameRoomDatabase.getDatabase(this);
            levels.addAll(commonFuncs.json(commonFuncs.loadJSONFromAsset(this)));
            for (int i = 0; i < levels.size(); i++) {
                Log.e("level",levels.get(i).getLevelNo()+"");
                gameRoomDatabase.levelsDao().insert(levels.get(i));
            }
            commonFuncs.WriteSP(this,SoundStatus,"On");
            commonFuncs.WriteSP(this,NotificationStatus,"On");
            commonFuncs.WriteSP(this,FirstOpen,"Yes");
            commonFuncs.WriteSP(this,MyPoints,"0");
            playBackgroundSound();
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                    startActivity(intent);
                    finish();

                }
            }.start();
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.black));
    }
    public void playBackgroundSound() {
        Intent intent = new Intent(SplashScreen.this, BackgroundMusicService.class);
        startService(intent);
    }



}