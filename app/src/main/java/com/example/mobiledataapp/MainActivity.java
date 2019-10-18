package com.example.mobiledataapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements LoadThread.LoadThreadInterface{

    String key;
    int mode;
    ListView listView = null;
    EditText editText = null;
    ArrayList<NewsObject> newsArrayList;
    String TAG = "kimmo";
    JSONArray newsJsonArray;
    String PREFERENCES = "preferences";
    SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.list);
        editText = findViewById(R.id.edit);

        key = this.getResources().getString(R.string.api_key);
        preferences = this.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String strEdit = preferences.getString("editor", "");
        mode = preferences.getInt("mode", 1);
        Log.d(TAG, "onCreate:\nedit: " + strEdit + "\nmode: " + mode);
        editText.setText(strEdit);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsObject newsObject = newsArrayList.get(i);
                Intent intent = new Intent(MainActivity.this, SingleNewsActivity.class);
                intent.putExtra("news", newsObject);
                startActivity(intent);
            }
        });

        Intent introIntent = new Intent(MainActivity.this, IntroActivity.class);
        MainActivity.this.startActivity(introIntent);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.settingKeyword){
            Log.d(TAG, "onOptionsItemSelected: keyword");
            mode = 1;
        }
        else if(item.getItemId() == R.id.settingCountry){
            Log.d(TAG, "onOptionsItemSelected: country");
            mode = 2;
        }
        else if(item.getItemId() == R.id.settingDomain){
            Log.d(TAG, "onOptionsItemSelected: domain");
            mode = 3;
        }
        else if(item.getItemId() == R.id.settingCategory){
            Log.d(TAG, "onOptionsItemSelected: category");
            mode = 4;
        }
        else if(item.getItemId() == R.id.settingSources){
            Log.d(TAG, "onOptionsItemSelected: sources");
            Intent intent = new Intent(this, SourcesActivity.class);
            intent.putExtra("key", key);
            startActivityForResult(intent, 1);
        }
        editText.setText("");
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            mode=5;
            String id = data.getStringExtra("id");
            editText.setText(id);
            load();
        }
    }

    public void onClick(View view){
        load();
    }

    public void load(){
        Log.d(TAG, "load: ");
        String search = editText.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("editor", search);
        editor.putInt("mode", mode);
        editor.apply();
        String url = "";
        if(mode==1){
            url = "https://newsapi.org/v2/top-headlines?q="+search+"&apiKey="+key;
        }
        else if(mode==2){
            url = "https://newsapi.org/v2/top-headlines?country="+search+"&apiKey="+key;
        }
        else if(mode==3){
            url = "https://newsapi.org/v2/everything?domains="+search+"&apiKey="+key;
        }
        else if(mode==4){
            url = "https://newsapi.org/v2/top-headlines?category="+search+"&apiKey="+key;
        }
        else if(mode==5){
            url = "https://newsapi.org/v2/top-headlines?sources="+search+"&apiKey="+key;
            //"https://newsapi.org/v2/top-headlines?sources="+search+"&apiKey="+key;
        }
        LoadThread loadThread = new LoadThread(url, this);
        loadThread.start();
    }

    public void returnJSONString(String response){
        newsJsonArray = parseJSONArray(response);
        newsArrayList = parseNewsObject(newsJsonArray);
        Log.d(TAG, "returnJSONString: " + newsArrayList.size());
        NewsArrayAdapter aa = new NewsArrayAdapter(this, newsArrayList);
        listView.setAdapter(aa);
    }

    public Context getContext(){
        return this;
    }

    public ArrayList<NewsObject> parseNewsObject(JSONArray jsonArray){
        Log.d(TAG, "parseNewsObject: ");
        int length = jsonArray.length();
        ArrayList<NewsObject> arrayList = new ArrayList<>();
        for(int i=0;i<length;i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String author = jsonObject.get("author").toString();
                String title = jsonObject.get("title").toString();
                String description = jsonObject.get("description").toString();
                String url = jsonObject.get("url").toString();
                String imageUrl = jsonObject.get("urlToImage").toString();
                String site = jsonObject.getJSONObject("source").getString("name");
                NewsObject newsObject = new NewsObject(author, title, description, url, imageUrl, site);
                arrayList.add(newsObject);
            }
            catch (Exception e){
                Log.d(TAG, "parseNewsObject: error");
            }
        }
        Log.d(TAG, "parseNewsObject: return arraylist " + arrayList.toString());
        return arrayList;
    }

    public JSONArray parseJSONArray(String jsonString){
        Log.d(TAG, "parseJSONArray: ");
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonArray = jsonObject.getJSONArray("articles");
            Log.d(TAG, "parseJSONArray: " + jsonArray.toString());
        }
        catch (Exception e){
            Log.d(TAG, "parseJSONArray: error");
            e.printStackTrace();
        }
        return jsonArray;
    }


}
