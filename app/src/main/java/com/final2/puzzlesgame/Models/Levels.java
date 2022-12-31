package com.final2.puzzlesgame.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Entity(tableName = "level_table")
public class Levels implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "levelno")
    private int levelNo;
    @ColumnInfo(name = "unlockpoints")
    private int unlockPoints;
    @ColumnInfo(name = "questions")
    private String questions;

    public Levels() {
    }

    public Levels(int levelNo, int unlockPoints, String questions) {
        this.levelNo = levelNo;
        this.unlockPoints = unlockPoints;
        this.questions = questions;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    public int getUnlockPoints() {
        return unlockPoints;
    }

    public void setUnlockPoints(int unlockPoints) {
        this.unlockPoints = unlockPoints;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }
}
