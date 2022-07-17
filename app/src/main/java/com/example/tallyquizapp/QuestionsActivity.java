package com.example.tallyquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {

    private Button addques;
    private RecyclerView recyclerViewques;
    private QuestionAdapter adapter;
    public static List<QuestionModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        addques=(Button)findViewById(R.id.add_ques_btn);
        recyclerViewques=(RecyclerView)findViewById(R.id.recycler_view_ques);

        String category_name="hey";


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerViewques.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();


        list.add(new QuestionModel("question1 ?" , "a","b","c","d","b"));
        list.add(new QuestionModel(  "question2 ?" , "a","b","c","d","a"));


        adapter=new QuestionAdapter(list);
        recyclerViewques.setAdapter(adapter);

        getData(category_name);

        addques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addQuestion = new Intent(QuestionsActivity.this,AddQuestionActivity.class);
                startActivity(addQuestion);
            }
        });
    }

//child(category_name).
    private void getData(String category_name){
        FirebaseDatabase.getInstance().getReference().child("Categories")
                .child("cat31")
                .child(category_name).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String question = snapshot.child("question").getValue().toString();
                String a = snapshot.child("optionA").getValue().toString();
                String b = snapshot.child("optionB").getValue().toString();
                String c = snapshot.child("optionC").getValue().toString();
                String d = snapshot.child("optionD").getValue().toString();
                String correct = snapshot.child("correctans").getValue().toString();

                    list.add(new QuestionModel(question ,a,b,c,d,correct));
//id
//                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(QuestionsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }
}