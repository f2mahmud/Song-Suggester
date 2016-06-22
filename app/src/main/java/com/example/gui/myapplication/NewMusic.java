package com.example.gui.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

// >>> DISCOVER page <<<
public class NewMusic extends Fragment {

    View rootView;
    TextView newMusic, newArtist, newGenre, firstTime;
    ImageButton like, dislike;
    ImageView newAlbum;

    int databasePosition;
    String music;
    String artist;
    String genre;
    Context context;
    ArrayList<Song> answers = new ArrayList<>();
    Song answer;
    int quizScore = 0;

    /*
     * Test scores
     */
    Integer extra;
    Integer agree;
    Integer consci;
    Integer emo;
    Integer openExp;

    //TODO: int -> bitmap
    int album = R.mipmap.ic_music_note;
    //ArrayList<Song> answers = new ArrayList<Song>();
    //Song answer = null;

    public NewMusic() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_music, container, false);

        quizScore = MainActivity.classification.getSumScores();

        // getting scores for each class
        extra = MainActivity.classification.getIndexScore(MainActivity.EXTRAVERSION);
        agree = MainActivity.classification.getIndexScore(MainActivity.AGREEABLENESS);
        consci = MainActivity.classification.getIndexScore(MainActivity.CONSCIENTIOUSNESS);
        emo = MainActivity.classification.getIndexScore(MainActivity.EMOTIONAL_STABILITY);
        openExp = MainActivity.classification.getIndexScore(MainActivity.OPENNESS_TO_EXPERIENCE);


        System.out.println("(onCreateView) extra=" + extra);
        System.out.println("(onCreateView) agree=" + agree);
        System.out.println("(onCreateView) consci=" + consci);
        System.out.println("(onCreateView) emo=" + emo);
        System.out.println("(onCreateView) openExp=" + openExp);

        System.out.println("(onCreateView) quizScore=" + quizScore);


        initialize();

        return rootView;
    }


    public void getAnswer(){
        int[] allScores = MainActivity.classification.getAllScores();
        String currentMood = null;
        int highestScore = 1;
        int currentScore = 0;

        System.out.print("Printing all the scores:");
        for(int n : allScores)  {
            System.out.print(n + " ");
        }
        System.out.println("\n");

        int matchingMoodId = determineMood(allScores);
        printMood(matchingMoodId); // debug print of mood

            /*for(int i = 0; i < allScores.length; i++){
                currentScore = MainActivity.classification.getIndexScore(i);
                if (currentScore > highestScore){
                    highestScore = currentScore;
                    currentMood  = MainActivity.classification.getClassificationName(i);
                }
            }*/

        DBAcess db = DBAcess.getInstance(this.getContext());
        db.open();
        System.out.println("highestScore=" + highestScore);
        ArrayList<Song> suggestedSongs = db.getSuggestedSongs(highestScore);

        // test print of all songs fetched from database
        for(Song song : suggestedSongs) {
            System.out.print(song.toString());
        }
        System.out.println();

        db.close();
        songChoice(suggestedSongs);

    }

    public static void printMood(int moodVal) {
        switch (moodVal) {
            case 0:
                System.out.println("Unable to determine mood.");
                break;
            case 1:
                System.out.println("You are reflexive & complex");
                break;
            case 2:
                System.out.println("You are intense & rebellious");
                break;
            case 3:
                System.out.println("You are upbeat & conventional");
                break;
            case 4:
                System.out.println("You are energetic & rhythmic");
                break;
            default:

        }
    }

    /* ************************************************
     * Returns a mood value based on test scores      *
     * >>>> mood values:                              *
     * indeterminate=0, reflexive & complex=1         *
     * intense & rebellious=2, upbeat & conventional=3*
     * energetic & rhythmic=4                         *
     **************************************************/
    public static int determineMood(int[] scoreArray)
    {
        int moodValue = 0;

        // unpack scores
        int extraversion = scoreArray[0];
        int agreeableness = scoreArray[1];
        int conscientious = scoreArray[2];
        int emotion = scoreArray[3];
        int openness = scoreArray[4];

        // intense & rebellious
        if(agreeableness < 3 && conscientious < 3) {
            moodValue = 2;
        }
        // reflective & complex
        else if((openness > 7) && (conscientious==4 || conscientious==5)) {
            moodValue = 1;
        }
        // energetic & rhythmic
        else if((extraversion > 7) && (agreeableness==6) || (agreeableness==7)){
            moodValue = 4;
        }
        // upbeat& conventional
        else if((extraversion > 7) && (openness < 4)) {
            moodValue = 3;
        }
        // WARNING: 0 is returned if we were unable to determine a moodValue!
        return moodValue;
    }

    public ArrayList<Song> nextSong(ArrayList<Song> lst){
        lst.remove(0);

        return lst;
    }

    private void songChoice(final ArrayList<Song> songs){

        answer = songs.get(0);
        newMusic.setText(answer.getName());
        newArtist.setText(answer.getArtist());
        newGenre.setText(answer.getGenre());

        final Context context = this.getContext();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // event handler for like button
                answers = nextSong(songs);
                final DBAcess db = DBAcess.getInstance(context);
                if(songs.size() != 0) {
                    answer.setIsSaved(1);

                    db.open();
                    db.updateSong(answer);
                    db.close();
                    songChoice(answers);
                }else{
                    answer.setIsSaved(1);// save song as like button was pressed
                    db.open();
                    db.updateSong(answer);
                    db.close();
                    quizScore = 0;
                    initialize();
                }
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // event handler for dislike btn
                answers = nextSong(songs); // moves to the next song by discarding first song from list
                if(answers.size() != 0) {
                    answer = answers.get(0);
                    songChoice(answers);
                }else{
                    quizScore = 0;
                    initialize();
                }
            }
        });
    }
    private void initialize() {
        firstTime = (TextView) rootView.findViewById(R.id.tvFirstTime);
        newMusic = (TextView) rootView.findViewById(R.id.suggestedSongName);
        newArtist = (TextView) rootView.findViewById(R.id.songArtistName);
        newGenre = (TextView) rootView.findViewById(R.id.songGenre);
        newAlbum = (ImageView) rootView.findViewById(R.id.songAlbumArtwork);
        like = (ImageButton) rootView.findViewById(R.id.like);
        dislike = (ImageButton) rootView.findViewById(R.id.dislike);

        if (music != null){
            System.out.println("music !=null");
            newMusic.setText(music);
            newArtist.setText(artist);
            newGenre.setText(genre);
            //TODO: int -> bitmap
            newAlbum.setImageResource(album);
        }

        if (quizScore == 0){
            System.out.println("---(initialise) quizScore="+quizScore);
            firstTime.setVisibility(View.VISIBLE);
            newMusic.setVisibility(View.INVISIBLE);
            newArtist.setVisibility(View.INVISIBLE);
            newAlbum.setVisibility(View.INVISIBLE);
            newGenre.setVisibility(View.INVISIBLE);
            like.setVisibility(View.INVISIBLE);
            dislike.setVisibility(View.INVISIBLE);
        }else {
            System.out.println("==0>>>(initialise) quizScore="+quizScore);
            firstTime.setVisibility(View.INVISIBLE);
            newMusic.setVisibility(View.VISIBLE);
            newArtist.setVisibility(View.VISIBLE);
            newAlbum.setVisibility(View.VISIBLE);
            newGenre.setVisibility(View.VISIBLE);
            like.setVisibility(View.VISIBLE);
            dislike.setVisibility(View.VISIBLE);

            getAnswer();

        }

    }



    }

