package com.final2.puzzlesgame.Adapters;

import static com.final2.puzzlesgame.Commons.Common.MyPoints;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.final2.puzzlesgame.Commons.CommonFuncs;
import com.final2.puzzlesgame.LevelsScreen;
import com.final2.puzzlesgame.Models.Levels;
import com.final2.puzzlesgame.R;
import com.final2.puzzlesgame.databinding.ItemLevelBinding;

import java.util.ArrayList;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {
    ItemLevelBinding binding;
    Context context;
    ArrayList<Levels> levels;

    CommonFuncs commonFuncs = new CommonFuncs();

    public LevelsAdapter(Context context, ArrayList<Levels> levels) {
        this.context = context;
        this.levels = levels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemLevelBinding.inflate(LayoutInflater.from(context),parent,false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Levels l = levels.get(position);
        holder.binding.tvLevelNo.setText(l.getLevelNo() + "");
        holder.binding.tvUnlockPoints.setText(context.getString(R.string.unlock_points) + " " + l.getUnlockPoints() + "");
//        if (score == l.getUnlockPoints()){
//            isLevelUnlocked = "Unlocked";
//            holder.binding.tvLevelStatus.setText("Level status : " + isLevelUnlocked);
//        } else {
//            isLevelUnlocked = "Locked";
//            holder.binding.tvLevelStatus.setText("Level status : " + isLevelUnlocked);
//        }

        int myPoints = Integer.parseInt(commonFuncs.GetFromSP(context,MyPoints).toString());
        if (myPoints >= l.getUnlockPoints()){
            holder.binding.tvLevelStatus.setText(R.string.unlocked);
        }else {
            holder.binding.tvLevelStatus.setText(R.string.locked);
        }

        holder.binding.ivEnterLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int myPoints = Integer.parseInt(commonFuncs.GetFromSP(context,MyPoints).toString());
                if (myPoints >= l.getUnlockPoints()){
                    Intent toLevel = new Intent(context, LevelsScreen.class);
                    toLevel.putExtra("leveldata",l);
                    context.startActivity(toLevel);
                }else {
                    Toast.makeText(context, R.string.no_points+"", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemLevelBinding binding;
        public ViewHolder(@NonNull ItemLevelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
