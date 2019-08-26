package com.example.springfirebasedemo.models.enumclass;

public enum  Category {
    Animal("A1"),
    Comic("C1"),
    Computer("C2"),
    History("H1"),
    Novel("N1");

    private String catCode;

    Category(String catCode){
        this.catCode = catCode;
    }

    public String getCatCode() {
        return catCode;
    }
}
