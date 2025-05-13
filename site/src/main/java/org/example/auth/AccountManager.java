package org.example.auth;

import org.example.database.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountManager {
    DatabaseConnection connection;

    public AccountManager(DatabaseConnection connection) {
        this.connection = connection;
        essentials();
    }

    public void register(String username, String password) {
        try{
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, hashed);
            statement.executeUpdate();

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticate(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
            return BCrypt.checkpw(password, resultSet.getString("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Account getAccount(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.getConnection().prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                return null;
            }
            return new Account(resultSet.getInt("id"), resultSet.getString("username"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void essentials() {
        try(Statement statement = connection.getConnection().createStatement()){

            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS users ("
                            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + "username TEXT UNIQUE NOT NULL,"
                            + "password TEXT NOT NULL)"
            );



        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
