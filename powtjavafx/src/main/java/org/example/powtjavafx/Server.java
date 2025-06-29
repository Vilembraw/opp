package org.example.powtjavafx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<ClientThread> clients = new ArrayList<>();
    private final int port;
    private Connection connection;

    public Server(int port) {
        this.port = port;
        connectToDatabase();
        createTable();
    }



    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dots.db");
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS dot(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "x INTEGER NOT NULL," +
                "y INTEGER NOT NULL," +
                "color TEXT NOT NULL," +
                "radius INTEGER NOT NULL)";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Table creation error: " + e.getMessage());
        }
    }

    public void saveDot(Dot dot) {
        String sql = "INSERT INTO dot(x, y, color, radius) VALUES(?,?,?,?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, dot.x());
            pstmt.setInt(2, dot.y());
            pstmt.setString(3, dot.color());
            pstmt.setInt(4, dot.radius());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Save dot error: " + e.getMessage());
        }
    }

    public List<Dot> getSavedDots() {
        List<Dot> dots = new ArrayList<>();
        String sql = "SELECT x, y, color, radius FROM dot";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                dots.add(new Dot(
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getString("color"),
                        rs.getInt("radius")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Get dots error: " + e.getMessage());
        }

        return dots;
    }



    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientThread client = new ClientThread(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
        }
    }

    public void broadcast(String message) {
        for (ClientThread client : clients) {
            client.send(message);
        }
    }

    public void removeClient(ClientThread client) {
        clients.remove(client);
    }
}