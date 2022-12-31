package com.final2.puzzlesgame.SettingsSection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.final2.puzzlesgame.Adapters.QStatsAdapter;
import com.final2.puzzlesgame.DatabaseSection.GameRoomDatabase;
import com.final2.puzzlesgame.Models.QuestionStats;
import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.databinding.ScreenStatsBinding;

import java.util.ArrayList;

public class StatsScreen extends AppCompatActivity {


    ArrayList<QuestionStats> questionStats = new ArrayList<>();
    QStatsAdapter qStatsAdapter;
    ScreenStatsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScreenStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        GameRoomDatabase gameRoomDatabase = GameRoomDatabase.getDatabase(this);
        questionStats.addAll(gameRoomDatabase.questionStatsDao().getQuestionStats());

        qStatsAdapter = new QStatsAdapter(this,questionStats);
        binding.StatsRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.StatsRecycler.setAdapter(qStatsAdapter);



    }

}