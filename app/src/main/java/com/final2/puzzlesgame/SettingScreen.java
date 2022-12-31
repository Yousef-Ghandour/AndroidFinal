package com.final2.puzzlesgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.SettingsSection.GameSettingScreen;
import com.final2.puzzlesgame.SettingsSection.ProfileScreen;
import com.final2.puzzlesgame.databinding.ScreenSettingBinding;

public class SettingScreen extends AppCompatActivity {


    ScreenSettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScreenSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toGameSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingScreen.this, GameSettingScreen.class));
            }
        });
        binding.toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingScreen.this, ProfileScreen.class));
            }
        });
    }



}