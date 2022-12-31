package com.final2.puzzlesgame.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "qpatters")
public class QPatterns implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "patternid")
    private int patternId;
    @ColumnInfo(name = "patternname")
    private String patternName;

    public QPatterns() {
    }

    public QPatterns(int patternId, String patternName) {
        this.patternId = patternId;
        this.patternName = patternName;
    }

    public int getPatternId() {
        return patternId;
    }

    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }
}
