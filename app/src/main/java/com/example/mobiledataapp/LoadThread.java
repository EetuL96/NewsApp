package com.example.mobiledataapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoadThread extends Thread {

    String url;
    LoadThreadInterface cb;

    public LoadThread(String URLString, LoadThreadInterface cb) {
        this.url = URLString;
        this.cb = cb;
    }

    public interface LoadThreadInterface{
        void returnJSONString(String responseString);
        Context getContext();
    }

    @Override
    public void run() {
        super.run();
        loadJSONString(url);
    }

    private void loadJSONString(String url) {
        try {
            RequestQueue queue = Volley.newRequestQueue(cb.getContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String responseString) {
                            cb.returnJSONString(responseString);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("kimmo", "onErrorResponse:");
                }
            });
            queue.add(stringRequest);
        } catch (Exception e) {
            Log.d("kimmo", "load failed");
        }
    }
}
