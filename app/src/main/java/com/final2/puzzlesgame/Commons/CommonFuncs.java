package com.final2.puzzlesgame.Commons;

import static android.content.Context.MODE_PRIVATE;
import static com.final2.puzzlesgame.Commons.Common.MyPoints;
import static com.final2.puzzlesgame.Commons.Common.SharedPreferenceName;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.final2.puzzlesgame.LevelsScreen;
import com.final2.puzzlesgame.Models.Levels;
import com.final2.puzzlesgame.Models.QPatterns;
import com.final2.puzzlesgame.Models.Questions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CommonFuncs {


    public void WriteSP(Context context,String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferenceName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();

    }

    public String GetFromSP(Context context,String key){
        String data = "";
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferenceName, MODE_PRIVATE);
        data = sharedPreferences.getString(key,"");
        return data;
    }

    public void DeleteSp(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferenceName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
    public void DeleteAll(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPreferenceName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("puzzleGameData.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public ArrayList<Levels> json(String data){
        ArrayList<Levels> levels = new ArrayList<>();
        try {
            Gson gson = new Gson();
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject levelObj = array.getJSONObject(i);
                int levelNo = levelObj.getInt("level_no");
                Log.d("levelNo", "json: " + levelNo);
                int unlockPoints = levelObj.getInt("unlock_points");
                ArrayList<Questions> levelQuestions = new ArrayList<>();
                JSONArray questionJArray = levelObj.getJSONArray("questions");
                for (int j = 0; j < questionJArray.length() ; j++) {
                    JSONObject questionObj = questionJArray.getJSONObject(j);
                    int id = questionObj.getInt("id");
                    String title = questionObj.getString("title");
                    String answer1 = questionObj.getString("answer_1");
                    String answer2 = questionObj.getString("answer_2");
                    String answer3 = questionObj.getString("answer_3");
                    String answer4 = questionObj.getString("answer_4");
                    String trueAnswer = questionObj.getString("true_answer");
                    int points = questionObj.getInt("points");
                    int duration = questionObj.getInt("duration");
                    JSONObject levelPattern = questionObj.getJSONObject("pattern");
                    int patternId = levelPattern.getInt("pattern_id");
                    String patternName = levelPattern.getString("pattern_name");
                    String hint = questionObj.getString("hint");
                    QPatterns qPatterns = new QPatterns(patternId , patternName);
                    Questions questions = new Questions(id , title , answer1 , answer2 , answer3 , answer4 , trueAnswer , points , duration , gson.toJson(qPatterns).toString() , hint);
                    levelQuestions.add(questions);
                }
                String questionsLevels = gson.toJson(levelQuestions).toString();
                Levels levelsObj = new Levels(levelNo , unlockPoints , questionsLevels);
                levels.add(levelsObj);
            }
        } catch (JSONException e) {
            Log.e("catch", "json: " + e.getMessage() );
            e.printStackTrace();
        }
        return levels;
    }

    public void AddPoints(Context context,int newpoints){
        int myPoints = Integer.parseInt(GetFromSP(context,MyPoints).toString());
        myPoints = myPoints + newpoints;
        WriteSP(context,MyPoints,myPoints+"");
    }

}
