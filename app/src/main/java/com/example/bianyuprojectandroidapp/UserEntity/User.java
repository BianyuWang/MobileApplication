package com.example.bianyuprojectandroidapp.UserEntity;

import java.io.Serializable;
import java.util.List;
// user class
public class User implements Serializable {

    private String name;
    private Gender gender;

    // highest score and longest play time, for future use
    private int highestScore;
    private String LongestTime;


    public User(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public String getLongestTime() {
        return LongestTime;
    }

    public void setLongestTime(String longestTime) {
        LongestTime = longestTime;
    }

    public  enum  Gender {Boy,Girl}
}
