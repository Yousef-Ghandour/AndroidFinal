package com.final2.puzzlesgame;

import static com.final2.puzzlesgame.Commons.Common.MyPoints;
import static com.final2.puzzlesgame.Commons.Common.MyQuestion;
import static com.final2.puzzlesgame.Commons.Common.NotificationStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.final2.puzzlesgame.Adapters.QuestionsAdapter;
import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.DatabaseSection.GameRoomDatabase;
import com.final2.puzzlesgame.Interfaces.QuestionNextListener;
import com.final2.puzzlesgame.Models.Levels;
import com.final2.puzzlesgame.Models.QuestionStats;
import com.final2.puzzlesgame.Models.Questions;
import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.Services.SendNotification;
import com.final2.puzzlesgame.databinding.ScreenLevelsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LevelsScreen extends AppCompatActivity implements QuestionNextListener {

    ScreenLevelsBinding binding;
    QuestionsAdapter questionsAdapter;
    Levels l;
    ArrayList<Questions> questionsArrayList = new ArrayList<>();

    CommonFuncs commonFuncs = new CommonFuncs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        l = (Levels) getIntent().getSerializableExtra("leveldata");

        Gson gson = new Gson();

        Type questionsListType = new TypeToken<ArrayList<Questions>>(){}.getType();
        ArrayList<Questions> userArray = gson.fromJson(l.getQuestions(), questionsListType);
        GameRoomDatabase gameRoomDatabase = GameRoomDatabase.getDatabase(this);

        questionsArrayList.addAll(userArray);
        binding = ScreenLevelsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        questionsAdapter = new QuestionsAdapter(LevelsScreen.this,questionsArrayList,this,gameRoomDatabase,l.getLevelNo());
        binding.QuestionsViewPager.setAdapter(questionsAdapter);
        binding.QuestionsViewPager.setUserInputEnabled(false);


        binding.SkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedQues = binding.QuestionsViewPager.getCurrentItem();
                if (selectedQues < (questionsArrayList.size())){
                    int myPoints = Integer.parseInt(commonFuncs.GetFromSP(LevelsScreen.this,MyPoints).toString());
                    if (myPoints >= 3){
                        myPoints = myPoints - 3;
                        commonFuncs.WriteSP(LevelsScreen.this,MyPoints,myPoints+"");
                        Questions currentQues = questionsArrayList.get(selectedQues);
                        gameRoomDatabase.questionStatsDao().insert(new QuestionStats(
                                currentQues.getId(),
                                currentQues.getTitle(),
                                "Skipped",
                                System.currentTimeMillis(),
                                currentQues.getTrueAnswer()
                                ));
                        if (selectedQues == (questionsArrayList.size()-1)){
                            // toast level ended
                            finish();
                        }else {
                            binding.QuestionsViewPager.setCurrentItem(selectedQues+1);
                        }
                    }else {
                        // toast no points
                    }
                }

            }
        });

        if (!commonFuncs.GetFromSP(LevelsScreen.this,MyQuestion+l.getLevelNo()).isEmpty()){
            int myCurrentQuestion = Integer.parseInt(commonFuncs.GetFromSP(LevelsScreen.this,MyQuestion+l.getLevelNo()).toString());
            binding.QuestionsViewPager.setCurrentItem(myCurrentQuestion);
        }




    }
    @Override
    public void onQuestionNextListener(int Qindex) {
        if (Qindex < (questionsArrayList.size())){
            if (Qindex == (questionsArrayList.size()-1)){
                commonFuncs.WriteSP(LevelsScreen.this,MyQuestion+l.getLevelNo(),"");
                finish();
            }else {
                commonFuncs.WriteSP(LevelsScreen.this,MyQuestion+l.getLevelNo(),(Qindex+1)+"");
                binding.QuestionsViewPager.setCurrentItem(Qindex+1);
            }
        }
    }




    private void scheduleNotificationJob() {
        // Create a new job
        int jobId = 1; // The job's unique ID
        long repeatInterval = TimeUnit.SECONDS.toMillis(5); // Run the job every 24 hours

        ComponentName serviceComponent = new ComponentName(this, SendNotification.class);

        JobInfo jobInfo = new JobInfo.Builder(jobId, serviceComponent)
                .setMinimumLatency(repeatInterval) // Set the job to run periodically
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);
        JobInfo job = new JobInfo.Builder(1, new ComponentName(this, SendNotification.class))
                .setMinimumLatency(TimeUnit.SECONDS.toMillis(5)) // Wait at least 24 hours before executing the job
                .build();

        // Schedule the job
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(job);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d("jobLog", "Job scheduled successfully!");
        } else {
            Log.d("jobLog", "Failed to schedule job.");
        }
    }

}