package com.example.bianyuprojectandroidapp.UserEntity;



import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
// class of history with play date , for future use
public class History implements Serializable{

    String time;
    ArrayList<Question> listOfQuestion;


    public History(String time, ArrayList<Question> listOfQuestion) {
        this.time = time;
        this.listOfQuestion = listOfQuestion;
    }

    public History(String time) {
        this.time = time;
        this.listOfQuestion = new ArrayList<>();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Question> getListOfQuestion() {
        return listOfQuestion;
    }

    public void setListOfQuestion(ArrayList<Question> listOfQuestion) {
        this.listOfQuestion = listOfQuestion;
    }


    // get right percentage

    public String myPercentage() {


        String scorePrecentage = "";
        double rightAnswerD = getRightAnswerQuestion().size() * 1.0;
        double TotalAnswerD = getListOfQuestion().size() * 1.0;
        double scorePrecentageD = rightAnswerD / TotalAnswerD;

        DecimalFormat df1 = new DecimalFormat("##.00%"); // ##.00%

        scorePrecentage = df1.format(scorePrecentageD);
        return scorePrecentage;
    }


    //get all the right answers
    public ArrayList<Question> getRightAnswerQuestion() {
        ArrayList<Question> rightAnserList = new ArrayList<>();
        for (Question q :
                listOfQuestion) {
            if (q.getRightAnswer() == true)
                rightAnserList.add(q);

        }
        return rightAnserList;
    }

    //get all the wrong answers
    public ArrayList<Question> getWrongAnswerQuestion() {
        ArrayList<Question> wrongAnserList = new ArrayList<>();
        for (Question q :
                listOfQuestion) {
            if (q.getRightAnswer() == false)
                wrongAnserList.add(q);

        }
        return wrongAnserList;
    }


//sort right answer on first

    public void getRightAnswerFirstQuestion() {
        ArrayList<Question> newList= null;
        newList=listOfQuestion;
        Collections.sort(newList, new Comparator<Question>() {

            @Override
            public int compare(Question q1, Question q2) {
                boolean answer1 = q1.getRightAnswer();
                boolean answer2 = q2.getRightAnswer();

                if (answer1 ^ answer2) {
                    return answer1 ? -1 : 1;
                } else {
                    return 0;
                }
            }
        });

    }
    //sort wrong  answer on first
    public void getWrongAnswerFirstQuestion() {
ArrayList<Question> newList= null;
newList=listOfQuestion;
        Collections.sort(newList, new Comparator<Question>() {

            @Override
            public int compare(Question q1, Question q2) {
                boolean answer1 = q1.getRightAnswer();
                boolean answer2 = q2.getRightAnswer();

                if (answer1 ^ answer2) {
                    return answer1 ? 1 : -1;
                } else {
                    return 0;
                }
            }
        });

    }


}