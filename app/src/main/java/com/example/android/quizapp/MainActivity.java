package com.example.android.quizapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    float SCALE_DP;

    HashMap<Integer, BaseQuestion> questionsHashMap = new HashMap<Integer, BaseQuestion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SCALE_DP = getResources().getDisplayMetrics().density;

        ArrayList<BaseQuestion> metaData = new ArrayList<>();
        metaData.add( new RadioButtonQuestion(
                getResources().getString(R.string.question_1_title),
                2,
                new String[]{
                        getResources().getString(R.string.banana_text),
                        getResources().getString(R.string.avocado_text),
                        getResources().getString(R.string.tomato_text)
                }
        ));

        metaData.add( new RadioButtonQuestion(
                getResources().getString(R.string.question_2_title),
                0,
                new String[]{
                        getResources().getString(R.string.banana_text),
                        getResources().getString(R.string.strawberry_text),
                        getResources().getString(R.string.tomato_text)
                }
        ));


        metaData.add( new CheckBoxQuestion(
                getResources().getString(R.string.question_3_title),
                new ArrayList<Integer>( Arrays.asList(0,1) ),
                new String[]{
                        getResources().getString(R.string.banana_text),
                        getResources().getString(R.string.pineapple_text),
                        getResources().getString(R.string.tomato_text)
                }
        ));

        metaData.add( new EditTextQuestion(
                getResources().getString(R.string.question_4_title),
                getResources().getString(R.string.question_4_answer)
            ));

        LinearLayout baseLayout = (LinearLayout) findViewById(R.id.base_layout_id);

        int idOffset = 10;
        for (int i=0; i < metaData.size(); i++) {
            baseLayout.addView(LayoutCreator(i+idOffset, metaData.get(i)), baseLayout.getChildCount()-1);
            questionsHashMap.put(i+idOffset,metaData.get(i) );
        }
    }

    protected View LayoutCreator(int id, BaseQuestion question) {
        if( question instanceof RadioButtonQuestion ) {
            return  CreateRadioButtonQuestionLayout(id, (RadioButtonQuestion)question);
        } else if(  question instanceof  EditTextQuestion ){
            return CreateEditTextQuestionLayout(id, (EditTextQuestion)question);
        }
        else {
            return CreateCheckBoxQuestionLayout(id, (CheckBoxQuestion)question);
        }
    }

    protected View CreateRadioButtonQuestionLayout(int id, RadioButtonQuestion radioButtonQuestion) {
        LinearLayout base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);

        TextView title = CreateQuizQuestionTitleTextView(radioButtonQuestion.getTitle());

        base.addView(title);

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setId(id);

        for (int i = 0; i < radioButtonQuestion.getOptions().length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(radioButtonQuestion.getOptions()[i]);
            radioGroup.addView(radioButton);
        }
        base.addView(radioGroup);

        View line = new View(this);
        LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(1*SCALE_DP) );
        int margin = (int)(25*SCALE_DP);
        layParams.setMargins(0,margin,0,margin);
        line.setLayoutParams( layParams );
        line.setBackgroundColor(Color.GRAY);

        base.addView(line);

        return base;
    }

    protected View CreateCheckBoxQuestionLayout(int id, CheckBoxQuestion checkBoxQuestion) {
        LinearLayout base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);

        TextView title = CreateQuizQuestionTitleTextView(checkBoxQuestion.getTitle());

        base.addView(title);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setId(id);

        for (int i = 0; i < checkBoxQuestion.getOptions().length; i++) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(checkBoxQuestion.getOptions()[i]);
            layout.addView(checkBox);
        }

        base.addView(layout);

        View line = new View(this);
        LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(1*SCALE_DP) );
        int margin = (int)(25*SCALE_DP);
        layParams.setMargins(0,margin,0,margin);
        line.setLayoutParams( layParams );
        line.setBackgroundColor(Color.GRAY);
        base.addView(line);

        return base;
    }

    protected View CreateEditTextQuestionLayout(int id, EditTextQuestion editTextQuestion) {
        LinearLayout base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);

        base.setId(id);
        TextView title = CreateQuizQuestionTitleTextView(editTextQuestion.getTitle());

        base.addView(title);

        EditText editText= new EditText(this);

        LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        int margin = (int)(25*SCALE_DP);
        layParams.setMargins(0,0,0,margin);
        editText.setLayoutParams( layParams );

        base.addView(editText);
        return base;
    }

    protected TextView CreateQuizQuestionTitleTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setGravity(Gravity.LEFT);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        int margin = (int)(18*SCALE_DP);
        layParams.setMargins(0,0,0,margin);
        textView.setLayoutParams( layParams );


        return textView;
    }

    public void onSubmit(View view) {
        int goodAnswers = 0;
        for(Map.Entry<Integer, BaseQuestion> entry : questionsHashMap.entrySet()){
            int key = entry.getKey();
            BaseQuestion question = entry.getValue();
            LinearLayout layout = (LinearLayout) findViewById(key);
            goodAnswers = question.IsCorrect( layout ) ? goodAnswers +1 : goodAnswers;
        }
        String result = MessageFormat.format(getResources().getString(R.string.result_text), goodAnswers);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }
}