package com.ehsunbehravesh.capturehome;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Ehsun Behravesh
 */
public class Main {

    private static String uploadUrl, uploadUrl2;

    public static void main(String[] args) throws MalformedURLException {
        if (args.length == 1) {
            uploadUrl = args[0];
        } else if (args.length == 2) {
            uploadUrl2 = args[1];
            uploadUrl = args[0];
        } else {
            System.out.println("please enter interval and upload URL as parameters.");
            System.exit(1);
        }

        if (uploadUrl2 != null) {
            List<Webcam> webcams = Webcam.getWebcams();
            if (webcams.size() > 1) {
                Dimension size = new Dimension(640, 480);

                MotionCaptureUploader cu = new MotionCaptureUploader(webcams.get(0), new URL(uploadUrl), new File("."), size, size, 200, 10);
                cu.start();

                MotionCaptureUploader cu2 = new MotionCaptureUploader(webcams.get(1), new URL(uploadUrl2), new File("."), size, size, 200, 10);
                cu2.start();
            } else {
                System.out.println("Not enough webcams");
            }
        } else {
            Dimension size = new Dimension(640, 480);
            MotionCaptureUploader cu = new MotionCaptureUploader(Webcam.getDefault(), new URL(uploadUrl), new File("."), size, size, 200, 10);
            cu.start();
        }
    }
}
