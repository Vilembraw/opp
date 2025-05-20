package org.example;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int second){
        for(Song song : this){
            if(song.duration() < second){
                second -= song.duration();
            }else{
                return song;
            }
        }
        return null;
    }
}
