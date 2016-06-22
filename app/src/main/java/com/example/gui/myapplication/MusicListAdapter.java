package com.example.gui.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicListAdapter extends BaseAdapter{

    String[] musicName, artistName, genre;
    //TODO: change int to Bitmap?
    int[] album;
    Context context;

    private static LayoutInflater inflater = null;

    //TODO: int -> bitmap
    public MusicListAdapter(MainActivity mainActivity, String[] musicNames, String[] artistNames, String[] genres, int[] albums){
        musicName = musicNames;
        artistName = artistNames;
        genre = genres;
        album = albums;
        inflater = (LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return musicName.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        TextView tvMusic, tvArtist, tvGenre;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.music_list, null);

        holder.tvMusic = (TextView) rowView.findViewById(R.id.tvMusicName);
        holder.tvArtist = (TextView) rowView.findViewById(R.id.tvArtistName);
        holder.tvGenre = (TextView) rowView.findViewById(R.id.tvGenre);
        holder.img = (ImageView) rowView.findViewById(R.id.ivMusicAlbum);

        //TODO: int -> bitmap
        holder.tvMusic.setText(musicName[position]);
        holder.tvArtist.setText(artistName[position]);
        holder.tvGenre.setText(genre[position]);
        holder.img.setImageResource(album[position]);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: do something when a music is clicked in the music view?
            }
        });

        return rowView;
    }
}
