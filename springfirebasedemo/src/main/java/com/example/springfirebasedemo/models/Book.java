package com.example.springfirebasedemo.models;

import lombok.Data;

@Data
public class Book {

    private String name,code,category,status,author,recap;

    public Book(){

    }

    public Book(String name, String code, String category, String status, String author, String recap) {
        this.name = name;
        this.code = code;
        this.category = category;
        this.status = status;
        this.author = author;
        this.recap = recap;
    }

//    //======================= Getter =======================//
//    public String getName() {
//        return name;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public String getAuthor() {
//        return author;
//    }
//
//    public String getRecap() {
//        return recap;
//    }
//    //======================= Getter =======================//
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public void setAuthor(String author) {
//        this.author = author;
//    }
//
//    public void setRecap(String recap) {
//        this.recap = recap;
//    }
}
