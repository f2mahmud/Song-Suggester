package com.example.gui.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TakeTest extends Fragment {

    View questionView;

    public TakeTest(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        questionView = inflater.inflate(R.layout.fragment_take_test, container, false);
        Intent intent = new Intent(getActivity(), Questions.class);
        startActivity(intent);
        return questionView;
    }

}
