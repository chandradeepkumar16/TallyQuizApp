package com.example.tallyquizapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private int sets=0;

    public GridAdapter(int sets) {
        this.sets = sets;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View view;
        if(convertview==null){
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item,parent,false);
        }else{
            view = convertview;
        }

        ((TextView)view.findViewById(R.id.textviewset)).setText(String.valueOf(position+1));

        return view;
    }
}
