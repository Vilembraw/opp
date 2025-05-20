package org.example;

import java.util.ArrayList;

public class Playlist extends ArrayList<Song> {
    public Song atSecond(int second){
        if(this.isEmpty())
            throw new RuntimeException("playlista jest pusta");

        if(second < 0){
            throw new IndexOutOfBoundsException("zadany czas jest ujemny");
        }
        for(Song song : this){
            if(song.duration() < second){
                second -= song.duration();
            }else{
                return song;
            }
        }
        throw new IndexOutOfBoundsException("zadany czas jest pozniejszy niz czas odtwarzania playlisty");
    }
}
