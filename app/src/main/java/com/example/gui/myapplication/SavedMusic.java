package com.example.gui.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class SavedMusic extends Fragment {

    ListView listView;
    Context context;

    String[] music;// = {"Music1", "Music2","Music3","Music4","Music5","Music6","Music7","Music8"};
    String[] artist;// = {"art1", "art2","art3","art4","art5","art6","art7","art8"};
    String[] genre;// = {"g1", "g2","g3","g4","g5","g6","g7","g8"};
    //TODO: int -> bitmap
    int[] album = {R.mipmap.ic_music_note, R.mipmap.ic_music_note, R.mipmap.ic_music_note, R.mipmap.ic_music_note,
            R.mipmap.ic_music_note, R.mipmap.ic_music_note, R.mipmap.ic_music_note, R.mipmap.ic_music_note};

    public SavedMusic(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_saved_music, container, false);

        context = getContext();

        listView = (ListView) rootView.findViewById(R.id.listView);

        DBAcess db = DBAcess.getInstance(this.getContext());
        db.open();
        List<Song> savedSongs = db.getSavedSongs();
        db.close();

        if (savedSongs.size() == 0){
            music = new String[]{"First Time Here?"};
            artist= new String[]{"Take a quick test and see what music you like!"};
            genre = new String[]{""};
            album = new int[]{R.mipmap.ic_take_test};

        }else{
            int i = 0;
            int size = savedSongs.size();
            music = new String[size];
            artist = new String[size];
            genre = new String[size];
            album = new int[size];
            for(Song song: savedSongs){
                music[i] = song.getName();
                artist[i]= song.getArtist();
                genre[i] = song.getGenre();
                album[i] = R.mipmap.ic_take_test;
                i++;
            }
        }
        listView.setAdapter(new MusicListAdapter((MainActivity) context, music, artist, genre, album));

        return rootView;
    }


}
