package com.example.g3bilabonnement.entity.helper;

public class Button {

    String name;
    String path;

    public void setPath(String path) {
        this.path = path;
    }

    public Button(String name, String path){
        this.name=name;
        this.path=path;
    }


    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
    }
}
