package com.example.gui.myapplication;

/**
 * Created by Fahim on 2016-04-10.
 */
public class Song {

    private String  name;
    private String  artist;
    private String  genre;
    private Integer mood;
    private String  album;
    private Integer isSaved;

    public Song(String name, String artist, String genre, String album, Integer mood, Integer isSaved){
        this.name   = name;
        this.artist = artist;
        this.genre  = genre;
        this.album  = album;
        this.mood   = mood;
        this.isSaved = isSaved;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }

    public String getArtist(){
        return artist;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getGenre(){
        return genre;
    }

    public void setAlbum(String album ){
        this.album = album;
    }

    public String getAlbum(){
        return album;
    }

    public void setMood(Integer moodId){
        this.mood = mood;
    }

    public Integer getMood(){
        return mood;
    }

    public void setIsSaved(Integer isSaved){
        this.isSaved = isSaved;
    }

    public Integer getIsSaved(){
        return isSaved;
    }

    @Override
    public String toString() {
        return "name: " + this.name + " " + "album: " + this.album + " " + "artist: " + this.artist+ " " + "genre: " +  this.genre;
    }

}
