package com.example.rschir6.models;

public enum TypeProduct {
    ELECTRONICS("Электроника"),
    BOOKS("Книги"),
    PLUMBING("Сантехника");
    String name;

    TypeProduct(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
