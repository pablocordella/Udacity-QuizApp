package com.example.android.quizapp;

import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Pablo on 23-Jul-16.
 */
class EditTextQuestion extends BaseQuestion  {
    String correctText;

    public EditTextQuestion(String title, String correctText) {
        this.correctText = correctText;
        this.title = title;
    }

    @Override
    public boolean IsCorrect(LinearLayout parentView) {
        // Log.i("tsdaa", "asdad");
        EditText editText = (EditText)parentView.getChildAt(1);
        //Log.i("gettext", editText.getText().toString());
        //Log.i("gettext", correctText);
        return (editText.getText().toString().trim().equals(correctText));
    }
}
