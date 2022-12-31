package com.final2.puzzlesgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.final2.puzzlesgame.Adapters.LevelsAdapter;
import com.final2.puzzlesgame.DatabaseSection.GameRoomDatabase;
import com.final2.puzzlesgame.Models.Levels;
import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.databinding.ScreenStartgameBinding;


import java.util.ArrayList;

public class StartGameScreen extends AppCompatActivity {
    ArrayList<Levels> levels = new ArrayList<>();
    LevelsAdapter levelsAdapter;
    ScreenStartgameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScreenStartgameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GameRoomDatabase gameRoomDatabase = GameRoomDatabase.getDatabase(StartGameScreen.this);
        levels.addAll(gameRoomDatabase.levelsDao().getAllLevels());


        levelsAdapter = new LevelsAdapter(this , levels);
        binding.rvLevels.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false));
        binding.rvLevels.setAdapter(levelsAdapter);
        Log.e("newName", "onCreate: " + levels.size());
    }



}