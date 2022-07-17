package com.example.tallyquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;

public class SetsActivity extends AppCompatActivity {

    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        gridView=findViewById(R.id.gridView);


        GridAdapter gridAdapter = new GridAdapter(15);
        gridView.setAdapter(gridAdapter);

    }


}