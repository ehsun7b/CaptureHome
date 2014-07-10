package com.ehsunbehravesh.capturehome;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64OutputStream;

/**
 *
 * @author Ehsun Behravesh
 */
public class CaptureUploader implements Observer {

    private URL url;
    private final File uploadDirectory;
    private InfinitCapture capture;
    private int interval;

    public CaptureUploader(URL url, File uploadDirectory, int interval) {
        this.url = url;
        this.uploadDirectory = uploadDirectory;
        this.interval = interval;
    }

    public void start() {
        capture = new InfinitCapture(interval);
        capture.addObserver(this);
        Thread thread = new Thread(capture);
        thread.start();
    }

    public void stop() {
        if (capture != null) {
            capture.stop();
        }

        capture = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        InfinitCapture c = (InfinitCapture) o;
        BufferedImage img = (BufferedImage) arg;

        if (img != null) {
            try {
                File imageFile = writeImage(img);
                uploadImage(img);
            } catch (IOException ex) {
                Logger.getLogger(CaptureUploader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private File writeImage(BufferedImage img) throws IOException {
        File file = new File(uploadDirectory, System.currentTimeMillis() + ".png");
        ImageIO.write(img, "PNG", file);
        return file;
    }

    public static String imageToBase64(BufferedImage img) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream b64 = new Base64OutputStream(os);
        ImageIO.write(img, "png", b64);
        String result = os.toString("UTF-8");
        return result;
    }

    private void uploadImage(BufferedImage img) throws IOException {
        System.out.println(imageToBase64(img));
    }

}
