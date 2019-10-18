package com.example.mobiledataapp;

public class SourceObject {

    String id;
    String name;
    String description;
    String category;
    String language;
    String country;

    public SourceObject(String id, String name, String description, String category, String language, String country) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.language = language;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }
}
