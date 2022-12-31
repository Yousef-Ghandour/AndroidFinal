package com.final2.puzzlesgame.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "questionstats")
public class QuestionStats implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "questionid")
    private int questionId;
    @ColumnInfo(name = "questiontitle")
    private String questionTitle;
    @ColumnInfo(name = "questionanswer")
    private String questionAnswer;
    @ColumnInfo(name = "questiondate")
    private Long questionDate;
    @ColumnInfo(name = "questiontrueanswer")
    private String questionTrueAnswer;

    public QuestionStats() {
    }

    public QuestionStats(int questionId, String questionTitle, String questionAnswer, Long questionDate, String questionTrueAnswer) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionAnswer = questionAnswer;
        this.questionDate = questionDate;
        this.questionTrueAnswer = questionTrueAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public Long getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Long questionDate) {
        this.questionDate = questionDate;
    }

    public String getQuestionTrueAnswer() {
        return questionTrueAnswer;
    }

    public void setQuestionTrueAnswer(String questionTrueAnswer) {
        this.questionTrueAnswer = questionTrueAnswer;
    }
}
