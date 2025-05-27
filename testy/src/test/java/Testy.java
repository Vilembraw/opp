import org.example.database.DatabaseConnection;
import org.example.music.Playlist;
import org.example.music.Song;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @BeforeAll
    public static void connect(){
        DatabaseConnection.connect("songs.db", "testy");
    }

    @Test
    public void zlyindexAdnotacje() {
        String sql = "SELECT artist, title, length FROM song WHERE id = ?";
        try {
            int index = 2137;
            PreparedStatement statement = DatabaseConnection.getConnection("testy").prepareStatement(sql);
            statement.setInt(1, index);
            ResultSet resultSet = statement.executeQuery();
            Optional<Song> songOption = Optional.empty();

            if (resultSet.next()) {
                songOption =  Optional.of(new Song(resultSet.getString("artist"), resultSet.getString("title"), resultSet.getInt("length")));
            }

            assertEquals(false, songOption.isPresent());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        @AfterAll
    public static void disconnect(){
        DatabaseConnection.disconnect("testy");
    }



    static Stream<Arguments> provideIndexesAndExpectedSongs() {
        return Stream.of(
                Arguments.of(1,"Hey Jude"),
                Arguments.of(2,"(I Can't Get No) Satisfaction"),
                Arguments.of(3,"Stairway to Heaven")

        );
    }

    @ParameterizedTest
    @MethodSource("provideIndexesAndExpectedSongs")
    public void parametryzowany(int index, String expectedSong){
        Song.Persistance persistance = new Song.Persistance();
        Song s = persistance.read(index).get();
        assertEquals(expectedSong, s.title());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./songs.csv", numLinesToSkip = 1)
    public void parametryzowanycsv(int index, String artist, String expectedSong, int duration){
        Song.Persistance persistance = new Song.Persistance();
        Song s = persistance.read(index).get();
        assertEquals(expectedSong, s.title());
    }





}
