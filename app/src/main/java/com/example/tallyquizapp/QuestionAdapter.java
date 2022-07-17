package com.example.tallyquizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.Viewholder>{

    private List<QuestionModel> list;

    public QuestionAdapter(List<QuestionModel> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull QuestionAdapter.Viewholder holder, int position) {
        String question = list.get(position).getQuestion();
        String answer = list.get(position).getAnswer();

        holder.setData(question,answer,position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{

        private TextView question , answer;

        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
        }

        private void setData(String question , String answer , int position){
            this.question.setText(position+1 +" ."+question);
            this.answer.setText("Ans ."+answer) ;

        }



    }

}
