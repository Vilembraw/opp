package org.example;

import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        ImageHandler imageHandler = new ImageHandler();
        imageHandler.loadImage("thready/ksiek.jpg");

        long start = System.currentTimeMillis();
        imageHandler.incBrigthness(20);
        long end = System.currentTimeMillis();

        System.out.println(end - start);
        imageHandler.saveImage("thready/ksiek_rozjasniony1.jpg");


        try {
            imageHandler.loadImage("thready/ksiek.jpg");
            start = System.currentTimeMillis();
            imageHandler.incBrigthnessThreads(20, 4);
            end = System.currentTimeMillis();
            System.out.println(end - start);
            imageHandler.saveImage("thready/ksiek_rozjasniony2.jpg");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            imageHandler.loadImage("thready/ksiek.jpg");
            start = System.currentTimeMillis();
            imageHandler.incBrigthnessThreadPool(20, 4);
            end = System.currentTimeMillis();
            imageHandler.saveImage("thready/ksiek_rozjasniony3.jpg");

            System.out.println(end - start);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        imageHandler.loadImage("thready/ksiek.jpg");
        imageHandler.saveImage("thready/red.jpg", imageHandler.generateHistogramImage("red"));
        imageHandler.saveImage("thready/green.jpg", imageHandler.generateHistogramImage("green"));
        imageHandler.saveImage("thready/blue.jpg", imageHandler.generateHistogramImage("blue"));

    }
}

/* TODO */
/*  zadanie 5 i 6 (histogram)*/