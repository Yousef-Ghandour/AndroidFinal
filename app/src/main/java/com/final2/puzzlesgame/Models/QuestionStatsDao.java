package com.final2.puzzlesgame.Models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionStatsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(QuestionStats questionStats);

    @Query("DELETE FROM questionstats")
    void deleteAll();

    @Query("SELECT * FROM questionstats ORDER BY questionid ASC")
    List<QuestionStats> getQuestionStats();
}
