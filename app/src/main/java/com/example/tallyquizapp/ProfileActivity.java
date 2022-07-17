package com.example.tallyquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref = database.getReference();

    private EditText category_name;
    private Button category_btn;

    private RecyclerView recyclerView;
    private List<CategoryModel> list;

    private Dialog category_dialog;
    private Adapter adapter;
    private ImageButton share;

    private Adapter.ShareListener shareListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setCategoryDialog();
        recyclerView=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        share=(ImageButton)findViewById(R.id.share);

        list = new ArrayList<>();

        list.add(new CategoryModel("category 1"));
        list.add(new CategoryModel("category 2"));
        list.add(new CategoryModel("category 3"));




        adapter = new Adapter(list, new Adapter.ShareListener() {
            @Override
            public void onshare() {
                Toast.makeText(ProfileActivity.this, "clicked on share", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        myref.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {

                    HashMap<String , String> hm = (HashMap<String, String>) dataSnapshot1.getValue();

//                    list.add(new CategoryModel(dataSnapshot1.child("title").getValue().toString()));
                    list.add(new CategoryModel(hm.get("title")));
//                    list.add(dataSnapshot1.getValue(CategoryModel.class));
                }
                for(DataSnapshot x:snapshot.getChildren()){

                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()== R.id.add){
            category_dialog.show();
            Toast.makeText(this, "Dialog", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    private void setCategoryDialog(){
        category_dialog = new Dialog(this);
        category_dialog.setContentView(R.layout.add_category_dialog);
        category_dialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        category_dialog.setCancelable(true);

        category_name=category_dialog.findViewById(R.id.category_name);
        category_btn=category_dialog.findViewById(R.id.addbtn);

        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category_name.getText().toString().isEmpty()){
                    category_name.setError("required field");
                    return;
                }
                for (CategoryModel model :list){
                    if(category_name.getText().toString().equals(model.getTitle())){
                        category_name.setError("category name already present");
                        return;
                    }
                }
                category_dialog.dismiss();
                uploadData();
            }
        });
    }


    private void uploadData(){
//        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        Map<String,Object> map= new HashMap<>();
        map.put("title" , category_name.getText().toString());
//
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        database.getReference().child("Categories").child("cat"+list.size()+1).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){

                    list.add(new CategoryModel(category_name.getText().toString()));
                    adapter.notifyDataSetChanged();

                }else{
                    Toast.makeText(ProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}