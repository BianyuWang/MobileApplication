package com.example.bianyuprojectandroidapp.JsonData;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.bianyuprojectandroidapp.UserEntity.History;
import com.example.bianyuprojectandroidapp.UserEntity.Question;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
//future use
public class JSONParser {

    ArrayList<History> listOfHistory = new ArrayList();

    private InputStreamReader inputStreamReader;


    public ArrayList<History> processJSONFile(Context context, String fileName){

        AssetManager assetManager = context.getResources().getAssets();

        try {
            // Pass filename to assetManager.open(fileName). In next step pass it to InputStreamReader
            inputStreamReader = new InputStreamReader(assetManager.open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String oneLine = null;
            StringBuilder stringBuilder = new StringBuilder();

            while((oneLine = bufferedReader.readLine()) != null){
                stringBuilder.append(oneLine);
            }

            bufferedReader.close();
            inputStreamReader.close();

            processJSONData(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfHistory;
    }


    public void processJSONData(String data){

        try {

            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++){

                JSONObject currentJSONHistoryObject = jsonArray.getJSONObject(i);

              String time=currentJSONHistoryObject.getString("time");
              JSONArray listOfQuestionJsonArray=currentJSONHistoryObject.getJSONArray("listOfQuestion");
             ArrayList<Question> listOfQuestion=new ArrayList<>();
               for(int j=0;j<listOfQuestionJsonArray.length();j++)
               {
                   JSONObject currentQuestion = listOfQuestionJsonArray.getJSONObject(j);

                   String question    = currentQuestion.getString("question");
                   String yourAnswer    = currentQuestion.getString("yourAnswer");
                   Boolean rightAnswer   = currentQuestion.getBoolean("rightAnswer");

                   Question newQuestion=new Question(question,yourAnswer,rightAnswer);

                   listOfQuestion.add(newQuestion);
               }

                History currentHistory = new History(time,listOfQuestion);
                listOfHistory.add(currentHistory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}