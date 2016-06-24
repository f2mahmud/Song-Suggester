package com.example.gui.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fahim on 2016-04-19.
 */
public class DBAcess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DBAcess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DBAcess(Context context) {
        DBHandler db = new DBHandler(context);
        //Uncomment this if database has to be upgraded
        db.setForcedUpgrade();
        this.openHelper = db;
    }

    //Table name for Songs
    private static final String TABLE_SONG = "Song";
    private static final String TABLE_MOOD = "MoodID";

    //Table Columns for song
    private static final String ID     = "_id";
    private static final String NAME   = "Name";
    private static final String ARTIST = "Artist";
    private static final String ALBUM  = "Album";
    private static final String MOOD   = "Mood";
    private static final String GENRE  = "Genre";
    private static final String SAVED  = "IsSaved";


    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DBAcess getInstance(Context context) {
        if (instance == null) {
            instance = new DBAcess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    // Add new song
    public void addSong(Song song){

        ContentValues values = new ContentValues();
        values.put(NAME,   song.getName());
        values.put(ARTIST, song.getArtist());
        values.put(GENRE,  song.getGenre());
        values.put(MOOD,   song.getMood());
        values.put(SAVED,  song.getIsSaved());

        database.insert(TABLE_SONG, null, values );
        Log.d("DBHandler", "Song :" + song.getName() + "added!");

    }

    //Get suggested songs that match the mood
    public ArrayList<Song> getSuggestedSongs(Integer moodId){
        String id = moodId.toString();
        String query = "SELECT " + TABLE_SONG + ".*, " + MOOD   +
                       " FROM "  + TABLE_SONG +
                            " INNER JOIN " + TABLE_MOOD + " ON "+  TABLE_MOOD + "." + ID + "=" + TABLE_SONG + "." + ID +
                       " WHERE "  + TABLE_MOOD + "." + ID + "=" + id;

//        String query = "SELECT * FROM "+ TABLE_SONG + " WHERE " + TABLE_MOOD + ".ID = " + moodId;

        Cursor cursor = database.rawQuery(query, null);

        ArrayList<Song> suggestedSongs = new ArrayList<Song>();

        cursor.moveToFirst();
        int c = 0;
        while (!cursor.isAfterLast()){
            c = cursor.getInt(cursor.getColumnIndex(ID));
            if(c == moodId){
                Song suggestedSong = new Song("","","","", 0 , 0 );

                suggestedSong.setName   (cursor.getString(cursor.getColumnIndex(NAME)));
                suggestedSong.setArtist (cursor.getString(cursor.getColumnIndex(ARTIST)));
                //suggestedSong.setAlbum  (cursor.getString(cursor.getColumnIndex(ALBUM)));
                suggestedSong.setMood   (cursor.getInt(cursor.getColumnIndex(MOOD)));
                suggestedSong.setGenre  (cursor.getString(cursor.getColumnIndex(GENRE)));
                suggestedSong.setIsSaved(cursor.getInt(cursor.getColumnIndex(SAVED)));
                suggestedSongs.add(suggestedSong);
                cursor.moveToNext();
            }
        }
        database.close();
        return suggestedSongs;
    }

    //Get Saved Songs
    public List<Song> getSavedSongs(){

        String query =  "SELECT * FROM "  + TABLE_SONG + " WHERE " +
                        SAVED +"= 1";

        Cursor cursor = database.rawQuery(query, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        List<Song> savedSongs = new ArrayList<Song>();


        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            if(cursor.getInt(cursor.getColumnIndex(SAVED)) != 0){
                Song savedSong = new Song("","","","",0,0);
                savedSong.setName    (cursor.getString(cursor.getColumnIndex(NAME)));
                savedSong.setArtist  (cursor.getString(cursor.getColumnIndex(ARTIST)));
                savedSong.setMood    (cursor.getInt(cursor.getColumnIndex(ID)));
                savedSong.setGenre   (cursor.getString(cursor.getColumnIndex(GENRE)));
                savedSong.setIsSaved (cursor.getInt(cursor.getColumnIndex(SAVED)));

                savedSongs.add(savedSong);
                cursor.moveToNext();
            }
        }
        return savedSongs;
    }

    //update song
    public void updateSong(Song song){

        String query = "UPDATE " + TABLE_SONG + " SET " +
                           SAVED + " = 1 WHERE " + NAME + " = '" + song.getName() + "'";

        Cursor cu = database.rawQuery(query,null);
        cu.moveToFirst();
        cu.close();
    }

    //delete song

    public void deleteSong(Song song){
        database.delete(TABLE_SONG, NAME + " = ?", new String[]{song.getName()});
    }

}
