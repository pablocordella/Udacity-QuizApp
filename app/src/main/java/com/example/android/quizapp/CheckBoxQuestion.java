package com.example.android.quizapp;

import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Pablo on 23-Jul-16.
 */
class CheckBoxQuestion extends MultipleQuestion  {
    private ArrayList<Integer> correctIndex;

    public CheckBoxQuestion(String title, ArrayList<Integer> correctIndex, String[] options) {
        this.correctIndex = correctIndex;
        this.options = options;
        this.title = title;
    }

    @Override
    public boolean IsCorrect(LinearLayout parentView) {
        int count = parentView.getChildCount();
        //Log.i("count", count+"");
        for( int i=0; i<count; i++) {
            CheckBox checkBox = (CheckBox)parentView.getChildAt(i);
            boolean contains = correctIndex.contains(i);
            //Log.i("asdf", checkBox.isChecked() + ", contains("+i+"): " + contains);
            if( checkBox.isChecked() && !contains ) {
                return false;
            }

            if( !checkBox.isChecked() && contains ) {
                return false;
            }
        }

        return true;
    }
}
