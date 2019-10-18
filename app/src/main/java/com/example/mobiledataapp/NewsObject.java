package com.example.mobiledataapp;

import java.io.Serializable;

public class NewsObject implements Serializable {

    String author;
    String title;
    String description;
    String url;
    String imageUrl;
    String site;

    public NewsObject(String author, String title, String description, String url, String imageUrl, String site) {
        this.author = setString(author);
        this.title = setString(title);
        this.description = setString(description);
        this.url = setString(url);
        this.imageUrl = setString(imageUrl);
        this.site = setString(site);
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSite() {
        return site;
    }

    public String setString(String str){
        if (str==null||str=="null") {
            return "";
        }
        else{
            return str;
        }
    }
}
