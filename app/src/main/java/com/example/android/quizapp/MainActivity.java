package com.example.android.quizapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    float SCALE_DP;

    HashMap<Integer, Question> questionsHashMap = new HashMap<Integer, Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SCALE_DP = getResources().getDisplayMetrics().density;

        LinearLayout baseLayout = (LinearLayout) findViewById(R.id.base_layout_id);
        int idOffset = 10;
        int i;
        for (i=0; i < 3; i++) {
            RadioButtonQuestion radioButtonQuestion = new RadioButtonQuestion("title: " + i, i, new String[]{"1", "2"});
            questionsHashMap.put(i+idOffset, radioButtonQuestion);
            baseLayout.addView(CreateRadioButtonQuestionLayout(i+idOffset, radioButtonQuestion) ,baseLayout.getChildCount()-1);
        }

        for (; i < 10; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            list.add(0);
            list.add(1);

            CheckBoxQuestion checkBoxQuestion = new CheckBoxQuestion("hola: " + i, list, new String[]{"1", "a2","3v"});
            questionsHashMap.put(i+idOffset, checkBoxQuestion);
            baseLayout.addView(CreateCheckBoxQuestionLayout(i+idOffset, checkBoxQuestion), baseLayout.getChildCount()-1);
        }

        for( ; i< 12; i++){
            EditTextQuestion editTextQuestion = new EditTextQuestion("title " +i, "asi");
            questionsHashMap.put(i+idOffset, editTextQuestion);
            baseLayout.addView(CreateEditTextQuestionLayout(i+idOffset, editTextQuestion), baseLayout.getChildCount()-1);
        }
    }

    protected View CreateRadioButtonQuestionLayout(int id, RadioButtonQuestion radioButtonQuestion) {
        LinearLayout base = new LinearLayout(this);
        base.setOrientation(LinearLayout.VERTICAL);


        //base.setId(id);
        //Log.i("seteando aid", id+"");
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


       // base.setId(id);
        //Log.i("seteando aid", id+"");
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
        //Log.i("seteando aid", id+"");
        TextView title = CreateQuizQuestionTitleTextView(editTextQuestion.getTitle());

        base.addView(title);

        EditText editText= new EditText(this);
        editText.setHint("test");

        LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        int margin = (int)(25*SCALE_DP);
        layParams.setMargins(0,0,0,margin);
        editText.setLayoutParams( layParams );


        base.addView(editText);

       /* View line = new View(this);
        LinearLayout.LayoutParams layParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(1*SCALE_DP) );
        int margin = (int)(25*SCALE_DP);
        layParams.setMargins(0,margin,0,margin);
        line.setLayoutParams( layParams );
        line.setBackgroundColor(Color.GRAY);
        base.addView(line);*/

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
        Log.i("yay",  questionsHashMap.size()+"");
        for(Map.Entry<Integer, Question> entry : questionsHashMap.entrySet()){
            int key = entry.getKey();
            Question question = entry.getValue();

           // Log.i("key", key+"");
            //Log.i("question", question.toString());
            LinearLayout layout = (LinearLayout) findViewById(key);
            String rspta = "Tuviste: " + question.IsCorrect( layout ) + " (id:" + key + ")";
            Log.i("debug", rspta);
        }
        Log.i("asda", "================================");
    }
}

interface Question {
    public boolean IsCorrect(LinearLayout parentView);
}

class RadioButtonQuestion implements Question {

    private int correctIndex;

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        return options;
    }

    private String[] options;
    private String title;

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

class CheckBoxQuestion implements Question {
    private ArrayList<Integer> correctIndex;
    private String[] options;
    private String title;

    public String getTitle() {
        return title;
    }

    public String[] getOptions() {
        return options;
    }

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

class EditTextQuestion implements Question {
    String correctText;

    public String getTitle() {
        return title;
    }

    String title;

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