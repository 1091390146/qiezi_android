package com.example.qiezi.main;

/**
 * Created by 潘 on 2016/3/9.
 */
public class Fruit {
    private String name;
    private int imageId;
    public Fruit(String name , int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
