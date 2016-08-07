package com.example.android.quizapp;

import android.widget.LinearLayout;

/**
 * Created by Pablo on 23-Jul-16.
 */
class BaseQuestion {
    public String getTitle() {
        return title;
    }

    protected String title;

    public boolean IsCorrect(LinearLayout parentView) { return false; }
}
