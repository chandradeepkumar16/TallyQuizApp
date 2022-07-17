package com.example.tallyquizapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;
    private ShareListener shareListener;

    public Adapter(List<CategoryModel> categoryModelList , ShareListener shareListener) {
        this.categoryModelList = categoryModelList;
        this.shareListener=shareListener;
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.ViewHolder holder, int position) {

        holder.setData(categoryModelList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageButton share;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.headtitle);
            share=itemView.findViewById(R.id.share);
        }

        private void setData(final String title){
            this.title.setText(title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent setIntent = new Intent(itemView.getContext(),QuestionsActivity.class);
                    setIntent.putExtra("title" , title);

                    itemView.getContext().startActivity(setIntent);
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    shareListener.onshare();


                }
            });
        }
    }

    public interface ShareListener{
        public void onshare();
    }

}
