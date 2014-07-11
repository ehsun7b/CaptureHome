/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehsunbehravesh.capturehome;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ehsun Behravesh
 */
public class MotionCapture extends Observable implements Runnable {

    private boolean capture;

    public MotionCapture() {
        capture = true;
    }

    @Override
    public void run() {
        /*
        Dimension[] nonStandardResolutions = new Dimension[]{
            WebcamResolution.PAL.getSize(),
            WebcamResolution.HD720.getSize(),
            new Dimension(2000, 1000),
            new Dimension(1000, 500)
        };*/

        Webcam webcam = Webcam.getDefault();

        //webcam.setCustomViewSizes(nonStandardResolutions);
        //webcam.setViewSize(WebcamResolution.HD720.getSize());
        webcam.setViewSize(new Dimension(640, 480));

        setChanged();
        notifyObservers(capture(webcam));

        WebcamMotionDetector detector = new WebcamMotionDetector(webcam);
        detector.setInterval(100);
        detector.start();

        long lastCapture = System.currentTimeMillis();

        while (capture) {

            if (detector.isMotion()) {
                System.out.println("Motion detected.");
                setChanged();
                notifyObservers(capture(webcam));
                lastCapture = System.currentTimeMillis();
            } else if (System.currentTimeMillis() - lastCapture > 1000 * 60 * 30) { // 30 min
                System.out.println("Periodical capture.");
                setChanged();
                notifyObservers(capture(webcam));
                lastCapture = System.currentTimeMillis();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MotionCapture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        setChanged();
        notifyObservers();
    }

    public void stop() {
        capture = false;
    }

    private BufferedImage capture(Webcam webcam) {
        BufferedImage image = webcam.getImage();
        return image;
    }

}
