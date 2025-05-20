import org.example.Playlist;
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
}
