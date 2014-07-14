package com.ehsunbehravesh.capturehome;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private MotionCapture motionCapture;
    private VideoCapture videoCapture;
    private final Webcam webcam;
    private final Dimension photoSize, videoSize;
    private final long videoFrames;
    private final int videoFPS;
    private boolean recordingVideo;

    public MotionCaptureUploader(URL url, File uploadDirectory,
            Dimension photoSize, Dimension videoSize, long videoFrames, int videoFPS) {
        this.url = url;
        this.uploadDirectory = uploadDirectory;
        this.photoSize = photoSize;
        this.videoSize = videoSize;
        this.videoFrames = videoFrames;
        this.videoFPS = videoFPS;
        webcam = Webcam.getDefault();
        recordingVideo = false;
    }

    public void start() {
        motionCapture = new MotionCapture(webcam, photoSize);
        motionCapture.addObserver(this);
        Thread thread = new Thread(motionCapture);
        thread.start();
    }

    public void stop() {
        if (motionCapture != null) {
            motionCapture.stop();
        }

        motionCapture = null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof MotionCapture) {

            if (!recordingVideo) {
                recordingVideo = true;
                videoCapture = new VideoCapture(webcam, new File(uploadDirectory, System.currentTimeMillis() + ".ts"), videoFrames, videoSize, videoFPS);
                videoCapture.addObserver(this);
                new Thread(videoCapture).start();                
            }

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
        } else if (o instanceof VideoCapture) {
            videoCapture = null;
            recordingVideo = false;
        }
    }

    private File writeImage(BufferedImage img, String name) throws IOException {
        File file = new File(uploadDirectory, name + ".png");
        ImageIO.write(img, "PNG", file);
        return file;
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
