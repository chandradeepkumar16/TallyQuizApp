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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText et_su_email , et_su_pass , et_su_name;
    Button registerbtn;
    TextView gotologin_page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        et_su_email=(EditText) findViewById(R.id.signup_email);
        et_su_pass=(EditText) findViewById(R.id.signup_password);
        et_su_name=(EditText) findViewById(R.id.signup_name);

        registerbtn=(Button)findViewById(R.id.goto_register);
        gotologin_page=(TextView)findViewById(R.id.goto_login);


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this , LoginActivity.class));
                RegisterUser();
            }
        });

        gotologin_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , LoginActivity.class));
            }
        });

    }

    private void RegisterUser() {
        String email = et_su_email.getText().toString().trim();
        String password = et_su_pass.getText().toString().trim();
        String name = et_su_name.getText().toString().trim();

        if(name.isEmpty()){
            et_su_name.setError(" User name is required");
            et_su_name.requestFocus();
            return;
        }

        if(email.isEmpty()){
            et_su_email.setError(" email is required");
//            Toast.makeText(this, "email is not perfect", Toast.LENGTH_SHORT).show();
            et_su_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_su_email.setError("Please provide valid email");
            et_su_email.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length() <6){
            et_su_pass.setError(" password should have atleast 6 letters");
            et_su_pass.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Admins admins = new Admins( name ,email , password);

                            FirebaseDatabase.getInstance().getReference("Admins")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(admins).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                                        Toast.makeText(MainActivity.this, "registration done", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this, "Failed to go further", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}