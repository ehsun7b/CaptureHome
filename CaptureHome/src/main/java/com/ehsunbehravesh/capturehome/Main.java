package com.ehsunbehravesh.capturehome;

import java.awt.Dimension;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Ehsun Behravesh
 */
public class Main {

    private static int interval;
    private static String uploadUrl;

    public static void main(String[] args) throws MalformedURLException {
        if (args.length == 1) {
            uploadUrl = args[0];
        } else if (args.length == 2) {
            interval = Integer.parseInt(args[1]);
            uploadUrl = args[0];
        } else {
            System.out.println("please enter interval and upload URL as parameters.");
            System.exit(1);
        }

        if (interval > 0) {
            CaptureUploader cu = new CaptureUploader(new URL(uploadUrl), new File("."), interval);
            //CaptureUploader cu = new CaptureUploader(new URL("http://localhost:8080/upload"), new File("."), 2000);
            cu.start();
        } else {
            Dimension size = new Dimension(640, 480);
            MotionCaptureUploader cu = new MotionCaptureUploader(new URL(uploadUrl), new File("."), size, size, 200, 10);
            cu.start();
        }
    }
}
