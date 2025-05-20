import org.example.Playlist;
import org.example.Song;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Testy {

    @Test
    public void pusta(){
        //czy nowo utworzona playlista jest pusta
        Playlist playlist = new Playlist();
        assertEquals(playlist.isEmpty(), true);
    }

    @Test
    public void dodanie1(){
        //Napisz test sprawdzający, czy po dodaniu jednego utworu playlista ma rozmiar 1.
        Playlist playlist = new Playlist();
        playlist.add(new Song("Dio","Holydiver", 60));
        assertEquals(playlist.size(), 1);
    }

    @Test
    public void tensam(){
//        Napisz jest sprawdzający, czy po dodaniu jednego utworu, jest w nim ten sam utwór.        Playlist playlist = new Playlist();
        Playlist playlist = new Playlist();
        Song song = new Song("Dio","Holydiver", 60);
        playlist.add(song);
        assertEquals(playlist.getFirst(), song);
    }

    @Test
    public void playlist(){
//        1f
        Playlist playlist = new Playlist();
        Song song = new Song("Dio","Holydiver", 60);
        Song song1 = new Song("Iron Maiden","Fear of the Dark", 60);
        playlist.add(song);
        playlist.add(song1);
        assertEquals(playlist.atSecond(61), song1);
    }

    @Test
    public void playlist1(){
//      1g
        Playlist playlist = new Playlist();
        Song song = new Song("Dio","Holydiver", 60);
        Song song1 = new Song("Iron Maiden","Fear of the Dark", 60);
        playlist.add(song);
        playlist.add(song1);
        assertEquals(playlist.atSecond(124), song1);
    }




}
