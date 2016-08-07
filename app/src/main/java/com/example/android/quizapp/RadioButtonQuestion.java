package com.example.android.quizapp;

import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * Created by Pablo on 23-Jul-16.
 */
class RadioButtonQuestion extends MultipleQuestion {

    private int correctIndex;
    public RadioButtonQuestion(String title, int correctIndex, String[] options) {
        this.title = title;
        this.correctIndex = correctIndex;
        this.options = options;
    }

    @Override
    public boolean IsCorrect(LinearLayout parentView) {
        //Log.i("IsCorrect", "Iscorrect");

        int count = parentView.getChildCount();
        //Log.i("count", count+"");
        for( int i=0; i<count; i++) {
            RadioButton radioButton = (RadioButton)parentView.getChildAt(i);
            if( radioButton == null ) {
                continue;
            }

            //Log.i("asdf", radioButton.isChecked() + ", correctIndex: " + correctIndex);
            if( radioButton.isChecked() && correctIndex == i ) {
                return true;
            }
        }

        return false;
    }
}
