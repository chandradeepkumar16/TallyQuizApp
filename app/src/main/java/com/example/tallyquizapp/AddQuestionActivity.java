package com.example.tallyquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class AddQuestionActivity extends AppCompatActivity {

    private EditText questionhere;
    private LinearLayout answers;
    private Button uploadbtn;
    private EditText eta , etb , etc , etd;
    private EditText correctans;

    private String categoryname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        correctans=(EditText)findViewById(R.id.correct_ans);

        categoryname="hey";
        questionhere=(EditText)findViewById(R.id.ques_here);

        uploadbtn=(Button)findViewById(R.id.uploadbtn);
        eta=(EditText)findViewById(R.id.eta);
        etb=(EditText)findViewById(R.id.etb);
        etc=(EditText)findViewById(R.id.etc);
        etd=(EditText)findViewById(R.id.etd);
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload();
            }
        });


    }


    private void upload(){


        HashMap<String , Object> map = new HashMap<>();
        map.put("question", questionhere.getText().toString());
        map.put("optionA" ,eta.getText().toString());
        map.put("optionB",etb.getText().toString());
        map.put("optionC",etc.getText().toString());
        map.put("optionD" ,etd.getText().toString());
        map.put("correctans" , correctans.getText().toString());



        FirebaseDatabase.getInstance().getReference()
                .child("Categories").child("cat31")
                .child(categoryname)
                .child("questions")
                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
//id
                    QuestionModel questionModel = new QuestionModel(map.get("question").toString(),
                            map.get("optionA").toString(),
                            map.get("optionB").toString(),
                            map.get("optionC").toString(),
                            map.get("optionD").toString(),
                            map.get("correctans").toString()


                            );

                    QuestionsActivity.list.add(questionModel);
                    finish();

                }else{
                    Toast.makeText(AddQuestionActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
