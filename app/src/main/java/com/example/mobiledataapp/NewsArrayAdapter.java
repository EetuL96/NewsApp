package com.example.mobiledataapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsArrayAdapter extends ArrayAdapter<NewsObject> {

    public NewsArrayAdapter(Context context, ArrayList<NewsObject> items){
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View newsItemView, ViewGroup parent) {

        if (newsItemView == null) {
            int layoutId = R.layout.news_item;
            newsItemView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        NewsObject item = getItem(position);
        TextView textView = newsItemView.findViewById(R.id.txt);
        String str = item.getTitle() +"\n"+ item.getAuthor();
        textView.setText(str);

        return newsItemView;
    }
}