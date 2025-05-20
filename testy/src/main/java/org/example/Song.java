package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public record Song(String artist, String title, int duration) {


    public static class Persistance{
        Connection connection;
        public Persistance(){
            DatabaseConnection.connect("songs.db");
            this.connection = DatabaseConnection.getConnection();
        }

        public Optional<Song> read(int index){
            String sql = "SELECT artist, title, length FROM song WHERE id = ?";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1,index);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return Optional.of(new Song(resultSet.getString("artist"), resultSet.getString("title"), resultSet.getInt("length")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return Optional.empty();
        }



    }
}
