package com.example.testspringapp.Serialization;

import com.example.testspringapp.Models.Table;

import com.google.gson.Gson;

public class TableSerializer {

    public String serialize(Table table){
        Gson gson = new Gson();
        return gson.toJson(table);
    }

}