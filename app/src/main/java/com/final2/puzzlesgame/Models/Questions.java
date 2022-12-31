package com.final2.puzzlesgame.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "question")
public class Questions implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "answer1")
    private String answer1;
    @ColumnInfo(name = "answer2")
    private String answer2;
    @ColumnInfo(name = "answer3")
    private String answer3;
    @ColumnInfo(name = "answer4")
    private String answer4;
    @ColumnInfo(name = "trueAnswer")
    private String trueAnswer;
    @ColumnInfo(name = "points")
    private int points;
    @ColumnInfo(name = "duration")
    private int duration;
    @ColumnInfo(name = "patterns")
    private String patterns;
    @ColumnInfo(name = "hint")
    private String hint;

    public Questions() {
    }

    public Questions(int id, String title, String answer1, String answer2, String answer3, String answer4, String trueAnswer, int points, int duration, String patterns, String hint) {
        this.id = id;
        this.title = title;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.trueAnswer = trueAnswer;
        this.points = points;
        this.duration = duration;
        this.patterns = patterns;
        this.hint = hint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPatterns() {
        return patterns;
    }

    public void setPatterns(String patterns) {
        this.patterns = patterns;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
