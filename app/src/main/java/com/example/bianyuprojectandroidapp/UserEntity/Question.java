package com.example.bianyuprojectandroidapp.UserEntity;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
// question class
public class Question implements Serializable,Cloneable {
    public  String question="";
    public  String yourAnswer="";
    public Boolean rightAnswer=false;
    public Question() {
    }

    public Question(String question, String yourAnswer, Boolean rightAnswer) {
        this.question = question;
        this.yourAnswer = yourAnswer;
        this.rightAnswer = rightAnswer;
    }

    public String getQuestion() {
        return question;
    }
    public void setQuestion(int level) {
        int number1, number2 ,number3,number4;
        Double num2, num1;
        int operatorIndex1,operatorIndex2;
// for user friendly use '×', '÷' instead of "/","*"
        char[] operator = {'+', '-', '×', '÷'};

      //generate question selon the game level
        switch (level) {
            // level one, only int with +/-
            case 1:
                number1 = (int) randomNum(11);
                number2 = (int) randomNum(11);
                operatorIndex1 =(int) randomNum(2);
                question = number1 + String.valueOf(operator[operatorIndex1]) + number2;
                break;
            // level two, only int with +/-/*//
            case 2:
                do {
                    number1 = (int) randomNum(11);
                    number2 = (int) randomNum(11);
                    operatorIndex1 = (int) randomNum(4);
              // make sure 0 doesn't come after /
                }while (operatorIndex1==3&&number2==0);

                question = number1 + String.valueOf(operator[operatorIndex1]) + number2;
                break;
         //level three double with +/-/*//
            case 3:
                do {
                    num1 = getDouble();
                    num2 = getDouble();
                    operatorIndex1 = (int) randomNum(4);
                }

            // make sure 0 doesn't come after /
                while (
                        operatorIndex1==3&& num2==0
                );
                question = num1 + String.valueOf(operator[operatorIndex1]) + num2;
                break;
        }
    }

//future use
    protected Question clone() {
        Question clone = null;
        try{
            clone = (Question) super.clone();

        }catch(CloneNotSupportedException e){
            throw new RuntimeException(e);
        }

        return clone;
    }

    public Boolean getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(Boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    // set user's answer and raplace back operators
    public void setYourAnswer(String yourAnswer) {
String questionMark=question.replace('÷','/');
        questionMark=  questionMark.replace('×','*');

        String answerMark= yourAnswer.replace('×','*');
        answerMark=answerMark.replace('÷','/');



        // use javascripte engin to test if user's answer is right
        if(yourAnswer.length()!=0)
        {    this.yourAnswer = answerMark;
      ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
      try {
            Object res1 = engine.eval(questionMark);
            Object res = engine.eval(this.yourAnswer);
            if (res.equals(res1)&&!questionMark.equals(answerMark)) {

        //set if user enter the right answer
          this.rightAnswer = true;
         } else {
                this. rightAnswer = false;
          }

        } catch (ScriptException e) {

        }
        }else
        {
            this. rightAnswer = false;
        }
    }



// generate random double
    private double randomNum(int i) {
        return Math.random() * i;
    }
    public static double getDouble(){
        Random rand = new Random();
        DecimalFormat df=new DecimalFormat("#.0");
        int a=(int)(Math.random()*2+1);
        int aa=(int)(Math.pow(1, a));
        return Double.valueOf(df.format(rand.nextDouble()*10*aa));
    }


}
