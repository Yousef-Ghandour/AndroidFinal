package com.final2.puzzlesgame.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.final2.puzzlesgame.Models.QuestionStats;
import com.final2.puzzlesgame.R;
import com.final2.puzzlesgame.databinding.ItemStatsBinding;

import java.util.ArrayList;
import java.util.Date;

public class QStatsAdapter extends RecyclerView.Adapter<QStatsAdapter.ViewHolder> {
    ItemStatsBinding binding;
    Context context;
    ArrayList<QuestionStats> data;


    public QStatsAdapter(Context context, ArrayList<QuestionStats> levels) {
        this.context = context;
        this.data = levels;
    }

    @NonNull
    @Override
    public QStatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemStatsBinding.inflate(LayoutInflater.from(context),parent,false);
        QStatsAdapter.ViewHolder holder = new QStatsAdapter.ViewHolder(binding);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull QStatsAdapter.ViewHolder holder, int position) {
        QuestionStats l = data.get(position);
        Long date = l.getQuestionDate();
        String dateString = DateFormat.format("dd/MM/yyyy", new Date(date)).toString();
        holder.binding.tvStatsTitle.setText(l.getQuestionTitle());
        holder.binding.tvStatsAnswer.setText(context.getString(R.string.answer) + "" +l.getQuestionAnswer());
        holder.binding.tvStatsTrueAnswer.setText(context.getString(R.string.true_answer) + "" + l.getQuestionTrueAnswer());
        holder.binding.tvStatsDate.setText(context.getString(R.string.played_at) + "" + dateString);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemStatsBinding binding;
        public ViewHolder(@NonNull ItemStatsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
