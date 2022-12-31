package com.final2.puzzlesgame.Services;

import static com.final2.puzzlesgame.Commons.Common.NotificationStatus;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.R;

public class SendNotification extends JobService {
        CommonFuncs commonFuncs = new CommonFuncs();

        public static final String CHANNEL_ID = "channel_ID";

        @Override
        public boolean onStartJob(JobParameters jobParameters) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "channel name", NotificationManager.IMPORTANCE_DEFAULT);
                        NotificationManager manager = getSystemService(NotificationManager.class);
                        manager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle("We Missed you!")
                        .setSmallIcon(R.mipmap.ic_launcher_foreground)
                        .setContentText("You haven't opened the app in 24 hours!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

                // Display the notification
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.notify(1, builder.build());
                Log.d("makeText", "onStartJob: ");
                return false;


        }



        @Override
        public boolean onStopJob(JobParameters jobParameters) {
                return false;
        }
}

