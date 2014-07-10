package com.ehsunbehravesh.capturehome;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
                String timestamp = System.currentTimeMillis() + "";
                File imageFile = writeImage(img, timestamp);
                uploadImage(imageToBase64(img), timestamp);
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

    private File writeImage(BufferedImage img, String name) throws IOException {
        File file = new File(uploadDirectory, name + ".png");
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

}
