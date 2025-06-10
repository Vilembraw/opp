package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ImageHandler {
    private BufferedImage image;

    public void loadImage(String path){
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String path){
        try {
            String format = path.substring(path.lastIndexOf(".") + 1);
            File output = new File(path);
            if (image != null)
                ImageIO.write(this.image, "png", output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String path, BufferedImage image){
        try {
            String format = path.substring(path.lastIndexOf(".") + 1);
            File output = new File(path);
            if (image != null)
                ImageIO.write(image, "png", output);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void incBrigthnessThreads(int value, int cores) throws InterruptedException {
//        int cores = Runtime.getRuntime().availableProcessors();
        int height = image.getHeight();
        int chunk = height / cores;

        Thread[] threads = new Thread[cores];

        for(int i = 0; i < cores; i++){
            int startY = i * chunk;
            int endY = (i == cores - 1) ? height : startY + chunk;

            threads[i] = new Thread(() -> {

                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        int pixel = image.getRGB(x, y);
                        Color color = new Color(pixel);
                        int blue = Math.clamp(color.getBlue() + value, 0 , 255);
                        int green = Math.clamp(color.getGreen() + value, 0 , 255);
                        int red = Math.clamp(color.getRed() + value, 0 , 255);
                        Color newColor = new Color(red, green, blue);
                        image.setRGB(x, y, newColor.getRGB());
                    }
                }


            });

        }


        for (Thread t : threads) {
            t.start();
        }

        for(Thread t : threads){
            t.join();
        }


    }

    public void incBrigthnessThreadPool(int value, int cores) throws InterruptedException {
//        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);


        for(int y = 0; y < image.getHeight(); y++){
            final int row = y;
            executor.execute(() -> {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, row);
                    Color color = new Color(pixel);
                    int blue = Math.clamp(color.getBlue() + value, 0 , 255);
                    int green = Math.clamp(color.getGreen() + value, 0 , 255);
                    int red = Math.clamp(color.getRed() + value, 0 , 255);
                    Color newColor = new Color(red, green, blue);
                    image.setRGB(x, row, newColor.getRGB());                }
            });


        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public void incBrigthness(int value){
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel);
                int blue = Math.clamp(color.getBlue() + value, 0 , 255);
                int green = Math.clamp(color.getGreen() + value, 0 , 255);
                int red = Math.clamp(color.getRed() + value, 0 , 255);
                Color newColor = new Color(red, green, blue);
                image.setRGB(x, y, newColor.getRGB());
            }
        }
    }

    public int[] calcHistogram(String channel) throws InterruptedException {
        int width = image.getWidth();
        int height = image.getHeight();
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // thread safe array
        AtomicIntegerArray histogram = new AtomicIntegerArray(256);

        int chunkSize = height / cores;

        for (int i = 0; i < cores; i++) {
            final int startY = i * chunkSize;
            final int endY = (i == cores - 1) ? height : startY + chunkSize;

            executor.execute(() -> {
                for (int y = startY; y < endY; y++) {
                    for (int x = 0; x < width; x++) {
                        Color color = new Color(image.getRGB(x, y));
                        int value = switch (channel.toLowerCase()) {
                            case "red" -> color.getRed();
                            case "green" -> color.getGreen();
                            case "blue" -> color.getBlue();
                            default -> 0;
                        };
                        histogram.incrementAndGet(value);
                    }
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        int[] result = new int[256];
        for (int i = 0; i < 256; i++) {
            result[i] = histogram.get(i);
        }

        return result;
    }

    public BufferedImage generateHistogramImage(String channel) {
        Color color;
        switch (channel.toLowerCase()) {
            case "red" -> color = Color.RED;
            case "green" -> color = Color.GREEN;
            case "blue" -> color = Color.BLUE;
            default -> {
                return null;
            }
        }

        int[] histogram;
        try {
            histogram = this.calcHistogram(channel);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        int maxValue = Arrays.stream(histogram).max().orElse(1);
        if (maxValue == 0) maxValue = 1;

        int imgWidth = 256;
        int imgHeight = 256;
        BufferedImage histogramImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = histogramImage.createGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, imgWidth, imgHeight);

        g.setColor(color);
        for (int i = 0; i < histogram.length; i++) {
            int barHeight = (int) ((double) histogram[i] / maxValue * imgHeight);
            g.fillRect(i, imgHeight - barHeight, 1, barHeight);
        }

        g.setColor(Color.BLACK);
        g.drawLine(0, imgHeight-1, imgWidth, imgHeight-1);
        g.drawLine(0, 0, 0, imgHeight);

        g.dispose();

        return histogramImage;
    }
}
