package com.example.tallyquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    EditText log_email , log_pass;
    Button loginbtn;
    TextView gotoregister;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        log_email=(EditText)findViewById(R.id.login_email);
        log_pass=(EditText)findViewById(R.id.login_pass);

        loginbtn=(Button)findViewById(R.id.mainlogin);
        gotoregister=(TextView)findViewById(R.id.goto_register);

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this , ProfileActivity.class));
        }



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogin();
            }
        });

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , MainActivity.class));
            }
        });

    }

    private void UserLogin() {

        String email = log_email.getText().toString().trim();
        String password = log_pass.getText().toString().trim();


        if(email.isEmpty()){
            log_email.setError(" email is required");
            log_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            log_email.setError("Please provide valid email");
            log_email.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length() <6){
            log_pass.setError(" password should have atleast 6 letters");
            log_pass.requestFocus();
            return;
        }



        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this , ProfileActivity.class));

                }else{

                    Toast.makeText(LoginActivity.this, "User failed to login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}