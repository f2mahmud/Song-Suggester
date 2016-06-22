package com.example.gui.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowTestResults extends Fragment{

    View rootView;
    TextView class1, class2, class3, class4, class5, score1, score2, score3, score4, score5;

    public ShowTestResults(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_show_test_results, container, false);
        initialize();
        return rootView;
    }

    private void initialize() {

        class1 = (TextView) rootView.findViewById(R.id.class1);
        class2 = (TextView) rootView.findViewById(R.id.class2);
        class3 = (TextView) rootView.findViewById(R.id.class3);
        class4 = (TextView) rootView.findViewById(R.id.class4);
        class5 = (TextView) rootView.findViewById(R.id.class5);

        score1 = (TextView) rootView.findViewById(R.id.score1);
        score2 = (TextView) rootView.findViewById(R.id.score2);
        score3 = (TextView) rootView.findViewById(R.id.score3);
        score4 = (TextView) rootView.findViewById(R.id.score4);
        score5 = (TextView) rootView.findViewById(R.id.score5);


        class1.setText(MainActivity.classification.getClassificationName(MainActivity.EXTRAVERSION) + ":");
        class2.setText(MainActivity.classification.getClassificationName(MainActivity.AGREEABLENESS) + ":");
        class3.setText(MainActivity.classification.getClassificationName(MainActivity.CONSCIENTIOUSNESS) + ":");
        class4.setText(MainActivity.classification.getClassificationName(MainActivity.EMOTIONAL_STABILITY) + ":");
        class5.setText(MainActivity.classification.getClassificationName(MainActivity.OPENNESS_TO_EXPERIENCE) + ":");

        score1.setText(MainActivity.classification.getIndexScore(MainActivity.EXTRAVERSION) + "/10");
        score2.setText(MainActivity.classification.getIndexScore(MainActivity.AGREEABLENESS) + "/10");
        score3.setText(MainActivity.classification.getIndexScore(MainActivity.CONSCIENTIOUSNESS) + "/10");
        score4.setText(MainActivity.classification.getIndexScore(MainActivity.EMOTIONAL_STABILITY) + "/10");
        score5.setText(MainActivity.classification.getIndexScore(MainActivity.OPENNESS_TO_EXPERIENCE) + "/10");
    }
}
