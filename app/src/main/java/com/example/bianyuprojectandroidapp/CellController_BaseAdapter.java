package com.example.bianyuprojectandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bianyuprojectandroidapp.UserEntity.History;
import com.example.bianyuprojectandroidapp.UserEntity.Question;

import java.util.ArrayList;
import java.util.List;
//customer adapter
public class CellController_BaseAdapter extends BaseAdapter {

    Context context;
ArrayList<Question> todaysHistory;

    public CellController_BaseAdapter(Context context,  ArrayList<Question> todaysHistory) {
        this.context = context;
        this.todaysHistory = todaysHistory;
    }

    @Override
    public int getCount() {
        return todaysHistory.size();
    }

    @Override
    public Object getItem(int position) {
        return todaysHistory.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,
                        View cellReusableViewObject,
                        ViewGroup parent) {

        Question question = todaysHistory.get(position);

        if (cellReusableViewObject == null) {
            cellReusableViewObject = LayoutInflater.from(context).inflate(R.layout.cell_question,
                                                                          parent,
                                                                         false);
        }
        TextView questionHistoy_tv   = cellReusableViewObject.findViewById(R.id.QuestionHistory_tv);
        TextView answerHistoy_tv    = cellReusableViewObject.findViewById(R.id.AnswerHistory_tv);

         ImageView cell_imageView  = cellReusableViewObject.findViewById(R.id.cell_imageView);

        questionHistoy_tv.setText("Question : "+ question.getQuestion());
        answerHistoy_tv.setText("Your answer: "+question.getYourAnswer() );
//show image instead of boolean, user friendly
        if(question.getRightAnswer()==true)
            cell_imageView.setImageResource(R.drawable.right);
        else
            cell_imageView.setImageResource(R.drawable.wrong);
        return cellReusableViewObject;
    }
}