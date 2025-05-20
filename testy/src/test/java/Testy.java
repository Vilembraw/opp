import org.example.Playlist;
import org.example.Song;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

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
    public void playlistzaduzo(){
//      1g
        Playlist playlist = new Playlist();
        Song song = new Song("Dio","Holydiver", 60);
        Song song1 = new Song("Iron Maiden","Fear of the Dark", 60);
        playlist.add(song);
        playlist.add(song1);
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> playlist.atSecond(1000)
        );
        assertEquals("zadany czas jest pozniejszy niz czas odtwarzania playlisty", exception.getMessage());    }

    @Test
    public void playlistujemna(){
//      1g
        Playlist playlist = new Playlist();
        Song song = new Song("Dio","Holydiver", 60);
        Song song1 = new Song("Iron Maiden","Fear of the Dark", 60);
        playlist.add(song);
        playlist.add(song1);
        Exception exception = assertThrows(
                IndexOutOfBoundsException.class,
                () -> playlist.atSecond(-1)
        );
        assertEquals("zadany czas jest ujemny", exception.getMessage());
    }


    @Test
    public void odczyt(){
        Song.Persistance persistance = new Song.Persistance();
        Optional<Song> s1 = persistance.read(1);
        assertEquals(new Song("The Beatles","Hey Jude",431), s1.get());
    }

    @Test
    public void zlyindex(){
        Song.Persistance persistance = new Song.Persistance();
        Optional<Song> s1 = persistance.read(2137);
        assertEquals(false, s1.isPresent());
    }



}
