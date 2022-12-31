package com.final2.puzzlesgame.Models;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LevelsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Levels levels);

    @Query("DELETE FROM level_table")
    void deleteAll();

    @Query("SELECT * FROM level_table ORDER BY levelno ASC")
    List<Levels> getAllLevels();
}
