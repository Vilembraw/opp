package org.example;

import org.example.auth.Account;
import org.example.auth.ListenerAccount;
import org.example.database.DatabaseConnection;
import org.example.music.Song;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseConnection.connect("users.db");
        ListenerAccount.Persistence.init();
        ListenerAccount.Persistence.register("test","test");

        DatabaseConnection.connect("songs.db");
        Song.Persistance persistance = new Song.Persistance();
//        persistance.
    }
}