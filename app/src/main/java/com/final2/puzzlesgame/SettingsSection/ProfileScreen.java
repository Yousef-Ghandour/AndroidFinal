package com.final2.puzzlesgame.SettingsSection;

import static com.final2.puzzlesgame.Commons.Common.MyBirth;
import static com.final2.puzzlesgame.Commons.Common.MyCountry;
import static com.final2.puzzlesgame.Commons.Common.MyEmail;
import static com.final2.puzzlesgame.Commons.Common.MyName;
import static com.final2.puzzlesgame.Commons.Common.MyPoints;
import static com.final2.puzzlesgame.Commons.Common.MySex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.Services.BackgroundMusicService;
import com.final2.puzzlesgame.databinding.ScreenProfileBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileScreen extends AppCompatActivity {

    ScreenProfileBinding binding;


    CommonFuncs commonFuncs = new CommonFuncs();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ScreenProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.PlayerName.setText(commonFuncs.GetFromSP(this,MyName));
        binding.PlayerEmail.setText(commonFuncs.GetFromSP(this,MyEmail));
        binding.PlayerDate.setText(commonFuncs.GetFromSP(this,MyBirth));
        binding.PlayerSex.setText(commonFuncs.GetFromSP(this,MySex));
        binding.PlayerCountry.setText(commonFuncs.GetFromSP(this,MyCountry));

        binding.PlayerStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileScreen.this,StatsScreen.class));
            }
        });
        final Calendar myCalendar= Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel(myCalendar);
            }

        };
        binding.PlayerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date = new DatePickerDialog(ProfileScreen.this,dateSetListener,
                        myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                date.getDatePicker().setMaxDate(System.currentTimeMillis());
                date.show();

            }
        });


        binding.SavePlayerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put Checkers
                String PlayerName = binding.PlayerName.getText().toString();
                String PlayerEmail = binding.PlayerEmail.getText().toString();
                String PlayerDate = binding.PlayerDate.getText().toString();
                String PlayerSex = binding.PlayerSex.getText().toString();
                String PlayerCountry = binding.PlayerCountry.getText().toString();

                commonFuncs.WriteSP(ProfileScreen.this,MyName,PlayerName);
                commonFuncs.WriteSP(ProfileScreen.this,MyEmail,PlayerEmail);
                commonFuncs.WriteSP(ProfileScreen.this,MyBirth,PlayerDate);
                commonFuncs.WriteSP(ProfileScreen.this,MySex,PlayerSex);
                commonFuncs.WriteSP(ProfileScreen.this,MyCountry,PlayerCountry);
            }
        });

    }
    private void updateLabel(Calendar myCalendar) {
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        binding.PlayerDate.setText(dateFormat.format(myCalendar.getTime()) + " " + "[" + Integer.toString(calculateAge(myCalendar.getTimeInMillis())) + "]");
    }
    int calculateAge(long date){
        Calendar dob = Calendar.getInstance();
        dob.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return age;

    }


}