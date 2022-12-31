package com.final2.puzzlesgame.Adapters;

import static android.content.Context.VIBRATOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static com.final2.puzzlesgame.Commons.Common.MyQuestion;
import static com.final2.puzzlesgame.Commons.Common.SoundStatus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.DatabaseSection.GameRoomDatabase;
import com.final2.puzzlesgame.Interfaces.QuestionNextListener;
import com.final2.puzzlesgame.Models.QPatterns;
import com.final2.puzzlesgame.Models.QuestionStats;
import com.final2.puzzlesgame.Models.Questions;
import com.final2.puzzlesgame.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QViewHolder> {
    Context context;
    Activity activity;
    ArrayList<Questions> data;
    private String myAnswer = "";
    QuestionNextListener questionNextListener;
    GameRoomDatabase gameRoomDatabase;
    CommonFuncs commonFuncs = new CommonFuncs();
    int currentLevel = 0;



    public QuestionsAdapter(Context context, ArrayList<Questions> levels,
                            QuestionNextListener questionNextListener,
                            GameRoomDatabase gameRoomDatabase,
                            int currentLevel) {
        this.context = context;
        this.data = levels;
        this.questionNextListener = questionNextListener;
        this.gameRoomDatabase = gameRoomDatabase;
        this.currentLevel = currentLevel;

    }

    @NonNull
    @Override
    public QuestionsAdapter.QViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QViewHolder(rowItem);

    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsAdapter.QViewHolder holder, int position) {
        Gson gson = new Gson();
        int pos = position;
        final MediaPlayer correct = MediaPlayer.create(context , R.raw.correct_answer);
        final MediaPlayer wrong = MediaPlayer.create(context , R.raw.wrong_answer);
        Questions l = data.get(pos);
        QPatterns qPatterns = gson.fromJson(l.getPatterns(),QPatterns.class);
        holder.QuestionTitle.setText(l.getTitle().toString());

        if (qPatterns.getPatternId() == 1){
            holder.QuestionBlockChoose.setVisibility(View.GONE);
            holder.QuestionBlockFill.setVisibility(View.GONE);
            holder.QuestionBlockTrueFalse.setVisibility(View.VISIBLE);

        }else if (qPatterns.getPatternId() == 2){
            holder.QuestionBlockChoose.setVisibility(View.VISIBLE);
            holder.QuestionBlockFill.setVisibility(View.GONE);
            holder.QuestionBlockTrueFalse.setVisibility(View.GONE);

            holder.Answer1.setText(l.getAnswer1());
            holder.Answer2.setText(l.getAnswer2());
            holder.Answer3.setText(l.getAnswer3());
            holder.Answer4.setText(l.getAnswer4());

        }else if (qPatterns.getPatternId() == 3){
            holder.QuestionBlockChoose.setVisibility(View.GONE);
            holder.QuestionBlockFill.setVisibility(View.VISIBLE);
            holder.QuestionBlockTrueFalse.setVisibility(View.GONE);
        }
        holder.AnswersGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = holder.itemView.findViewById(checkedId);
                myAnswer = radioButton.getText()+"";
                Toast.makeText(context, radioButton.getText()+"", Toast.LENGTH_SHORT).show();
            }
        });

        holder.TrueFalseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = holder.itemView.findViewById(checkedId);
                myAnswer = radioButton.getText()+"";
                Toast.makeText(context, radioButton.getText()+"", Toast.LENGTH_SHORT).show();
            }
        });

        holder.SubmitAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qPatterns.getPatternId() == 3){
                    myAnswer = holder.FillAnswer.getText()+"";
                }
                if (myAnswer.isEmpty()){
                    Toast.makeText(context, R.string.need_to_answer + "", Toast.LENGTH_SHORT).show();
                }else {
                    if (myAnswer.equals(l.getTrueAnswer())){
                        commonFuncs.AddPoints(context,l.getPoints());
                        questionNextListener.onQuestionNextListener(pos);
                        showDialog(true,l.getHint());
                        gameRoomDatabase.questionStatsDao().insert(new QuestionStats(
                                l.getId(),
                                l.getTitle(),
                                context.getString(R.string.correct) + "",
                                System.currentTimeMillis(),
                                l.getTrueAnswer()
                        ));
                        if (commonFuncs.GetFromSP(context , SoundStatus).equals("On")){
                            correct.start();
                        }
                    }else {
                        gameRoomDatabase.questionStatsDao().insert(new QuestionStats(
                                l.getId(),
                                l.getTitle(),
                                context.getString(R.string.incorrect_answer) + "",
                                System.currentTimeMillis(),
                                l.getTrueAnswer()
                        ));
                        showDialog(false,l.getHint());
                        final Vibrator mVibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
                        mVibrator.vibrate(500);
                        if (commonFuncs.GetFromSP(context , SoundStatus).equals("On")){
                            wrong.start();
                        }
                    }
                }

            }
        });


    }

    public void showDialog(Boolean IsCorrect,String theHint){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_question_result);

        TextView QuestionAnswer = (TextView) dialog.findViewById(R.id.QuestionIsTrue);
        TextView QuestionHint = (TextView) dialog.findViewById(R.id.QuestionHint);

        QuestionHint.setText(theHint);
        if (IsCorrect){
            QuestionAnswer.setText(R.string.dialog_correct);
        }else {
            QuestionAnswer.setText(R.string.dialog_incorrect);
        }

        Button dialogButton = (Button) dialog.findViewById(R.id.closeDialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class QViewHolder extends RecyclerView.ViewHolder {
        private TextView QuestionTitle;

        private RadioGroup AnswersGroup;

        private RadioButton Answer1;
        private RadioButton Answer2;
        private RadioButton Answer3;
        private RadioButton Answer4;

        private RadioGroup TrueFalseGroup;
        private EditText FillAnswer;


        private LinearLayout QuestionBlockChoose;
        private LinearLayout QuestionBlockTrueFalse;
        private LinearLayout QuestionBlockFill;

        private Button SubmitAnswer;


        public QViewHolder(@NonNull View itemView) {
            super(itemView);
            QuestionTitle = itemView.findViewById(R.id.QuestionTitle);

            AnswersGroup = itemView.findViewById(R.id.AnswersGroup);

            Answer1 = itemView.findViewById(R.id.Answer1);
            Answer2 = itemView.findViewById(R.id.Answer2);
            Answer3 = itemView.findViewById(R.id.Answer3);
            Answer4 = itemView.findViewById(R.id.Answer4);

            TrueFalseGroup = itemView.findViewById(R.id.TrueFalseGroup);
            FillAnswer = itemView.findViewById(R.id.FillAnswer);

            QuestionBlockChoose = itemView.findViewById(R.id.QuestionBlockChoose);
            QuestionBlockTrueFalse = itemView.findViewById(R.id.QuestionBlockTrueFalse);
            QuestionBlockFill = itemView.findViewById(R.id.QuestionBlockFill);

            SubmitAnswer = itemView.findViewById(R.id.SubmitAnswer);
        }


    }
}
