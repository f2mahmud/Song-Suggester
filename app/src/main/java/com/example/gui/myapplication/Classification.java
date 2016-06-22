package com.example.gui.myapplication;

/*** Created by aZrafuL on 3/16/16. ***/

public class Classification {

    private String[] classificationName;
    // Every time the app opens all scores will be reset to 0
    private int[] totalScore;

    public Classification(){

        classificationName = new String[]{"Extraversion",
                "Agreeableness",
                "Conscientiousness",
                "Emotional Stability",
                "Openness to Experience"};
        totalScore = new int[]{0, 0, 0, 0, 0};
    }

    public int getIndexScore(int index){
        return totalScore[index];
    }

    public int[] getAllScores(){
        return totalScore;
    }

    public void addScore(int index, int score){
        totalScore[index] += score;
    }

    public String getClassificationName(int index){
        return classificationName[index];
    }

    public void resetScore() {
        totalScore = new int[]{0, 0, 0, 0, 0};
    }

    public int getSumScores() {
        int sum = totalScore[0] + totalScore[1] + totalScore[2] + totalScore[3] + totalScore[4];
        return sum;
    }
}
