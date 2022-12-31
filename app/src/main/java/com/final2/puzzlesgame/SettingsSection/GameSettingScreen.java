package com.final2.puzzlesgame.SettingsSection;

import static com.final2.puzzlesgame.Commons.Common.NotificationStatus;
import static com.final2.puzzlesgame.Commons.Common.SoundStatus;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.DatabaseSection.GameRoomDatabase;
import com.final2.puzzlesgame.R;
import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.Services.SendNotification;
import com.final2.puzzlesgame.SplashScreen;
import com.final2.puzzlesgame.databinding.ScreenGameSettingBinding;

public class GameSettingScreen extends AppCompatActivity {

    ScreenGameSettingBinding binding;

    CommonFuncs commonFuncs = new CommonFuncs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScreenGameSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(commonFuncs.GetFromSP(this , SoundStatus).equals("On")){
            binding.sSoundSwitch.setText("Sound enabled");
            binding.sSoundSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.app_cyan), PorterDuff.Mode.SRC_IN);
            binding.sSoundSwitch.setChecked(true);
        } else if (commonFuncs.GetFromSP(this , SoundStatus).equals("Off")){
            binding.sSoundSwitch.setText("Sound disabled");
            binding.sSoundSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
            binding.sSoundSwitch.setChecked(false);

        }
        if(commonFuncs.GetFromSP(this , NotificationStatus).equals("On")){
            binding.sNotificationSwitch.setText("Notifications enabled");
            binding.sNotificationSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.app_cyan), PorterDuff.Mode.SRC_IN);
            binding.sNotificationSwitch.setChecked(true);
        } else if (commonFuncs.GetFromSP(this , NotificationStatus).equals("Off")){
            binding.sNotificationSwitch.setText("Notifications disabled");
            binding.sNotificationSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
            binding.sNotificationSwitch.setChecked(false);

        }

        binding.sSoundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(binding.sSoundSwitch.isChecked()){
                    binding.sSoundSwitch.setText("Sound enabled");
                    binding.sSoundSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.app_cyan), PorterDuff.Mode.SRC_IN);
                    commonFuncs.WriteSP(GameSettingScreen.this,SoundStatus,"On");
                    startService(new Intent(getApplicationContext(), BackgroundMusicService.class));


                } else if (!binding.sSoundSwitch.isChecked()){
                    binding.sSoundSwitch.setText("Sound disabled");
                    binding.sSoundSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    commonFuncs.WriteSP(GameSettingScreen.this,SoundStatus,"Off");
                    stopService(new Intent(getApplicationContext(), BackgroundMusicService.class));

                }

            }
        });
        binding.sNotificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(binding.sNotificationSwitch.isChecked()){
                    binding.sNotificationSwitch.setText("Notifications enabled");
                    binding.sNotificationSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.app_cyan), PorterDuff.Mode.SRC_IN);
                    commonFuncs.WriteSP(GameSettingScreen.this,NotificationStatus,"On");

                } else if (!binding.sNotificationSwitch.isChecked()){
                    binding.sNotificationSwitch.setText("Notifications disabled");
                    binding.sNotificationSwitch.getTrackDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.white), PorterDuff.Mode.SRC_IN);
                    commonFuncs.WriteSP(GameSettingScreen.this,NotificationStatus,"Off");


                }

            }
        });
        binding.ResetAppDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameRoomDatabase gameRoomDatabase = GameRoomDatabase.getDatabase(GameSettingScreen.this);
                gameRoomDatabase.questionStatsDao().deleteAll();
                commonFuncs.DeleteAll(GameSettingScreen.this);
                Intent toRestart = new Intent(GameSettingScreen.this, SplashScreen.class);
                toRestart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                toRestart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(toRestart);
                finish();

            }
        });

    }


}