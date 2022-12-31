package com.final2.puzzlesgame;

import static com.final2.puzzlesgame.Commons.Common.NotificationStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.DatabaseSection.GameRoomDatabase;
import com.final2.puzzlesgame.Models.QuestionStats;
import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.Services.SendNotification;
import com.final2.puzzlesgame.databinding.ScreenHomeBinding;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class HomeScreen extends AppCompatActivity {
    ScreenHomeBinding binding;
    CommonFuncs commonFuncs = new CommonFuncs();
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScreenHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.btStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this , StartGameScreen.class);
                startActivity(intent);

            }
        });

        binding.btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this , SettingScreen.class);
                startActivity(intent);

            }
        });
        binding.btLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(commonFuncs.GetFromSP(this , NotificationStatus).equals("On")){
            scheduleNotificationJob();
        }
    }

    private void scheduleNotificationJob() {
        int jobId = 1; // The job's unique ID
        long repeatInterval = TimeUnit.SECONDS.toMillis(5); // Run the job every 24 hours

        ComponentName serviceComponent = new ComponentName(this, SendNotification.class);

        JobInfo jobInfo = new JobInfo.Builder(jobId, serviceComponent)
                .setMinimumLatency(repeatInterval) // Set the job to run periodically
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        JobInfo job = new JobInfo.Builder(1, new ComponentName(this, SendNotification.class))
                .setMinimumLatency(TimeUnit.SECONDS.toMillis(5)) // Wait at least 24 hours before executing the job
                .build();

        // Schedule the job
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(job);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d("jobLog", "Job scheduled successfully!");
        } else {
            Log.d("jobLog", "Failed to schedule job.");
        }

    }




}