/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ehsunbehravesh.capturehome;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
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
        setChanged();
        notifyObservers(capture());

        WebcamMotionDetector detector = new WebcamMotionDetector(Webcam.getDefault());
        detector.setInterval(100);
        detector.start();

        while (capture) {
            if (detector.isMotion()) {
                System.out.println("Motion detected.");
                setChanged();
                notifyObservers(capture());
            }

            try {
                Thread.sleep(500);
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

    private BufferedImage capture() {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage image = webcam.getImage();
        return image;
    }

}
