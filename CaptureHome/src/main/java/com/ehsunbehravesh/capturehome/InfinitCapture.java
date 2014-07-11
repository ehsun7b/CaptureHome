package com.ehsunbehravesh.capturehome;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ehsun Behravesh
 */
public class InfinitCapture extends Observable implements Runnable {

    private int interval;
    private boolean capture;

    public InfinitCapture(int interval) {
        this.interval = interval;
        capture = true;
    }

    @Override
    public void run() {
        while (capture) {
            BufferedImage img = capture();

            setChanged();
            notifyObservers(img);

            try {
                Thread.sleep(interval);
            } catch (InterruptedException ex) {
                Logger.getLogger(InfinitCapture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        setChanged();
        notifyObservers();
    }

    public void stop() {
        capture = false;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private BufferedImage capture() {
        Webcam webcam = Webcam.getDefault();
        webcam.open();
        BufferedImage image = webcam.getImage();
        return image;
    }
}
