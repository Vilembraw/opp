package org.example.powtjavafx;

public record Dot(int x, int y, String color, int radius) {
    public static String toMessage(int x, int y, String color, int radius) {
        return x + "," + y + "," + color + "," + radius;
    }

    public static Dot fromMessage(String message) {
        String[] parts = message.split(",");
        return new Dot(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                parts[2],
                Integer.parseInt(parts[3])
        );
    }

    public String toMessage() {
        return toMessage(x, y, color, radius);
    }
}