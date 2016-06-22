package com.example.gui.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Questions extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton disStrong, disFair, neutral, agreeFair, agreeStrong;
    private Button bNext;
    private TextView tvQuestion;
    private int score, currentQuestion;

    //  TODO: set points for all the choices
    private static final int DISAGREE_STRONGLY = 1;
    private static final int DISAGREE_FAIRLY = 2;
    private static final int NEUTRAL = 3;
    private static final int AGREE_FAIRLY = 4;
    private static final int AGREE_STRONGLY = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        initialize();

        if(MainActivity.currentQuestion >= 10) {
            MainActivity.currentQuestion = 0;
        }
        String text = MainActivity.questions[MainActivity.currentQuestion];
        tvQuestion.setText(text);
        currentQuestion = MainActivity.currentQuestion;
        if(currentQuestion == 9){
            bNext.setText("Save");
        }
    }

    private void initialize() {
        radioGroup = (RadioGroup) findViewById(R.id.rgAnswers);
        disStrong = (RadioButton) findViewById(R.id.rbDisStrong);
        disFair = (RadioButton) findViewById(R.id.rbDisFair);
        neutral = (RadioButton) findViewById(R.id.rbNeutral);
        agreeFair = (RadioButton) findViewById(R.id.rbAgFair);
        agreeStrong = (RadioButton) findViewById(R.id.rbAgStrong);

        bNext = (Button) findViewById(R.id.bNext);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (currentQuestion){
                    case 0:
                    case 1:
                        MainActivity.classification.addScore(MainActivity.EXTRAVERSION, score);
                        break;
                    case 2:
                    case 3:
                        MainActivity.classification.addScore(MainActivity.AGREEABLENESS, score);
                        break;
                    case 4:
                    case 5:
                        MainActivity.classification.addScore(MainActivity.CONSCIENTIOUSNESS, score);
                        break;
                    case 6:
                    case 7:
                        MainActivity.classification.addScore(MainActivity.EMOTIONAL_STABILITY, score);
                        break;
                    default:
                        MainActivity.classification.addScore(MainActivity.OPENNESS_TO_EXPERIENCE, score);
                }
                MainActivity.currentQuestion++;
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                switch (checkedId) {
                    case R.id.rbDisStrong:
                        score = DISAGREE_STRONGLY;
                        break;
                    case R.id.rbDisFair:
                        score = DISAGREE_FAIRLY;
                        break;
                    case R.id.rbNeutral:
                        score = NEUTRAL;
                        break;
                    case R.id.rbAgFair:
                        score = AGREE_FAIRLY;
                        break;
                    case R.id.rbAgStrong:
                        score = AGREE_STRONGLY;
                        break;
                }
            }
        });

    }
}
