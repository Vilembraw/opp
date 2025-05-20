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
}
