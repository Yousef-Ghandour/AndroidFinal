package com.final2.puzzlesgame.DatabaseSection;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.final2.puzzlesgame.Models.Levels;
import com.final2.puzzlesgame.Models.LevelsDao;
import com.final2.puzzlesgame.Models.QPatterns;
import com.final2.puzzlesgame.Models.QuestionStats;
import com.final2.puzzlesgame.Models.QuestionStatsDao;
import com.final2.puzzlesgame.Models.Questions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Levels.class, QPatterns.class, Questions.class, QuestionStats.class},version = 1, exportSchema = false)
public abstract class GameRoomDatabase extends RoomDatabase {

    public abstract LevelsDao levelsDao();
    public abstract QuestionStatsDao questionStatsDao();

    private static volatile GameRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static GameRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    GameRoomDatabase.class, "game_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}