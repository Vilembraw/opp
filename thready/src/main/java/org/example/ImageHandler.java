package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
}
