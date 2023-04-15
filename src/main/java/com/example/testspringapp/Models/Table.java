package com.example.testspringapp.Models;

import lombok.Data;

@Data
public class Table {
    private String[] columnName;
    private String[] dataType;
    private String[] constraintType;

    public Table() {
    }

    public Table(String[] columnName, String[] dataType, String[] constraintType) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.constraintType = constraintType;
    }
}

