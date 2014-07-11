package com.ehsunbehravesh.capturehome;

import static com.ehsunbehravesh.capturehome.CaptureUploader.imageToBase64;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ehsun Behravesh
 */
public class MotionCaptureUploader implements Observer {

    private final URL url;
    private final File uploadDirectory;
    private MotionCapture capture;

    public MotionCaptureUploader(URL url, File uploadDirectory) {
        this.url = url;
        this.uploadDirectory = uploadDirectory;
    }

    public void start() {
        capture = new MotionCapture();
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
        BufferedImage img = (BufferedImage) arg;

        if (img != null) {
            try {
                String timestamp = System.currentTimeMillis() + "";
                File imageFile = writeImage(img, timestamp);
                //uploadImage(imageToBase64(img), timestamp);
                uploadBinary(img, timestamp);
            } catch (IOException ex) {
                Logger.getLogger(CaptureUploader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private File writeImage(BufferedImage img, String name) throws IOException {
        File file = new File(uploadDirectory, name + ".png");
        ImageIO.write(img, "PNG", file);
        return file;
    }

    private void uploadImage(String content, String timestamp) throws IOException {
        System.out.println("uploading ... " + timestamp);
        //System.out.println(content);

        content = URLEncoder.encode(content, "UTF-8");
        String postData = "content=" + content + "&timestamp=" + timestamp;

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postData.length()));

        // Write data
        OutputStream os = connection.getOutputStream();
        os.write(postData.getBytes());

        // Read response
        StringBuilder responseSB = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ((line = br.readLine()) != null) {
            responseSB.append(line);
        }

        //Close streams
        br.close();
        os.close();
        //System.out.println(responseSB.toString());
    }

    private void uploadBinary(BufferedImage img, String timestamp) throws IOException {
        System.out.println("uploading ... " + timestamp);
        //System.out.println(content);
        
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "image/png");
        //connection.setRequestProperty("Content-Length", String.valueOf());

        // Write data
        OutputStream os = connection.getOutputStream();
        ImageIO.write(img, "PNG", os);

        // Read response
        StringBuilder responseSB = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ((line = br.readLine()) != null) {
            responseSB.append(line);
        }

        //Close streams
        br.close();
        os.close();
        //System.out.println(responseSB.toString());
    }
}
