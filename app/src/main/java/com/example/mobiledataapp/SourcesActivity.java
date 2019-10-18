package com.example.mobiledataapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SourcesActivity extends AppCompatActivity implements LoadThread.LoadThreadInterface{

    ArrayList<SourceObject> sourceObjectArrayList;
    ArrayList<SourceObject> filteredSourceArrayList;

    String TAG = "kimmo";
    String categoryFilter = "";
    String languageFilter = "";
    String countryFilter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");

        String url = "https://newsapi.org/v2/sources?apiKey="+key;
        LoadThread loadThread = new LoadThread(url,this);
        loadThread.start();
    }

    public Context getContext(){
        return this;
    }

    public void returnJSONString(String responseString){

        sourceObjectArrayList = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray jsonArray = jsonObject.getJSONArray("sources");
            for(int i=0;i<jsonArray.length();i++){
                jsonObject = (JSONObject) jsonArray.get(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String description = jsonObject.getString("description");
                String category = jsonObject.getString("category");
                String language = jsonObject.getString("language");
                String country = jsonObject.getString("country");
                SourceObject sourceObject = new SourceObject(id, name, description, category, language, country);
                sourceObjectArrayList.add(sourceObject);
            }
        }
        catch (Exception e){

        }
        finally {
            setList(sourceObjectArrayList);
        }
    }

    public void filter(){
        filteredSourceArrayList = new ArrayList<>();
        for(int i=0;i<sourceObjectArrayList.size();i++){
            SourceObject sourceObject = sourceObjectArrayList.get(i);
            if(categoryFilter.equals(sourceObject.getCategory())||categoryFilter.equals("")){
                if(languageFilter.equals(sourceObject.getLanguage())||languageFilter.equals("")){
                    if(countryFilter.equals(sourceObject.getCountry())||countryFilter.equals("")){
                        filteredSourceArrayList.add(sourceObject);
                    }
                }
            }
        }
        setList(filteredSourceArrayList);
    }

    public void setList(ArrayList arrayList){
        ListView listView = findViewById(R.id.sourceList);
        SourceArrayAdapter aa = new SourceArrayAdapter(this, arrayList);
        listView.setAdapter(aa);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = filteredSourceArrayList.get(i).getId();
                Intent resultIntent = new Intent();
                //resultIntent.setData(Uri.parse(id));
                resultIntent.putExtra("id", id);
                setResult(1, resultIntent);
                finish();
            }
        });
    }

    public void onBtnClick(View view){
        EditText edit = findViewById(R.id.editFilter);
        String strEdit = edit.getText().toString();
        if(view.getId()==R.id.btnCategory){
            categoryFilter = strEdit;
        }
        else if(view.getId()==R.id.btnLanguage){
            languageFilter = strEdit;
        }
        else if(view.getId()==R.id.btnCountry){
            countryFilter = strEdit;
        }
        else if(view.getId()==R.id.btnClear){
            categoryFilter = "";
            languageFilter = "";
            countryFilter = "";
        }
        filter();
    }
}
