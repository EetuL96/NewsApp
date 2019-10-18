package com.example.mobiledataapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SourceArrayAdapter extends ArrayAdapter<SourceObject> {

    public SourceArrayAdapter(Context context, ArrayList<SourceObject> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View sourceItemView, ViewGroup parent) {

        if (sourceItemView == null) {
            int layoutId = R.layout.source_item;
            sourceItemView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        SourceObject item = getItem(position);
        TextView textViewBig = sourceItemView.findViewById(R.id.txtBig);
        TextView textViewSmall = sourceItemView.findViewById(R.id.txtSmall);
        String big = item.getName() +"\n"+ item.getDescription();
        String small = "category: " + item.getCategory() + "\nlanguage: " + item.getLanguage() + "\ncountry: " + item.getCountry();
        textViewBig.setText(big);
        textViewSmall.setText(small);

        return sourceItemView;
    }
}