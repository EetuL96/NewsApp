package com.example.mobiledataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SingleNewsActivity extends AppCompatActivity {

    String TAG = "kimmo";
    ImageView imageView = null;
    TextView title = null;
    TextView description = null;
    TextView source = null;
    NewsObject newsObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_news);

        imageView = findViewById(R.id.image);
        title = findViewById(R.id.txtTitle);
        description = findViewById(R.id.txtDescription);
        source = findViewById(R.id.txtSource);

        Intent intent = getIntent();
        newsObject = (NewsObject) intent.getSerializableExtra("news");
        title.setText(newsObject.getTitle());
        if(newsObject.getImageUrl()!=""){
            Picasso.with(this).load(newsObject.getImageUrl()).into(imageView);
        }
        description.setText(newsObject.getDescription());
        source.setText(newsObject.getAuthor() + "\n" + newsObject.getSite());
    }

    public void onClick(View view){
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsObject.getUrl()));
            startActivity(browserIntent);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
